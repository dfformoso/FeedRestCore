package com.denis.feed.feed.domain;

import com.denis.feed.feedInstance.domain.FeedInstance;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by denis on 14/10/15
 */
public class FeedWrapper extends ResourceSupport {


    public FeedWrapper(Feed feed, FeedInstance feedInstance) {
        idFeed = feed.getIdFeed();
        url = feed.getUrl();
        title = feed.getTitle();
        lastUpdate = feed.getLastUpdate();
        sourceUrl = feed.getSourceUrl();
        idCategory = feedInstance.getIdCategory();
        idUser = feedInstance.getIdUser();
        feedInstanceName = feedInstance.getFeedInstanceName();
    }

    private Long idFeed;

    private String url;

    private String title;

    private Date lastUpdate;

    private String sourceUrl;

    private Long idCategory;

    private Long idUser;

    private String feedInstanceName;

    public Long getIdFeed() {
        return idFeed;
    }


    public String getUrl() {
        return url;
    }


    public String getTitle() {
        return title;
    }


    public Date getLastUpdate() {
        return lastUpdate;
    }


    public String getSourceUrl() {
        return sourceUrl;
    }


    public Long getIdCategory() {
        return idCategory;
    }


    public Long getIdUser() {
        return idUser;
    }


    public String getFeedInstanceName() {
        return feedInstanceName;
    }


}
