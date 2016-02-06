package com.denis.feed.feedInstance.dao;

import com.denis.feed.feedInstance.domain.FeedInstance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by denis on 27/09/15
 */
@org.springframework.stereotype.Repository
public interface FeedInstanceRepository extends Repository<FeedInstance, Long> {

    List<FeedInstance> getByIdUser(Long idUser, Pageable pageable);

    FeedInstance getByIdFeedInstance(Long idFeedInstance);

    FeedInstance getByIdUserAndIdFeed(Long idUser, Long idFeed);

    List<FeedInstance> getByIdFeed(Long idFeed);

    List<FeedInstance> getByIdCategory(Long idCategory);

    FeedInstance save(FeedInstance feedInstance);

    void delete(FeedInstance feedInstance);

}
