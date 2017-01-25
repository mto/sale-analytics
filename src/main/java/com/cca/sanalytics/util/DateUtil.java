package com.cca.sanalytics.util;

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

    public static String extractMYKey(String mm_dd_yyyy){
        if (mm_dd_yyyy == null || mm_dd_yyyy.isEmpty()) {
            return null;
        }

        String[] tokens = mm_dd_yyyy.split("/");

        return tokens.length == 3 ? (tokens[0] + "-" + tokens[2]) : "";
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
            c.setFirstDayOfWeek(Calendar.MONDAY);
            try {
                int year = Integer.parseInt(tokens[2]);//Avoid such hardcode
                int month = Integer.parseInt(tokens[0]);
                int day = Integer.parseInt(tokens[1]);

                c.set(year, month, day);
                int dow =  1 + ((c.get(Calendar.DAY_OF_WEEK) + 7 - c.getFirstDayOfWeek()) % 7);

                return "" + dow;
            } catch (Exception ex) {
                return "";
            }
        }
    }

    public static void main(String[] args){
        System.out.println(DateUtil.extractDOW("10/15/10"));
        System.out.println(DateUtil.extractDOW("3/4/15"));
        System.out.println(DateUtil.extractDOW("6/1/15"));

    }
}
