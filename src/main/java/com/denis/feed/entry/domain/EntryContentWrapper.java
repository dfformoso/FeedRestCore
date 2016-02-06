package com.denis.feed.entry.domain;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by denisfeijooformoso on 18/1/16.
 */
public class EntryContentWrapper extends ResourceSupport {

    private String content;

    public EntryContentWrapper(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
