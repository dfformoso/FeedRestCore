package com.denis.feed.feedInstance.domain;

import javax.persistence.*;

/**
 * Created by denis on 27/09/15
 */
@Entity
@Table(name = "feedInstance")
public class FeedInstance {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idFeedInstance")
    private Long idFeedInstance;

    private String feedInstanceName;

    private Long idCategory;

    private Long idUser;

    private Long idFeed;


    public Long getIdFeedInstance() {
        return idFeedInstance;
    }

    public void setIdFeedInstance(Long idFeedInstance) {
        this.idFeedInstance = idFeedInstance;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdFeed() {
        return idFeed;
    }

    public void setIdFeed(Long idFeed) {
        this.idFeed = idFeed;
    }


    public String getFeedInstanceName() {
        return feedInstanceName;
    }

    public void setFeedInstanceName(String feedInstanceName) {
        this.feedInstanceName = feedInstanceName;
    }

}
