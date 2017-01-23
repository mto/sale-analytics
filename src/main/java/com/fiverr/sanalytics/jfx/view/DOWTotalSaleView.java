package com.fiverr.sanalytics.jfx.view;

import com.fiverr.sanalytics.jfx.model.DOWTotalSale;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DOWTotalSaleView extends TableView<DOWTotalSale> implements Exportable {

    public DOWTotalSaleView(){
        super();

        TableColumn<DOWTotalSale, String> dow = new TableColumn<DOWTotalSale, String>("DOW");
        TableColumn<DOWTotalSale, String> day = new TableColumn<DOWTotalSale, String>("Day");
        TableColumn<DOWTotalSale, Integer> count = new TableColumn<DOWTotalSale, Integer>("COUNT");
        TableColumn<DOWTotalSale, String> percentage = new TableColumn<DOWTotalSale, String>("%");

        dow.setCellValueFactory(new PropertyValueFactory<DOWTotalSale, String>("dow"));
        day.setCellValueFactory(new PropertyValueFactory<DOWTotalSale, String>("day"));
        count.setCellValueFactory(new PropertyValueFactory<DOWTotalSale, Integer>("count"));
        percentage.setCellValueFactory(new PropertyValueFactory<DOWTotalSale, String>("percentage"));

        getColumns().addAll(dow, day, count, percentage);
    }

    @Override
    public void exportToFile(File f) {
        
    }
}
