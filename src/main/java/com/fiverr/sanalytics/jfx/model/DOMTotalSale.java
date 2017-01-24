package com.fiverr.sanalytics.jfx.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/24/17
 */
public class DOMTotalSale {

    public SimpleStringProperty dom = new SimpleStringProperty();

    public SimpleIntegerProperty count = new SimpleIntegerProperty(0);

    public SimpleStringProperty monthsWith = new SimpleStringProperty();

    public SimpleIntegerProperty adjusted = new SimpleIntegerProperty(0);

    public SimpleStringProperty tteightDayMo = new SimpleStringProperty();

    public SimpleStringProperty ttnineDayMo = new SimpleStringProperty();

    public SimpleStringProperty thrdtDayMo = new SimpleStringProperty();

    public SimpleStringProperty thrdtoneDayMo = new SimpleStringProperty();

    public String getDom() {
        return dom.get();
    }

    public Integer getCount() {
        return count.get();
    }

    public String getMonthsWith() {
        return monthsWith.get();
    }

    public Integer getAdjusted() {
        return adjusted.get();
    }

    public String getTteightDayMo() {
        return tteightDayMo.get();
    }

    public String getTtnineDayMo() {
        return ttnineDayMo.get();
    }

    public String getThrdtDayMo() {
        return thrdtDayMo.get();
    }

    public String getThrdtoneDayMo() {
        return thrdtoneDayMo.get();
    }
}
