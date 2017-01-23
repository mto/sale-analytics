package com.fiverr.sanalytics.jfx.view;

import com.fiverr.sanalytics.jfx.model.DPSale;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;


/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/22/17
 */
public class DPSaleView extends TableView<DPSale> implements Exportable {

    public DPSaleView() {
        super();

        TableColumn<DPSale, String> dnColumn = new TableColumn<DPSale, String>("DEALER_NO");
        TableColumn<DPSale, String> pnColumn = new TableColumn<DPSale, String>("PART_NO");
        TableColumn<DPSale, String> firstSdColumn = new TableColumn<DPSale, String>("SALES_DATE1");
        TableColumn<DPSale, Integer> firstSdaColumn = new TableColumn<DPSale, Integer>("SALES_AMOUNT1");
        TableColumn<DPSale, String> secondSdColumn = new TableColumn<DPSale, String>("SALES_DATE2");
        TableColumn<DPSale, Integer> secondSdaColumn = new TableColumn<DPSale, Integer>("SALES_AMOUNT2");
        TableColumn<DPSale, String> thirdSdColumn = new TableColumn<DPSale, String>("SALES_DATE3");
        TableColumn<DPSale, Integer> thirdSdaColumn = new TableColumn<DPSale, Integer>("SALES_AMOUNT3");
        TableColumn<DPSale, String> fourthSdColumn = new TableColumn<DPSale, String>("SALES_DATE4");
        TableColumn<DPSale, Integer> fourthSdaColumn = new TableColumn<DPSale, Integer>("SALES_AMOUNT4");
        TableColumn<DPSale, String> fifthSdColumn = new TableColumn<DPSale, String>("SALES_DATE5");
        TableColumn<DPSale, Integer> fifthSdaColumn = new TableColumn<DPSale, Integer>("SALES_AMOUNT5");

        dnColumn.setCellValueFactory(new PropertyValueFactory<DPSale, String>("dealerNumber"));
        pnColumn.setCellValueFactory(new PropertyValueFactory<DPSale, String>("partNumber"));
        firstSdColumn.setCellValueFactory(new PropertyValueFactory<DPSale, String>("firstSaleDate"));
        firstSdaColumn.setCellValueFactory(new PropertyValueFactory<DPSale, Integer>("firstSaleAmount"));

        secondSdColumn.setCellValueFactory(new PropertyValueFactory<DPSale, String>("secondSaleDate"));
        secondSdaColumn.setCellValueFactory(new PropertyValueFactory<DPSale, Integer>("secondSaleAmount"));

        thirdSdColumn.setCellValueFactory(new PropertyValueFactory<DPSale, String>("thirdSaleDate"));
        thirdSdaColumn.setCellValueFactory(new PropertyValueFactory<DPSale, Integer>("thirdSaleAmount"));

        fourthSdColumn.setCellValueFactory(new PropertyValueFactory<DPSale, String>("fourthSaleDate"));
        fourthSdaColumn.setCellValueFactory(new PropertyValueFactory<DPSale, Integer>("fourthSaleAmount"));

        fifthSdColumn.setCellValueFactory(new PropertyValueFactory<DPSale, String>("fifthSaleDate"));
        fifthSdaColumn.setCellValueFactory(new PropertyValueFactory<DPSale, Integer>("fifthSaleAmount"));

        getColumns().addAll(dnColumn, pnColumn, firstSdColumn, firstSdaColumn,
                secondSdColumn, secondSdaColumn, thirdSdColumn, thirdSdaColumn,
                fourthSdColumn, fourthSdaColumn, fifthSdColumn, fifthSdaColumn);

    }

    @Override
    public void exportToFile(File f) {
        System.out.println("Export data to file " + f.getName());

    }
}
