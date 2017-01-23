package com.fiverr.sanalytics.jfx.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DOMSale {

    public SimpleStringProperty firstSaleDOM = new SimpleStringProperty();

    public SimpleStringProperty secondSaleDOM = new SimpleStringProperty();

    public SimpleStringProperty thirdSaleDOM = new SimpleStringProperty();

    public SimpleStringProperty fourthSaleDOM = new SimpleStringProperty();

    public SimpleStringProperty fifthSaleDOM = new SimpleStringProperty();

    public String getFirstSaleDOM() {
        return firstSaleDOM.getValue();
    }

    public String getSecondSaleDOM() {
        return secondSaleDOM.getValue();
    }

    public String getThirdSaleDOM() {
        return thirdSaleDOM.getValue();
    }

    public String getFourthSaleDOM() {
        return fourthSaleDOM.getValue();
    }

    public String getFifthSaleDOM() {
        return fifthSaleDOM.getValue();
    }

}
