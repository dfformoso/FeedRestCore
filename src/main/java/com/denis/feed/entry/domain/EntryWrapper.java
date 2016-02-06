package com.denis.feed.entry.domain;

import com.denis.feed.entryInstance.domain.EntryInstance;
import com.denis.feed.feed.domain.Feed;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by denis on 24/09/15
 */
public class EntryWrapper extends ResourceSupport {

    private final long idEntry;

    private final long idFeed;

    private final String url;

    private final String title;

    private final String summary;

    private final boolean processed;

    private final String feedURL;

    private final String feedTitle;

    public EntryWrapper(Entry entry, EntryInstance entryInstance, Feed feed) {
        this.idEntry = entry.getIdEntry();
        this.idFeed = entry.getIdFeed();
        this.url = entry.getUrl();
        this.title = entry.getTitle();
        this.processed = entryInstance.getProcessed();
        this.feedURL = feed.getSourceUrl();
        this.feedTitle = feed.getTitle();

        //TODO construct summary on com.denis.feed.entry creation
        String shortDescription = "";
        if (entry.getContent() != null) {
            if (entry.getContent() != null) {
                Document doc = Jsoup.parse(entry.getContent());
                Elements elements = doc.body().getAllElements();
                for (Element e : elements) {
                    if ((!e.tagName().equals("a")) && (!e.ownText().isEmpty())) {
                        String p = e.ownText();
                        int endChar = (p.length() > 150 ? 150 : p.length());
                        boolean endOfWord = false;
                        while (endOfWord == false && p.length() > endChar) {
                            if (p.charAt(endChar) == ' ') {
                                endOfWord = true;
                            } else {
                                endChar++;
                            }
                        }
                        shortDescription = p.substring(0, endChar);
                        break;
                    }
                }
            }
        }
        summary = shortDescription;
    }

    public long getIdEntry() {
        return idEntry;
    }

    public long getIdFeed() {
        return idFeed;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public boolean isProcessed() {
        return processed;
    }

    public String getFeedURL() {
        return feedURL;
    }

    public String getSummary() {
        return summary;
    }

    public String getFeedTitle() {
        return this.feedTitle;
    }

}
