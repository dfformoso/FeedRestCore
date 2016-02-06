package com.denis.feed.feed.dao;

import com.denis.feed.feed.domain.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by denis on 14/09/15
 */
public interface FeedRepository extends Repository<Feed, Long> {

    Feed getByIdFeed(Long idFeed);

    Feed getByUrl(String url);

    @Query("Select f From Feed f WHERE trim(upper(f.url)) like ?1")
    List<Feed> findByUrl(String url, Pageable pageable);

    List<Feed> findAll(Pageable pageable);

    Feed save(Feed feed);


}
