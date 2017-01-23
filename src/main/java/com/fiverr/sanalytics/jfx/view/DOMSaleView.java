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

        TableColumn<DOMSale, String> dom1 = new TableColumn<DOMSale, String>("DOM1");
        TableColumn<DOMSale, String> dom2 = new TableColumn<DOMSale, String>("DOM2");
        TableColumn<DOMSale, String> dom3 = new TableColumn<DOMSale, String>("DOM3");
        TableColumn<DOMSale, String> dom4 = new TableColumn<DOMSale, String>("DOM4");
        TableColumn<DOMSale, String> dom5 = new TableColumn<DOMSale, String>("DOM5");

        dom1.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("firstSaleDom"));
        dom2.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("secondSaleDom"));
        dom3.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("thirdSaleDom"));
        dom4.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("fourthSaleDom"));
        dom5.setCellValueFactory(new PropertyValueFactory<DOMSale, String>("fifthSaleDom"));

        getColumns().addAll(dom1, dom2, dom3, dom4, dom5);
    }

    @Override
    public void exportToFile(File f) {

    }
}
