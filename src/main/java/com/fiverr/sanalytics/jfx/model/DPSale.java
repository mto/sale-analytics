package com.fiverr.sanalytics.jfx.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/22/17
 */
public class DPSale {

    public SimpleIntegerProperty index = new SimpleIntegerProperty();

    public SimpleStringProperty dealerNumber = new SimpleStringProperty();

    public SimpleStringProperty partNumber = new SimpleStringProperty();

    public SimpleStringProperty firstSaleDate = new SimpleStringProperty();

    public SimpleStringProperty secondSaleDate = new SimpleStringProperty();

    public SimpleStringProperty thirdSaleDate = new SimpleStringProperty();

    public SimpleStringProperty fourthSaleDate = new SimpleStringProperty();

    public SimpleStringProperty fifthSaleDate = new SimpleStringProperty();

    public SimpleIntegerProperty firstSaleAmount = new SimpleIntegerProperty(0);

    public SimpleIntegerProperty secondSaleAmount = new SimpleIntegerProperty(0);

    public SimpleIntegerProperty thirdSaleAmount = new SimpleIntegerProperty(0);

    public SimpleIntegerProperty fourthSaleAmount = new SimpleIntegerProperty(0);

    public SimpleIntegerProperty fifthSaleAmount = new SimpleIntegerProperty(0);

    public SimpleIntegerProperty firstSaleDOM = new SimpleIntegerProperty();

    public SimpleIntegerProperty secondSaleDOM = new SimpleIntegerProperty();

    public SimpleIntegerProperty thirdSaleDOM = new SimpleIntegerProperty();

    public SimpleIntegerProperty fourthSaleDOM = new SimpleIntegerProperty();

    public SimpleIntegerProperty fifthSaleDOM = new SimpleIntegerProperty();

    public String getDealerNumber() {
        return dealerNumber.getValue();
    }

    public String getPartNumber() {
        return partNumber.getValue();
    }

    public String getFirstSaleDate() {
        return firstSaleDate.getValue();
    }

    public String getSecondSaleDate() {
        return secondSaleDate.getValue();
    }

    public String getThirdSaleDate() {
        return thirdSaleDate.getValue();
    }

    public String getFourthSaleDate() {
        return fourthSaleDate.getValue();
    }

    public String getFifthSaleDate() {
        return fifthSaleDate.getValue();
    }

    public Integer getFirstSaleAmount() {
        return firstSaleAmount.getValue();
    }

    public Integer getSecondSaleAmount() {
        return secondSaleAmount.getValue();
    }

    public Integer getThirdSaleAmount() {
        return thirdSaleAmount.getValue();
    }

    public Integer getFourthSaleAmount() {
        return fourthSaleAmount.getValue();
    }

    public Integer getFifthSaleAmount() {
        return fifthSaleAmount.getValue();
    }

    public Integer getIndex(){
        return index.getValue();
    }

}
