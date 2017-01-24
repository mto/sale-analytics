package com.cca.sanalytics.jfx.view;

import com.cca.sanalytics.jfx.model.DOMTotalSale;
import com.cca.sanalytics.util.ExcelUtil;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/24/17
 */
public class DOMTotalSaleView extends TableView<DOMTotalSale> implements Exportable {

    public DOMTotalSaleView(){
        super();

        TableColumn<DOMTotalSale, String> dom = new TableColumn<DOMTotalSale, String>("DOM");
        TableColumn<DOMTotalSale, Integer> count = new TableColumn<DOMTotalSale, Integer>("COUNT");
        TableColumn<DOMTotalSale, String> monthsWith = new TableColumn<DOMTotalSale, String>("Months with");
        TableColumn<DOMTotalSale, Integer> adjusted = new TableColumn<DOMTotalSale, Integer>("Adjusted");
        TableColumn<DOMTotalSale, String> pc28dm = new TableColumn<DOMTotalSale, String>("28 Day Mo");
        TableColumn<DOMTotalSale, String> pc29dm = new TableColumn<DOMTotalSale, String>("29 Day Mo");
        TableColumn<DOMTotalSale, String> pc30dm = new TableColumn<DOMTotalSale, String>("30 Day Mo");
        TableColumn<DOMTotalSale, String> pc31dm = new TableColumn<DOMTotalSale, String>("31 Day Mo");

        dom.setCellValueFactory(new PropertyValueFactory<DOMTotalSale, String>("dom"));
        count.setCellValueFactory(new PropertyValueFactory<DOMTotalSale, Integer>("count"));
        monthsWith.setCellValueFactory(new PropertyValueFactory<DOMTotalSale, String>("monthsWith"));
        pc28dm.setCellValueFactory(new PropertyValueFactory<DOMTotalSale, String>("tteightDayMo"));
        pc29dm.setCellValueFactory(new PropertyValueFactory<DOMTotalSale, String>("ttnineDayMo"));
        pc30dm.setCellValueFactory(new PropertyValueFactory<DOMTotalSale, String>("thrdtDayMo"));
        pc31dm.setCellValueFactory(new PropertyValueFactory<DOMTotalSale, String>("thrdtoneDayMo"));

        getColumns().addAll(dom, count, monthsWith, adjusted, pc28dm, pc29dm, pc30dm, pc31dm);
    }

    @Override
    public void exportToFile(File f) {
        ExcelUtil.writeToExcel("Blue", this, f);
    }
}
