package com.denis.feed.feed.controller;

import com.denis.feed.Utils.Session;
import com.denis.feed.entry.controller.EntryController;
import com.denis.feed.entry.domain.EntryWrapper;
import com.denis.feed.entryInstance.service.EntryInstanceService;
import com.denis.feed.feed.domain.FeedWrapper;
import com.denis.feed.feedInstance.service.FeedInstanceService;
import com.denis.feed.validation.InjectSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by denis on 14/10/15
 */
@RestController
@RequestMapping("/feeds")
public class FeedController {

    protected final Logger log = LogManager.getLogger();


    @Autowired
    FeedInstanceService feedInstanceService;

    @Autowired
    EntryInstanceService entryInstanceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<FeedWrapper> getByUser(@InjectSession Session session) {
        //TODO PROPERTIES
        List<FeedWrapper> feedList = feedInstanceService.getByUser(session.getUser().getIdUser(), new PageRequest(0, 10));
        for (FeedWrapper feed : feedList) {
            Link link = linkTo(methodOn(FeedController.class).getByIdFeedAndUser(feed.getIdFeed(), session)).withSelfRel();
            feed.add(link);
        }
        return feedList;
    }

    @RequestMapping("/{idFeed}")
    public FeedWrapper getByIdFeedAndUser(@PathVariable Long idFeed, @InjectSession Session session) {
        FeedWrapper fw = feedInstanceService.getByIdFeedAndUser(idFeed, session.getUser());
        Link link = linkTo(methodOn(FeedController.class).getEntriesByFeed(fw.getIdFeed(), session)).withRel("entries");
        fw.add(link);
        return fw;
    }

    @RequestMapping("/{idFeed}/entries")
    public List<EntryWrapper> getEntriesByFeed(@PathVariable Long idFeed, @InjectSession Session session) {
        List<EntryWrapper> entries = entryInstanceService.findByIdFeedAndUser(idFeed, session.getUser(), new PageRequest(0, 10));
        for (EntryWrapper entry : entries) {
            Link link = linkTo(methodOn(EntryController.class).getById(entry.getIdEntry(), session)).withSelfRel();
            entry.add(link);
        }
        return entries;
    }
}
