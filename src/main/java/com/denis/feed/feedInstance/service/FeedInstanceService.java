package com.denis.feed.feedInstance.service;

import com.denis.feed.category.dao.CategoryRepository;
import com.denis.feed.category.domain.Category;
import com.sun.syndication.io.FeedException;
import com.denis.feed.entry.dao.EntryRepository;
import com.denis.feed.entry.domain.Entry;
import com.denis.feed.entryInstance.dao.EntryInstanceRepository;
import com.denis.feed.entryInstance.domain.EntryInstance;
import com.denis.feed.feed.dao.FeedRepository;
import com.denis.feed.feed.domain.Feed;
import com.denis.feed.feed.domain.FeedWrapper;
import com.denis.feed.feed.service.FeedService;
import com.denis.feed.feedInstance.dao.FeedInstanceRepository;
import com.denis.feed.feedInstance.domain.FeedInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.denis.feed.user.dao.UserRepository;
import com.denis.feed.user.domain.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 27/09/15
 */
@Service(value = "feedInstanceService")
public class FeedInstanceService {

    protected final Logger log = LogManager.getLogger();

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    FeedService feedService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FeedInstanceRepository feedInstanceRepository;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    EntryInstanceRepository entryInstanceRepository;

    @Transactional
    public FeedInstance createFeedInstance(String feedUrl, String renameFeed, long idUser, long idCategory) {
        FeedInstance feedInstance = new FeedInstance();
        //Check existentFeed
        Feed feed = feedRepository.getByUrl(feedUrl);
        if (feed == null) {
            try {
                feed = feedService.createFeed(feedUrl);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FeedException e) {
                e.printStackTrace();
            }
        }
        feedInstance.setIdFeed(feed.getIdFeed());
        feedInstance.setFeedInstanceName(renameFeed != null ? renameFeed : feed.getTitle());

        // getUser and set User if not exists throw Exception
        User user = userRepository.getByIdUser(idUser);
        if (user == null) {
            //TODO throw new Throwable("User not exists");
        }
        feedInstance.setIdUser(user.getIdUser());

        //getCategory and set com.denis.com.denis.feed.feed.category if not exists throw Exception
        Category category = categoryRepository.getByIdCategory(idCategory);
        if (category == null) {
            //TODO throw new Throwable("Category not exists");
        }
        feedInstance.setIdCategory(category.getIdCategory());
        feedInstanceRepository.save(feedInstance);

        List<Entry> entryList = entryRepository.getByIdFeedOrderByCreationDateDesc(feed.getIdFeed(), new PageRequest(0, 30));
        for (Entry e : entryList) {
            EntryInstance ei = new EntryInstance();
            ei.setIdEntry(e.getIdEntry());
            ei.setIdFeedInstance(feedInstance.getIdFeedInstance());
            ei.setProcessed(false);
            entryInstanceRepository.save(ei);
        }
        return feedInstance;
    }

    @Transactional
    public void updateFeedInstanceRead(String feedUrl, Long idUser, boolean read) {
        log.info("updateFeedInstanceRead feedUrl[" + feedUrl + "] idUser [" + idUser + "] read [" + read + "]");
        //entryInstanceRepository.setAllProcessed();
    }

    @Transactional
    public void deleteFeedInstance(Long idFeed, Long idUser) {
        log.info("deleteFeedInstance idFeed[" + idFeed + "]");

        FeedInstance feedInstance = feedInstanceRepository.getByIdUserAndIdFeed(idUser, idFeed);
        List<EntryInstance> entryInstanceList = entryInstanceRepository.getByIdFeedInstance(feedInstance.getIdFeedInstance());
        //TODO launch in asynchronous response
        entryInstanceList.forEach(entryInstanceRepository::delete);
        feedInstanceRepository.delete(feedInstance);
    }

    public List<FeedWrapper> getByUser(Long idUser, PageRequest pageRequest) {
        List<FeedWrapper> feedWrappers = new ArrayList<>();
        List<FeedInstance> feedInstanceList = feedInstanceRepository.getByIdUser(idUser, pageRequest);
        for (FeedInstance fi : feedInstanceList) {
            Feed feed = feedService.getByIdFeed(fi.getIdFeed());
            feedWrappers.add(new FeedWrapper(feed, fi));
        }
        return feedWrappers;
    }

    public FeedWrapper getByIdFeedAndUser(Long idFeed, User user) {
        FeedInstance feedInstance = feedInstanceRepository.getByIdUserAndIdFeed(user.getIdUser(), idFeed);
        Feed feed = feedService.getByIdFeed(feedInstance.getIdFeed());
        return new FeedWrapper(feed, feedInstance);
    }

    public List<FeedWrapper> getByIdCategory(Long idCategory, Long idUser) {
        List<FeedWrapper> feedWrappers = new ArrayList<>();
        List<FeedInstance> feedInstanceList = feedInstanceRepository.getByIdCategory(idCategory);
        for (FeedInstance fi : feedInstanceList) {
            Feed feed = feedService.getByIdFeed(fi.getIdFeed());
            feedWrappers.add(new FeedWrapper(feed, fi));
        }
        return feedWrappers;
    }
}