package com.denis.feed.entryInstance.dao;

import com.denis.feed.entryInstance.domain.EntryInstance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by denis on 27/09/15
 */

public interface EntryInstanceRepository extends Repository<EntryInstance, Long> {

    EntryInstance getByIdEntryInstance(Long idEntryInstance);

    EntryInstance getByIdEntryAndIdFeedInstance(Long idEntry, Long idFeedInstance);

    @Query(value = "SELECT * FROM entryInstance ei INNER JOIN feedInstance fi ON ei.idFeedInstance = fi.idFeedInstance WHERE ei.idEntry = ?1 AND fi.idUser = ?2", nativeQuery = true)
    EntryInstance getBydIdEntryAndUser(Long idEntry, Long idUser);

    List<EntryInstance> getByIdFeedInstanceAndProcessed(Long idFeedInstance, boolean processed);

    List<EntryInstance> getByIdFeedInstance(Long idFeedInstance);

    @Query(value = "SELECT ei.* FROM entryInstance AS ei INNER JOIN entry AS e ON ei.idEntry = e.idEntry INNER JOIN feedInstance AS fi ON ei.idFeedInstance = fi.idFeedInstance INNER JOIN account AS u ON fi.idUser = u.idUser WHERE u.idUser = ?1 AND ei.processed=0 ORDER BY e.creationDate DESC LIMIT 10",nativeQuery = true)
    List<EntryInstance> getLastNonReadByUser(Long idUser);

//    @Query("SELECT COUNT (ei) FROM EntryInstance ei INNER JOIN ei.com.denis.feed.feedInstance fi " +
//            "WHERE fi.idFeedInstance= ?1 AND fi.com.denis.feed.user.idUser= ?2 AND ei.processed=false")
//    int countNonReadByIdFeedInstance(Long idFeedInstance, Long idUser);


//    @Query("UPDATE EntryInstance ei SET ei.processed=true, ei.dateProcess= CURRENT_TIMESTAMP WHERE ei.com.denis.feed.feedInstance IN (" +
//            "SELECT fi.idFeedInstance FROM FeedInstance fi INNER JOIN fi.com.denis.feed.feed AS f " +
//            "WHERE fi.com.denis.feed.user.idUser = :idUser AND f.feedID= :feedID ) AND ei.processed=false")
//    void setAllProcessed();

    EntryInstance save(EntryInstance entryInstance);

    void delete(EntryInstance entryInstance);
}
