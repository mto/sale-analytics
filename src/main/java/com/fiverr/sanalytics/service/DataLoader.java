package com.fiverr.sanalytics.service;

import com.fiverr.sanalytics.jfx.model.DOMSale;
import com.fiverr.sanalytics.jfx.model.DOMTotalSale;
import com.fiverr.sanalytics.jfx.model.DOWSale;
import com.fiverr.sanalytics.jfx.model.DOWTotalSale;
import com.fiverr.sanalytics.jfx.model.DPSale;
import com.fiverr.sanalytics.util.DateUtil;
import com.fiverr.sanalytics.util.StringUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    private final List<DPSale> dpSales = new ArrayList<DPSale>();

    private final List<DOMSale> domSales = new ArrayList<DOMSale>();

    private final List<DOWSale> dowSales = new ArrayList<DOWSale>();

    private final Map<String, DOWTotalSale> dowTotalSales = new HashMap<String, DOWTotalSale>();

    private final Map<String, DOMTotalSale> domTotalSales = new TreeMap<String, DOMTotalSale>(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            try {
                int n1 = Integer.parseInt(o1);
                int n2 = Integer.parseInt(o2);

                return n1 - n2;
            } catch (Exception ex) {
                return 0;
            }
        }
    });

    private DataLoader() {
        comboDS = new ComboPooledDataSource();
        comboDS.setJdbcUrl("jdbc:mysql://localhost:3306/sale_analytics");
        comboDS.setUser("maustin");
        comboDS.setPassword("123456");
        comboDS.setInitialPoolSize(5);
        comboDS.setMinPoolSize(5);
        comboDS.setMaxPoolSize(10);
        comboDS.setMaxStatements(100);
    }

    public static DataLoader getInstance() {
        if (instance == null) {
            synchronized (DataLoader.class) {
                if (instance == null) {
                    DataLoader tmp = new DataLoader();
                    instance = tmp;
                }
            }
        }

        return instance;
    }

    public void loadDPSales() {
        if (!dpSalesLoaded.get()) {
            synchronized (lock) {
                if (!dpSalesLoaded.get()) {
                    _loadDPSales();
                    dpSalesLoaded.set(true);
                }
            }
        }
    }

    private void _loadDPSales() {
        Connection conn;
        PreparedStatement pstmt;
        ResultSet result;

        try {
            conn = comboDS.getConnection();
            pstmt = conn.prepareStatement("select * from dealer_part_sales");

            result = pstmt.executeQuery();

            int index = 0;

            while (result.next()) {
                index++;

                DPSale dpsRecord = new DPSale();
                dpsRecord.index.set(index);
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
                doms.index.set(index);
                doms.firstSaleDom.set(DateUtil.extractDOM(fsd));
                doms.secondSaleDom.set(DateUtil.extractDOM(ssd));
                doms.thirdSaleDom.set(DateUtil.extractDOM(tsd));
                doms.fourthSaleDom.set(DateUtil.extractDOM(fosd));
                doms.fifthSaleDom.set(DateUtil.extractDOM(fisd));

                domSales.add(doms);

                String fsdow = DateUtil.extractDOW(fsd);
                String ssdow = DateUtil.extractDOW(ssd);
                String tsdow = DateUtil.extractDOW(tsd);
                String fosdow = DateUtil.extractDOW(fosd);
                String fisdow = DateUtil.extractDOW(fisd);

                DOWSale dows = new DOWSale();
                dows.index.set(index);
                dows.firstSaleDow.set(fsdow);
                dows.secondSaleDow.set(ssdow);
                dows.thirdSaleDow.set(tsdow);
                dows.fourthSaleDow.set(fosdow);
                dows.fifthSaleDow.set(fisdow);

                dowSales.add(dows);

                if (StringUtil.notNullOrEmpty(fsdow)) {
                    DOWTotalSale dowtts = getDOWTotalSale(fsdow);
                    try {
                        int count = dowtts.getCount() + result.getInt("first_sale_date_amount");
                        dowtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (StringUtil.notNullOrEmpty(ssdow)) {
                    DOWTotalSale dowtts = getDOWTotalSale(ssdow);
                    try {
                        int count = dowtts.getCount() + result.getInt("second_sale_date_amount");
                        dowtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                if (StringUtil.notNullOrEmpty(tsdow)) {
                    DOWTotalSale dowtts = getDOWTotalSale(tsdow);
                    try {
                        int count = dowtts.getCount() + result.getInt("third_sale_date_amount");
                        dowtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                if (StringUtil.notNullOrEmpty(fosdow)) {
                    DOWTotalSale dowtts = getDOWTotalSale(fosdow);
                    try {
                        int count = dowtts.getCount() + result.getInt("fourth_sale_date_amount");
                        dowtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                if (StringUtil.notNullOrEmpty(fisdow)) {
                    DOWTotalSale dowtts = getDOWTotalSale(fisdow);
                    try {
                        int count = dowtts.getCount() + result.getInt("fifth_sale_date_amount");
                        dowtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                String fsdom = DateUtil.extractDOM(fsd);
                String ssdom = DateUtil.extractDOM(ssd);
                String tsdom = DateUtil.extractDOM(tsd);
                String fosdom = DateUtil.extractDOM(fosd);
                String fisdom = DateUtil.extractDOM(fisd);

                if (StringUtil.notNullOrEmpty(fsdom)) {
                    DOMTotalSale domtts = getDOMTotalSale(fsdom);
                    try {
                        int count = domtts.getCount() + result.getInt("first_sale_date_amount");
                        domtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (StringUtil.notNullOrEmpty(ssdom)) {
                    DOMTotalSale domtts = getDOMTotalSale(ssdom);
                    try {
                        int count = domtts.getCount() + result.getInt("second_sale_date_amount");
                        domtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (StringUtil.notNullOrEmpty(tsdom)) {
                    DOMTotalSale domtts = getDOMTotalSale(tsdom);
                    try {
                        int count = domtts.getCount() + result.getInt("third_sale_date_amount");
                        domtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (StringUtil.notNullOrEmpty(fosdom)) {
                    DOMTotalSale domtts = getDOMTotalSale(fosdom);
                    try {
                        int count = domtts.getCount() + result.getInt("fourth_sale_date_amount");
                        domtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (StringUtil.notNullOrEmpty(fisdom)) {
                    DOMTotalSale domtts = getDOMTotalSale(fisdom);
                    try {
                        int count = domtts.getCount() + result.getInt("fifth_sale_date_amount");
                        domtts.count.set(count);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            computeDOWTTSPercentage();
            computeDOMTTSPercentage();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void computeDOWTTSPercentage() {
        int sumOfDOWtts = 0;
        for (DOWTotalSale v : dowTotalSales.values()) {
            sumOfDOWtts += v.getCount();
        }

        for (int i = 1; i < 8; i++) {
            DOWTotalSale dts = dowTotalSales.get("" + i);
            double pct = Math.round(dts.getCount() * 7 * 100.0) / sumOfDOWtts;

            dts.percentage.set("" + pct + "%");
        }
    }

    private void computeDOMTTSPercentage() {
        int sum28dm = 0;
        int sum29dm = 0;
        int sum30dm = 0;
        int sum31dm = 0;

        for (int i = 1; i < 32; i++) {
            DOMTotalSale domtts = domTotalSales.get("" + i);

            sum28dm += (i <= 28) ? domtts.getCount() : 0;
            sum29dm += (i <= 29) ? domtts.getCount() : 0;
            sum30dm += (i <= 30) ? domtts.getCount() : 0;
            sum31dm += (i <= 31) ? domtts.getCount() : 0;
        }

        for (int i = 1; i < 32; i++) {
            DOMTotalSale domtts = domTotalSales.get("" + i);

            double pct28dm = Math.round(domtts.getCount() * 28 * 100.0) / sum28dm;
            double pct29dm = Math.round(domtts.getCount() * 29 * 100.0) / sum29dm;
            double pct30dm = Math.round(domtts.getCount() * 30 * 100.0) / sum30dm;
            double pct31dm = Math.round(domtts.getCount() * 31 * 100.0) / sum31dm;

            domtts.tteightDayMo.set((i <= 28) ? "" + pct28dm + "%" : "");
            domtts.ttnineDayMo.set((i <= 29) ? "" + pct29dm + "%" : "");
            domtts.thrdtDayMo.set((i <= 30) ? "" + pct30dm + "%" : "");
            domtts.thrdtoneDayMo.set((i <= 31) ? "" + pct31dm + "%" : "");
        }
    }

    private DOWTotalSale getDOWTotalSale(String dow) {
        DOWTotalSale dowtts = dowTotalSales.get(dow);

        if (dowtts == null) {
            dowtts = new DOWTotalSale();
            dowtts.dow.set(dow);
            dowtts.day.set(DOWTotalSale.DOW_DAY_MAP.get(dow));

            dowTotalSales.put(dow, dowtts);
        }

        return dowtts;
    }

    private DOMTotalSale getDOMTotalSale(String dom) {
        DOMTotalSale domtts = domTotalSales.get(dom);

        if (domtts == null) {
            domtts = new DOMTotalSale();
            domtts.dom.set(dom);
            domtts.monthsWith.set("12");

            domTotalSales.put(dom, domtts);
        }

        return domtts;
    }

    public List<DPSale> getDPSales() {
        loadDPSales();

        return Collections.unmodifiableList(dpSales);
    }

    public List<DOMSale> getDOMSales() {
        loadDPSales();

        return Collections.unmodifiableList(domSales);
    }

    public List<DOWSale> getDOWSales() {
        loadDPSales();

        return Collections.unmodifiableList(dowSales);
    }

    public Map<String, DOWTotalSale> getDOWTotalSales() {
        loadDPSales();

        return Collections.unmodifiableMap(dowTotalSales);
    }

    public Map<String, DOMTotalSale> getDOMTotalSales() {
        loadDPSales();

        return Collections.unmodifiableMap(domTotalSales);
    }
}
