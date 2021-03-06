package com.cca.sanalytics.jfx.view;

import com.cca.sanalytics.jfx.model.DOWSale;
import com.cca.sanalytics.util.ExcelUtil;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/23/17
 */
public class DOWSaleView extends TableView<DOWSale> implements Exportable {

    public DOWSaleView() {
        super();

        TableColumn<DOWSale, Integer> nbColumn = new TableColumn<DOWSale, Integer>("NO");
        TableColumn<DOWSale, String> dow1 = new TableColumn<DOWSale, String>("DOW1");
        TableColumn<DOWSale, String> dow2 = new TableColumn<DOWSale, String>("DOW2");
        TableColumn<DOWSale, String> dow3 = new TableColumn<DOWSale, String>("DOW3");
        TableColumn<DOWSale, String> dow4 = new TableColumn<DOWSale, String>("DOW4");
        TableColumn<DOWSale, String> dow5 = new TableColumn<DOWSale, String>("DOW5");

        nbColumn.setCellValueFactory(new PropertyValueFactory<DOWSale, Integer>("index"));
        dow1.setCellValueFactory(new PropertyValueFactory<DOWSale, String>("firstSaleDow"));
        dow2.setCellValueFactory(new PropertyValueFactory<DOWSale, String>("secondSaleDow"));
        dow3.setCellValueFactory(new PropertyValueFactory<DOWSale, String>("thirdSaleDow"));
        dow4.setCellValueFactory(new PropertyValueFactory<DOWSale, String>("fourthSaleDow"));
        dow5.setCellValueFactory(new PropertyValueFactory<DOWSale, String>("fifthSaleDow"));

        getColumns().addAll(nbColumn, dow1, dow2, dow3, dow4, dow5);
    }

    @Override
    public void exportToFile(File f) {
        ExcelUtil.writeToExcel("Green", this, f);
    }
}
