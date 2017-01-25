package com.cca.sanalytics.jfx.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/24/17
 */
public class FinalData implements ExcelRow {

    public SimpleStringProperty date = new SimpleStringProperty();

    public SimpleStringProperty dom = new SimpleStringProperty();

    public SimpleStringProperty dow = new SimpleStringProperty();

    public SimpleStringProperty daysInMon = new SimpleStringProperty();

    public SimpleStringProperty percentageInMonth = new SimpleStringProperty();

    public SimpleStringProperty monYearKey = new SimpleStringProperty();

    public SimpleStringProperty cumulative = new SimpleStringProperty();

    public int count = 0;

    public String getDate(){
        return date.getValue();
    }

    public String getDom(){
        return dom.getValue();
    }

    public String getDow(){
        return dow.getValue();
    }

    public String getDaysInMon(){
        return daysInMon.getValue();
    }

    public String getPercentageInMonth(){
        return percentageInMonth.getValue();
    }

    public String getMonYearKey(){
        return monYearKey.getValue();
    }

    public String getCumulative(){
        return cumulative.getValue();
    }

    @Override
    public String[] getCells() {
        return new String[]{
                getDate(),
                getDom(),
                getDow(),
                getDaysInMon(),
                getPercentageInMonth(),
                getMonYearKey(),
                getCumulative()
        };
    }
}
