package com.fiverr.sanalytics.jfx.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DOWSale {

    public SimpleIntegerProperty firstSaleDOW = new SimpleIntegerProperty();

    public SimpleIntegerProperty secondSaleDOW = new SimpleIntegerProperty();

    public SimpleIntegerProperty thirdSaleDOW = new SimpleIntegerProperty();

    public SimpleIntegerProperty fourthSaleDOW = new SimpleIntegerProperty();

    public SimpleIntegerProperty fifthSaleDOW = new SimpleIntegerProperty();

    public Integer getFirstSaleDOW() {
        return firstSaleDOW.getValue();
    }

    public Integer getSecondSaleDOW() {
        return secondSaleDOW.getValue();
    }

    public Integer getThirdSaleDOW() {
        return thirdSaleDOW.getValue();
    }

    public Integer getFourthSaleDOW() {
        return fourthSaleDOW.getValue();
    }

    public Integer getFifthSaleDOW() {
        return fifthSaleDOW.getValue();
    }

}
