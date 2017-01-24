package com.fiverr.sanalytics.jfx.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DOMSale implements ExcelRow {

    public SimpleIntegerProperty index = new SimpleIntegerProperty();

    public SimpleStringProperty firstSaleDom = new SimpleStringProperty();

    public SimpleStringProperty secondSaleDom = new SimpleStringProperty();

    public SimpleStringProperty thirdSaleDom = new SimpleStringProperty();

    public SimpleStringProperty fourthSaleDom = new SimpleStringProperty();

    public SimpleStringProperty fifthSaleDom = new SimpleStringProperty();

    public String getFirstSaleDom() {
        return firstSaleDom.getValue();
    }

    public String getSecondSaleDom() {
        return secondSaleDom.getValue();
    }

    public String getThirdSaleDom() {
        return thirdSaleDom.getValue();
    }

    public String getFourthSaleDom() {
        return fourthSaleDom.getValue();
    }

    public String getFifthSaleDom() {
        return fifthSaleDom.getValue();
    }

    public Integer getIndex() {
        return index.getValue();
    }

    @Override
    public String[] getCells() {
        return new String[]{
                getFirstSaleDom(),
                getSecondSaleDom(),
                getThirdSaleDom(),
                getFourthSaleDom(),
                getFifthSaleDom()
        };
    }
}
