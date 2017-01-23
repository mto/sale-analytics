package com.fiverr.sanalytics.service;

import com.fiverr.sanalytics.jfx.model.DOMSale;
import com.fiverr.sanalytics.jfx.model.DPSale;
import com.fiverr.sanalytics.util.DateUtil;
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

    private List<DOMSale> domSales = new ArrayList<DOMSale>();
    
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

                String fsd = result.getString("first_sale_date");
                dpsRecord.firstSaleDate.set(fsd);
                dpsRecord.firstSaleAmount.set(result.getInt("first_sale_date_amount"));

                String ssd = result.getString("second_sale_date");
                dpsRecord.secondSaleDate.set(ssd);
                dpsRecord.secondSaleAmount.set(result.getInt("second_sale_date_amount"));

                String tsd = result.getString("third_sale_date");
                dpsRecord.thirdSaleDate.set(tsd);
                dpsRecord.thirdSaleAmount.set(result.getInt("third_sale_date_amount"));

                String fosd = result.getString("fourth_sale_date");
                dpsRecord.fourthSaleDate.set(fosd);
                dpsRecord.fourthSaleAmount.set(result.getInt("fourth_sale_date_amount"));

                String fisd = result.getString("fifth_sale_date");
                dpsRecord.fifthSaleDate.set(fisd);
                dpsRecord.fifthSaleAmount.set(result.getInt("fifth_sale_date_amount"));

                dpSales.add(dpsRecord);

                DOMSale doms = new DOMSale();
                doms.firstSaleDom.set(DateUtil.extractDOM(fsd));
                doms.secondSaleDom.set(DateUtil.extractDOM(ssd));
                doms.thirdSaleDom.set(DateUtil.extractDOM(tsd));
                doms.fourthSaleDom.set(DateUtil.extractDOM(fosd));
                doms.fifthSaleDom.set(DateUtil.extractDOM(fisd));

                domSales.add(doms);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<DPSale> getDPSales(){
        loadDPSales();

        return Collections.unmodifiableList(dpSales);
    }

    public List<DOMSale> getDOMSales(){
        loadDPSales();

        return Collections.unmodifiableList(domSales);
    }
}
