package com.denis.feed.entry.dao;

import com.denis.feed.entry.domain.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by denis on 17/09/15
 */
public interface EntryRepository extends Repository<Entry, Long> {

    Entry save(Entry entry);

    List<Entry> getByIdFeedOrderByCreationDateDesc(Long idFeed, Pageable pageable);

    Entry getByIdEntry(Long idEntry);

    Page<Entry> getByIdFeed(Long idFeed, Pageable pageable);
}
