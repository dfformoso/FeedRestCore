package com.denis.feed.Utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by denis on 19/09/15
 */
public class URLUtils {

    public static boolean isUrl(String url) {
        URL toRet;
        try {
            toRet = new URL(url);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    public static URL buildURL(String s) throws MalformedURLException {
        URL url = null;
        try {
            url = new URL("http", s, "");
        } catch (MalformedURLException e) {
            throw e;
        }
        return url;
    }
}
