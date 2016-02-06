package com.denis.feed.entryInstance.service;

import com.denis.feed.entry.domain.Entry;
import com.denis.feed.entry.domain.EntryWrapper;
import com.denis.feed.entry.service.EntryService;
import com.denis.feed.entryInstance.dao.EntryInstanceRepository;
import com.denis.feed.entryInstance.domain.EntryInstance;
import com.denis.feed.feed.dao.FeedRepository;
import com.denis.feed.feed.domain.Feed;
import com.denis.feed.feedInstance.dao.FeedInstanceRepository;
import com.denis.feed.feedInstance.domain.FeedInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.denis.feed.user.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by denis on 27/09/15
 */
@Service("entryInstanceService")
public class EntryInstanceService {

    protected final Logger log = LogManager.getLogger();

    @Autowired
    EntryInstanceRepository entryInstanceRepository;

    @Autowired
    FeedInstanceRepository feedInstanceRepository;

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    EntryService entryService;

    public void updateEntryInstance(Long idEntry, Long idFeed, Long idUser, boolean read) {
        log.info("updateEntryInstance idUser [" + idUser + "] idEntryInstance [" + idEntry + "] read [" + read + "]");

        FeedInstance feedInstance = feedInstanceRepository.getByIdUserAndIdFeed(idUser, idFeed);
        EntryInstance ei = entryInstanceRepository.getByIdEntryAndIdFeedInstance(idEntry, feedInstance.getIdFeed());
        ei.setProcessed(read);
        if (read) {
            ei.setDateProcess(new Date());
        }
        entryInstanceRepository.save(ei);
    }

    public EntryWrapper getByIdEntryAndUser(Long idEntry, User user) {
        log.info("getByIdEntryAndUser idUser [" + user.getIdUser() + "] idEntry [" + idEntry + "]");
        Entry entry = entryService.getById(idEntry);
        Feed feed = feedRepository.getByIdFeed(entry.getIdFeed());
        EntryInstance ei = entryInstanceRepository.getBydIdEntryAndUser(idEntry, user.getIdUser());
        return new EntryWrapper(entry, ei, feed);
    }

    public String getContent(Long idEntry, User user) {
        log.info("getContent idUser [" + user.getIdUser() + "] idEntry [" + idEntry + "]");
        Entry entry = entryService.getById(idEntry);
        return entry.getContent();
    }

    public List<EntryWrapper> findByIdFeedAndUser(Long idFeed, User user, PageRequest request) {
        log.info("findByIdFeedAndUser idUser [" + user.getIdUser() + "] idFeed [" + idFeed + "]");
        ArrayList<EntryWrapper> entryWrappers = new ArrayList<>();
        Page<Entry> entries = entryService.findByIdFeed(idFeed, request);
        Feed feed = feedRepository.getByIdFeed(idFeed);
        for (Entry e : entries) {
            EntryInstance ei = entryInstanceRepository.getBydIdEntryAndUser(e.getIdEntry(), user.getIdUser());
            EntryWrapper ew = new EntryWrapper(e, ei, feed);
            entryWrappers.add(ew);
        }
        return entryWrappers;
    }

    public List<EntryWrapper> getNewEntries(Long idUser) {
        log.info("getNewEntries idUser [" + idUser + "]");
        List<EntryWrapper> toRet = new ArrayList<>();
        List<EntryInstance> eiList = entryInstanceRepository.getLastNonReadByUser(idUser);
        for (EntryInstance ei : eiList) {
            Entry entry = entryService.getById(ei.getIdEntry());
            Feed feed = feedRepository.getByIdFeed(entry.getIdFeed());
            EntryWrapper ew = new EntryWrapper(entry, ei, feed);
            toRet.add(ew);
        }
        return toRet;
    }
}
