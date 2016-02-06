package com.denis.feed.entry.service;

import com.denis.feed.Utils.FeedUtils;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.denis.feed.entry.dao.EntryRepository;
import com.denis.feed.entry.domain.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 17/09/15
 */
@Service
public class EntryService {

    @Autowired
    EntryRepository entryRepository;

    public List<Entry> getEntriesBySyndFeed(SyndFeed feed, Integer maxEntries) {
        List<Entry> entryList = new ArrayList<Entry>();
        for (Object objEntry : feed.getEntries()) {
            SyndEntryImpl entry = (SyndEntryImpl) objEntry;
            Entry domainEntry = FeedUtils.convertToDomainEntry(entry);
            entryList.add(domainEntry);
            if (entryList.size() >= maxEntries) {
                break;
            }
        }
        return entryList;
    }

    public Entry getById(Long idEntry) {
        return entryRepository.getByIdEntry(idEntry);
    }

    public Page<Entry> findByIdFeed(Long idFeed, PageRequest request) {
        return entryRepository.getByIdFeed(idFeed, request);
    }

}
