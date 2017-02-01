package com.cca.sanalytics;

import com.cca.sanalytics.jfx.model.DOMTotalSale;
import com.cca.sanalytics.jfx.model.DOWSale;
import com.cca.sanalytics.jfx.model.DOWTotalSale;
import com.cca.sanalytics.jfx.model.DPSale;
import com.cca.sanalytics.jfx.model.FinalData;
import com.cca.sanalytics.jfx.view.DOWSaleView;
import com.cca.sanalytics.jfx.view.DOWTotalSaleView;
import com.cca.sanalytics.jfx.view.DPSaleView;
import com.cca.sanalytics.jfx.view.FinalView;
import com.cca.sanalytics.jfx.model.DOMSale;
import com.cca.sanalytics.jfx.view.DOMSaleView;
import com.cca.sanalytics.jfx.view.DOMTotalSaleView;
import com.cca.sanalytics.service.DataLoader;
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
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Export data to Excel");
                File f = fc.showSaveDialog(primaryStage);
                dpsv.exportToFile(f);
            }
        });

        VBox dlBox = new VBox();
        dlBox.setSpacing(5);
        dlBox.setPadding(new Insets(10, 0, 0, 10));
        dlBox.getChildren().addAll(dpsv, dpsvButton);
        dailyTab.setContent(dlBox);

        /* Setup Final tab */
        Tab fnTab = new Tab("Final");
        final FinalView fnv = new FinalView();
        final ObservableList<FinalData> fdatas = FXCollections.observableArrayList(dbLoader.getFinalDatas().values());
        fnv.setItems(fdatas);

        Button fnvButton = new Button("Export");
        fnvButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Export data to Excel");
                File f = fc.showSaveDialog(primaryStage);
                fnv.exportToFile(f);
            }
        });

        VBox fnBox = new VBox();
        fnBox.setSpacing(5);
        fnBox.setPadding(new Insets(10, 0, 0, 10));
        fnBox.getChildren().addAll(fnv, fnvButton);
        fnTab.setContent(fnBox);

        /* Setup Blue tab */
        Tab blueTab = new Tab("DOM Sales");
        final DOMTotalSaleView domttsv = new DOMTotalSaleView();
        final ObservableList<DOMTotalSale> domttsvData = FXCollections.observableArrayList(dbLoader.getDOMTotalSales().values());
        domttsv.setItems(domttsvData);

        Button domttsvButton = new Button("Export");
        domttsvButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Export data to Excel");
                File f = fc.showSaveDialog(primaryStage);
                domttsv.exportToFile(f);
            }
        });

        VBox blueBox = new VBox();
        blueBox.setSpacing(5);
        blueBox.setPadding(new Insets(10, 0, 0, 10));
        blueBox.getChildren().addAll(domttsv, domttsvButton);

        blueTab.setContent(blueBox);


        /* Setup Red tab */
        Tab redTab = new Tab("DOW Sales");
        final DOWTotalSaleView dowttsv = new DOWTotalSaleView();
        final ObservableList<DOWTotalSale> dowttsvData = FXCollections.observableArrayList(dbLoader.getDOWTotalSales().values());
        dowttsv.setItems(dowttsvData);

        Button dowttsvButton = new Button("Export");
        dowttsvButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Export data to Excel");
                File f = fc.showSaveDialog(primaryStage);
                dowttsv.exportToFile(f);
            }
        });

        VBox redBox = new VBox();
        redBox.setSpacing(5);
        redBox.setPadding(new Insets(10, 0, 0, 10));
        redBox.getChildren().addAll(dowttsv, dowttsvButton);

        redTab.setContent(redBox);

        
        /* Setup Green tab */
        /*
        Tab greenTab = new Tab("Green");
        final DOWSaleView dowsv = new DOWSaleView();
        final ObservableList<DOWSale> dowsvData = FXCollections.observableArrayList(dbLoader.getDOWSales());
        dowsv.setItems(dowsvData);

        Button dowsvButton = new Button("Export");
        dowsvButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Export data to Excel");
                File f = fc.showSaveDialog(primaryStage);
                dowsv.exportToFile(f);
            }
        });

        VBox greenBox = new VBox();
        greenBox.setSpacing(5);
        greenBox.setPadding(new Insets(10, 0, 0, 10));
        greenBox.getChildren().addAll(dowsv, dowsvButton);

        greenTab.setContent(greenBox);
        */

        /* Setup Yello tab */
        /*
        Tab yellowTab = new Tab("Yellow");
        final DOMSaleView domsv = new DOMSaleView();
        final ObservableList<DOMSale> domsvData = FXCollections.observableArrayList(dbLoader.getDOMSales());
        domsv.setItems(domsvData);

        Button domsvButton = new Button("Export");
        domsvButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Export data to Excel");
                File f = fc.showSaveDialog(primaryStage);
                domsv.exportToFile(f);
            }
        });

        VBox ylBox = new VBox();
        ylBox.setSpacing(5);
        ylBox.setPadding(new Insets(10, 0, 0, 10));
        ylBox.getChildren().addAll(domsv, domsvButton);
        yellowTab.setContent(ylBox);
        */

        //tabPane.getTabs().addAll(dailyTab, fnTab, blueTab, redTab, greenTab, yellowTab);
        tabPane.getTabs().addAll(dailyTab, fnTab, blueTab, redTab);

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
