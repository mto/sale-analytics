package com.fiverr.sanalytics.jfx.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DOWTotalSale {

    public final static Map<String, String> DOW_DAY_MAP = new HashMap<String, String>();

    static {
        DOW_DAY_MAP.put("1", "Mon");
        DOW_DAY_MAP.put("2", "Tue");
        DOW_DAY_MAP.put("3", "Wed");
        DOW_DAY_MAP.put("4", "Thu");
        DOW_DAY_MAP.put("5", "Fri");
        DOW_DAY_MAP.put("6", "Sat");
        DOW_DAY_MAP.put("7", "Sun");
    }

    public SimpleStringProperty dow = new SimpleStringProperty();

    public SimpleStringProperty day = new SimpleStringProperty();

    public SimpleIntegerProperty count = new SimpleIntegerProperty(0);

    public SimpleStringProperty percentage = new SimpleStringProperty();

    public String getDow() {
        return dow.getValue();
    }

    public String getDay() {
        return day.getValue();
    }

    public Integer getCount() {
        return count.getValue();
    }

    public String getPercentage() {
        return percentage.getValue();
    }
}
