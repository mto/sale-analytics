package com.fiverr.sanalytics;

import com.fiverr.sanalytics.jfx.model.DPSale;
import com.fiverr.sanalytics.service.DataLoader;
import com.fiverr.sanalytics.jfx.view.DPSaleView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/22/17
 */
public class SAnalytics extends Application {

    private final DataLoader dbLoader;

    public SAnalytics(){
        super();

        dbLoader = DataLoader.getInstance();
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Sale Analytics");

        TabPane tabPane = new TabPane();

        Tab dailyTab = new Tab("Daily");

        final DPSaleView dpsv = new DPSaleView();
        final ObservableList<DPSale> dpsvData = FXCollections.observableArrayList(dbLoader.getDPSales());
        dpsv.setItems(dpsvData);

        Button dpsvButton = new Button("Export");
        dpsvButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Export data to Excel");
                File f = fc.showSaveDialog(primaryStage);
                if(f!= null && f.getName().endsWith(".xlsx")){
                    dpsv.exportToFile(f);
                }
            }
        });

        VBox dlBox = new VBox();
        dlBox.setSpacing(5);
        dlBox.setPadding(new Insets(10, 0, 0, 10));
        dlBox.getChildren().addAll(dpsv, dpsvButton);

        dailyTab.setContent(dlBox);




        Tab finalTab = new Tab("Final");
        Tab blueTab = new Tab("Blue");
        Tab redTab = new Tab("Red");
        Tab greenTab = new Tab("Green");
        Tab yellowTab = new Tab("Yellow");

        tabPane.getTabs().addAll(dailyTab, finalTab, blueTab, redTab, greenTab, yellowTab);

        Group root = new Group();
        Scene scene = new Scene(root, 1500, 600);

        BorderPane bdPane = new BorderPane();

        bdPane.prefHeightProperty().bind(scene.heightProperty());
        bdPane.prefWidthProperty().bind(scene.widthProperty());
        bdPane.setCenter(tabPane);

        root.getChildren().add(bdPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}