package com.denis.feed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by denisfeijooformoso on 06/02/16.
 */
    @Component
    public final class App {

        private static final Logger log = LogManager.getLogger();

        @PostConstruct
        public void onCreate() {
            log.info("[onCreate] application is ready");
        }

        @PreDestroy
        public void onDestroy() {
            log.info("[onCreate] destroying application");
        }


}
