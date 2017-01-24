package com.cca.sanalytics.jfx.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DOWSale implements ExcelRow {

    public SimpleIntegerProperty index = new SimpleIntegerProperty();

    public SimpleStringProperty firstSaleDow = new SimpleStringProperty();

    public SimpleStringProperty secondSaleDow = new SimpleStringProperty();

    public SimpleStringProperty thirdSaleDow = new SimpleStringProperty();

    public SimpleStringProperty fourthSaleDow = new SimpleStringProperty();

    public SimpleStringProperty fifthSaleDow = new SimpleStringProperty();

    public String getFirstSaleDow() {
        return firstSaleDow.getValue();
    }

    public String getSecondSaleDow() {
        return secondSaleDow.getValue();
    }

    public String getThirdSaleDow() {
        return thirdSaleDow.getValue();
    }

    public String getFourthSaleDow() {
        return fourthSaleDow.getValue();
    }

    public String getFifthSaleDow() {
        return fifthSaleDow.getValue();
    }

    public Integer getIndex() {
        return index.getValue();
    }

    @Override
    public String[] getCells() {
        return new String[]
                {
                        getFirstSaleDow(),
                        getSecondSaleDow(),
                        getThirdSaleDow(),
                        getFourthSaleDow(),
                        getFifthSaleDow()
                };
    }
}
