package com.fiverr.sanalytics.util;

import java.util.Calendar;

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

    public static String extractDOW(String mm_dd_yyyy) {
        if (mm_dd_yyyy == null || mm_dd_yyyy.isEmpty()) {
            return "";
        }

        String[] tokens = mm_dd_yyyy.split("/");
        if (tokens.length != 3) {
            return "";
        } else {
            Calendar c = Calendar.getInstance();
            try {
                int year = Integer.parseInt(tokens[2]);//Avoid such hardcode
                int month = Integer.parseInt(tokens[0]);
                int day = Integer.parseInt(tokens[1]);

                c.set(year, month, day);

                return "" + c.get(Calendar.DAY_OF_WEEK);
            } catch (Exception ex) {
                return "";
            }
        }
    }
}
