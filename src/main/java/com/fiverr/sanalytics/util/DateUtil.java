package com.fiverr.sanalytics.util;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DateUtil {

    public static String extractDOM(String mm_dd_yyyy) {
        if (mm_dd_yyyy == null || mm_dd_yyyy.isEmpty()) {
            return null;
        }


        String[] tokens = mm_dd_yyyy.split("/");

        return tokens.length == 3 ? tokens[1] : "";
    }
}
