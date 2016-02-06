package com.denis.feed.entryInstance.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by denis on 27/09/15
 */
@Entity
@Table(name = "com/denis/feed/entryInstance")
public class EntryInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idEntryInstance")

    private Long idEntryInstance;

    private boolean processed;

    private Date dateProcess;

    private Long idEntry;

    private Long idFeedInstance;

    public Long getIdEntryInstance() {
        return idEntryInstance;
    }

    public void setIdEntryInstance(Long idEntryInstance) {
        this.idEntryInstance = idEntryInstance;
    }

    public boolean getProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public Date getDateProcess() {
        return dateProcess;
    }

    public void setDateProcess(Date dateProcess) {
        this.dateProcess = dateProcess;
    }

    public Long getIdEntry() {
        return idEntry;
    }

    public void setIdEntry(Long idEntry) {
        this.idEntry = idEntry;
    }

    public Long getIdFeedInstance() {
        return idFeedInstance;
    }

    public void setIdFeedInstance(Long idFeedInstance) {
        this.idFeedInstance = idFeedInstance;
    }
}
