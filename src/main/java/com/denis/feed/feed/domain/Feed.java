package com.denis.feed.feed.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by denis on 10/09/15
 */
@Entity
@Table(name = "feed")
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idFeed")
    private Long idFeed;

    private String url;

    private String title;

    private Date creationDate;

    private Date lastUpdate;

    private String sourceUrl;


    public Long getIdFeed() {
        return idFeed;
    }

    public void setIdFeed(Long idFeed) {
        this.idFeed = idFeed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

}
