package com.denis.feed.entry.controller;

import com.denis.feed.Utils.Session;
import com.denis.feed.entry.domain.EntryContentWrapper;
import com.denis.feed.entry.domain.EntryWrapper;
import com.denis.feed.entryInstance.service.EntryInstanceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.denis.feed.validation.InjectSession;

import java.util.List;

/**
 * Created by denis on 15/10/15
 */
@RestController
@RequestMapping("/entries")
public class EntryController {

    protected final Logger log = LogManager.getLogger();

    @Autowired
    EntryInstanceService entryInstanceService;

    @RequestMapping("/{id}")
    public EntryWrapper getById(@PathVariable Long id, @InjectSession Session session) {
        return entryInstanceService.getByIdEntryAndUser(id, session.getUser());
    }

    @RequestMapping("/{id}/content")
    public EntryContentWrapper getContent(@PathVariable Long id, @InjectSession Session session) {
        return new EntryContentWrapper(entryInstanceService.getContent(id, session.getUser()));
    }

    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public List<EntryWrapper> getNewEntries(@InjectSession Session session) {
        return entryInstanceService.getNewEntries(session.getUser().getIdUser());
    }

    @RequestMapping(value = "/{idEntry}/read", method = RequestMethod.POST)
    public void setEntryRead(@PathVariable Long idEntry, @InjectSession Session session) {
        entryInstanceService.updateEntryInstance(idEntry, session.getUser().getIdUser(), true);
    }


}
