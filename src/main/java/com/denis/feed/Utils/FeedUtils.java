package com.denis.feed.Utils;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.denis.feed.entry.domain.Entry;
import com.denis.feed.feed.domain.Feed;

/**
 * Created by denis on 22/09/15
 */
public class FeedUtils {

    public static Feed convertToDomainFeed(SyndFeed syndFeed) {
        Feed feed = new Feed();
        feed.setUrl(syndFeed.getUri());
        feed.setTitle(syndFeed.getTitle());
        return feed;
    }

    public static Entry convertToDomainEntry(SyndEntryImpl syndEntryImpl) {
        Entry entry = new Entry();
        entry.setTitle(syndEntryImpl.getTitle());
        if (syndEntryImpl.getContents() == null || syndEntryImpl.getContents().isEmpty()) {
            entry.setContent(syndEntryImpl.getDescription().getValue());
        } else {
            SyndContent content = (SyndContent) syndEntryImpl.getContents().get(0);
            entry.setContent(content.getValue());
        }
        entry.setUrl(syndEntryImpl.getLink());
        entry.setCreationDate(syndEntryImpl.getPublishedDate());
        return entry;
    }
}
