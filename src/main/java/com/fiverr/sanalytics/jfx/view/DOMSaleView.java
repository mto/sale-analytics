package com.fiverr.sanalytics.jfx.view;

import com.fiverr.sanalytics.jfx.model.DOMSale;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DOMSaleView extends TableView<DOMSale> implements Exportable {

    public DOMSaleView() {
        super();

        TableColumn<DOMSale, String> dow1 = new TableColumn<DOMSale, String>("DOM1");
        TableColumn<DOMSale, String> dow2 = new TableColumn<DOMSale, String>("DOM2");
        TableColumn<DOMSale, String> dow3 = new TableColumn<DOMSale, String>("DOM3");
        TableColumn<DOMSale, String> dow4 = new TableColumn<DOMSale, String>("DOM4");
        TableColumn<DOMSale, String> dow5 = new TableColumn<DOMSale, String>("DOM5");

        dow1.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("firstSaleDom"));
        dow2.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("secondSaleDom"));
        dow3.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("thirdSaleDom"));
        dow4.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("fourthSaleDom"));
        dow5.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("fifthSaleDom"));

        getColumns().addAll(dow1, dow2, dow3, dow4, dow5);
    }

    @Override
    public void exportToFile(File f) {

    }
}
