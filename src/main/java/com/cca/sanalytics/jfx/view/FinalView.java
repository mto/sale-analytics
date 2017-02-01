package com.cca.sanalytics.jfx.view;

import com.cca.sanalytics.jfx.model.FinalData;
import com.cca.sanalytics.util.ExcelUtil;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/24/17
 */
public class FinalView extends TableView<FinalData> implements Exportable {

    public FinalView(){
        super();

        TableColumn<FinalData, String> date = new TableColumn<FinalData, String>("Date");
        TableColumn<FinalData, String> dom = new TableColumn<FinalData, String>("DOM");
        TableColumn<FinalData, String> dow = new TableColumn<FinalData, String>("DOW");
        TableColumn<FinalData, String> daysInMon = new TableColumn<FinalData, String>("Days/Mon");
        TableColumn<FinalData, String> pcInMon = new TableColumn<FinalData, String>("PercentInMonth");
        TableColumn<FinalData, String> daySequence = new TableColumn<FinalData, String>("DaySequence");
        TableColumn<FinalData, String> monYearKey = new TableColumn<FinalData, String>("MonthYearKey");
        TableColumn<FinalData, String> cumPercent = new TableColumn<FinalData, String>("CumulativePercentage");

        date.setCellValueFactory(new PropertyValueFactory<FinalData, String>("date"));
        dom.setCellValueFactory(new PropertyValueFactory<FinalData, String>("dom"));
        dow.setCellValueFactory(new PropertyValueFactory<FinalData, String>("dow"));
        daysInMon.setCellValueFactory(new PropertyValueFactory<FinalData, String>("daysInMon"));
        pcInMon.setCellValueFactory(new PropertyValueFactory<FinalData, String>("percentageInMonth"));
        monYearKey.setCellValueFactory(new PropertyValueFactory<FinalData, String>("monYearKey"));
        cumPercent.setCellValueFactory(new PropertyValueFactory<FinalData, String>("cumulative"));


        getColumns().addAll(date, dom, dow, daysInMon, pcInMon, daySequence, monYearKey, cumPercent);
    }

    @Override
    public void exportToFile(File f) {
        ExcelUtil.writeToExcel("Final", this, f);
    }
}
