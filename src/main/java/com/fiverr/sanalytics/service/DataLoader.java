package com.fiverr.sanalytics.service;

import com.fiverr.sanalytics.jfx.model.DPSale;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/22/17
 */
public class DataLoader {

    private static volatile DataLoader instance;

    private AtomicBoolean dpSalesLoaded = new AtomicBoolean(false);

    private final Object lock = new Object();

    private final ComboPooledDataSource comboDS;

    private List<DPSale> dpSales = new ArrayList<DPSale>();

    private DataLoader(){
        comboDS = new ComboPooledDataSource();
        comboDS.setJdbcUrl("jdbc:mysql://localhost:3306/sale_analytics");
        comboDS.setUser("maustin");
        comboDS.setPassword("123456");
        comboDS.setInitialPoolSize(5);
        comboDS.setMinPoolSize(5);
        comboDS.setMaxPoolSize(10);
        comboDS.setMaxStatements(100);
    }

    public static DataLoader getInstance(){
        if(instance == null){
            synchronized (DataLoader.class){
                if(instance == null){
                    DataLoader tmp = new DataLoader();
                    instance = tmp;
                }
            }
        }

        return instance;
    }

    public void loadDPSales(){
        if(!dpSalesLoaded.get()){
            synchronized (lock){
                if(!dpSalesLoaded.get()){
                    _loadDPSales();
                    dpSalesLoaded.set(true);
                }
            }
        }
    }

    private void _loadDPSales(){
        Connection conn;
        PreparedStatement pstmt;
        ResultSet result;

        try{
            conn = comboDS.getConnection();
            pstmt = conn.prepareStatement("select * from dealer_part_sales");

            result = pstmt.executeQuery();

            while(result.next()){
                DPSale dpsRecord = new DPSale();
                dpsRecord.dealerNumber.set(result.getString("dealer_number"));
                dpsRecord.partNumber.set(result.getString("part_number"));
                dpsRecord.firstSaleDate.set(result.getString("first_sale_date"));
                dpsRecord.firstSaleAmount.set(result.getInt("first_sale_date_amount"));

                dpsRecord.secondSaleDate.set(result.getString("second_sale_date"));
                dpsRecord.secondSaleAmount.set(result.getInt("second_sale_date_amount"));

                dpsRecord.thirdSaleDate.set(result.getString("third_sale_date"));
                dpsRecord.thirdSaleAmount.set(result.getInt("third_sale_date_amount"));

                dpsRecord.fourthSaleDate.set(result.getString("fourth_sale_date"));
                dpsRecord.fourthSaleAmount.set(result.getInt("fourth_sale_date_amount"));

                dpsRecord.fifthSaleDate.set(result.getString("fifth_sale_date"));
                dpsRecord.fifthSaleAmount.set(result.getInt("fifth_sale_date_amount"));

                dpSales.add(dpsRecord);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<DPSale> getDPSales(){
        loadDPSales();

        return Collections.unmodifiableList(dpSales);
    }
}
