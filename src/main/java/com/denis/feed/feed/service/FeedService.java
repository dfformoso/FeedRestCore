package com.denis.feed.feed.service;

import com.denis.feed.Utils.FeedUtils;
import com.denis.feed.Utils.URLUtils;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.denis.feed.entry.dao.EntryRepository;
import com.denis.feed.entry.domain.Entry;
import com.denis.feed.entry.service.EntryService;
import com.denis.feed.feed.dao.FeedRepository;
import com.denis.feed.feed.domain.Feed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by denis on 17/09/15
 */
@Service(value = "feedService")
public class FeedService {
    protected final Logger log = LogManager.getLogger();

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    EntryService entryService;

    @Autowired
    EntryRepository entryRepository;


    public Feed getByIdFeed(Long idFeed) {
        return feedRepository.getByIdFeed(idFeed);
    }

    @Transactional
    public Feed createFeed(String url) throws IOException, FeedException {
        log.info("com.denis.feed.feed.url [" + url + "]");
        final Feed domainFeed = new Feed();

        domainFeed.setUrl(url);
        URL feedUrl = new URL(url);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed syndFeed = input.build(new XmlReader(feedUrl));

        domainFeed.setTitle(syndFeed.getTitle());
        domainFeed.setCreationDate(Calendar.getInstance().getTime());
        domainFeed.setSourceUrl(syndFeed.getLink());

        Feed toRetFeed = feedRepository.save(domainFeed);
        List<Entry> entryList = entryService.getEntriesBySyndFeed(syndFeed, 7);
        for (Entry e : entryList) {
            e.setIdFeed(toRetFeed.getIdFeed());
            entryRepository.save(e);
        }
        return toRetFeed;
    }

    /**
     * The method search first in DB to find Feeds already added.
     * If no feeds found, the method try to build an URL with the param url and make a web Search
     *
     * @param url String to search to
     * @return resultFeedList
     */
    public List<Feed> searchFeed(String url) {
        List<Feed> resultFeedList = new ArrayList<Feed>();
        //TODO properties value
        resultFeedList = feedRepository.findByUrl(url, new PageRequest(1, 10));
        log.info("Result size [" + resultFeedList.size() + "]");
        //NO DB results, try to build URL
        if (resultFeedList.isEmpty()) {
            URL webUrl = null;
            if (URLUtils.isUrl(url)) {
                //AUTO BUILD
                try {
                    webUrl = new URL(url);
                } catch (MalformedURLException ex) {
                    log.error("error auto build url, trying manual build");
                }
            }
            //MANUAL BUILD
            if (webUrl == null) {
                try {
                    webUrl = URLUtils.buildURL(url);
                } catch (MalformedURLException ex) {
                    log.error("error manual build url");
                }
            }
            //URL Search
            if (webUrl != null) {
                try {
                    resultFeedList = searchFeedsByUrl(webUrl);
                    log.info("URL search com.denis.feed.feed result [" + resultFeedList.size() + "]");
                } catch (IOException ioE) {
                    log.error("Can not connect [" + webUrl + "]", ioE);
                }
            }

        }
        return resultFeedList;
    }

    public List<Entry> getFeedUpdates(Feed feed) {
        final ArrayList<Entry> resultList = new ArrayList<>();
        Entry lastEntry = null;
        final List<Entry> entries = entryRepository.getByIdFeedOrderByCreationDateDesc(feed.getIdFeed(), new PageRequest(1, 1));
        if (entries.isEmpty() == false) {
            lastEntry = entries.get(0);
        }
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed syndFeed = input.build(new XmlReader(new URL(feed.getUrl())));

            for (Object objEntry : syndFeed.getEntries()) {
                SyndEntryImpl entry = (SyndEntryImpl) objEntry;
                Entry domainEntry = FeedUtils.convertToDomainEntry(entry);
                domainEntry.setIdFeed(feed.getIdFeed());
                if (domainEntry.getCreationDate().before(lastEntry.getCreationDate())) {
                    break;
                }
                resultList.add(domainEntry);
            }
        } catch (FeedException | IOException exc) {
            log.error("Error accessing Feed [" + feed.getUrl() + "] to get updates", exc);
        }
        return resultList;
    }

    /**
     * Return Feeds from a URL encapsulate loop for search feeds and JSOUP usage
     *
     * @param url
     * @return
     * @throws IOException
     */
    private List<Feed> searchFeedsByUrl(URL url) throws IOException {
        // Connect with Url
        ArrayList<Feed> feedList = new ArrayList<Feed>();
        Document doc;
        Connection conn = Jsoup.connect(url.toString());
        conn.timeout(60000);
        doc = conn.get();
        Element head = doc.head();
        Elements links = head.select("link");
        for (Element link : links) {
            if ((link.attr("type").contains("rss")) || (link.attr("type").contains("atom"))) {
                String feedRef = link.attr("href");
                // Construct ROME SyndFeed object
                SyndFeed syndFeed = getSyndFeedByURL(feedRef, url);
                if (syndFeed != null) {
                    feedList.add(FeedUtils.convertToDomainFeed(syndFeed));
                }
            }
        }
        return feedList;
    }

    /**
     * Build Feed resource URL from a resource find in a WEB and call to getSyndFeed
     *
     * @param url
     * @param hostUrl
     * @return
     */

    private SyndFeed getSyndFeedByURL(String url, URL hostUrl) {

        SyndFeed toRetSyndFeed = null;
        // DirectURL
        toRetSyndFeed = getSyndFeed(url);
        if (toRetSyndFeed == null) {
            // adding http
            if (!url.startsWith("http")) {
                String modUrl = null;
                modUrl = "https:" + url;
                toRetSyndFeed = getSyndFeed(modUrl);
                if (toRetSyndFeed == null) {
                    modUrl = "http:" + url;
                    toRetSyndFeed = getSyndFeed(modUrl);
                }
            }
            if (toRetSyndFeed == null) {
                if (hostUrl != null && !url.contains(hostUrl.getHost())) {
                    url = hostUrl.getProtocol() + "://" + hostUrl.getHost().toString() + url;
                    toRetSyndFeed = getSyndFeed(url);
                }
            }
        }
        return toRetSyndFeed;
    }

    /**
     * Basic ROME operation, get SyndFeed from a URL
     *
     * @param url
     * @return
     */
    private SyndFeed getSyndFeed(String url) {
        URL feedURL;
        SyndFeed syndFeed = null;
        try {
            feedURL = new URL(url);
            syndFeed = new SyndFeedInput().build(new XmlReader(feedURL));
            syndFeed.setUri(feedURL.toString());
        } catch (IllegalArgumentException | IOException | FeedException exception) {
            log.error("Error getSyndFeed on URL [" + url + "]");
        }
        return syndFeed;
    }
}


