package com.cca.sanalytics.jfx.view;

import com.cca.sanalytics.jfx.model.FinalData;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
        TableColumn<FinalData, Integer> daysInMon = new TableColumn<FinalData, Integer>("Days/Mon");
        TableColumn<FinalData, String> pcInMon = new TableColumn<FinalData, String>("% in Month");
        TableColumn<FinalData, String> monYearKey = new TableColumn<FinalData, String>("Mo-Year-Key");
        TableColumn<FinalData, String> cumPercent = new TableColumn<FinalData, String>("Cumulative %");

        getColumns().addAll(date, dom, dow, daysInMon, pcInMon, monYearKey, cumPercent);
    }

    @Override
    public void exportToFile(File f) {

    }
}
