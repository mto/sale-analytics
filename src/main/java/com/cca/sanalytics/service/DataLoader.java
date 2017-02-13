package com.cca.sanalytics.service;

import com.cca.sanalytics.config.Configuration;
import com.cca.sanalytics.jfx.model.DOMSale;
import com.cca.sanalytics.jfx.model.DOMTotalSale;
import com.cca.sanalytics.jfx.model.DOWSale;
import com.cca.sanalytics.jfx.model.DOWTotalSale;
import com.cca.sanalytics.jfx.model.DPSale;
import com.cca.sanalytics.jfx.model.FinalData;
import com.cca.sanalytics.util.DateUtil;
import com.cca.sanalytics.util.StringUtil;
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


public class DataLoader {

    private static volatile DataLoader instance;

    private AtomicBoolean dpSalesLoaded = new AtomicBoolean(false);

    private final Object lock = new Object();

    private final ComboPooledDataSource comboDS;

    private final List<DPSale> dpSales = new ArrayList<>();

    private final List<DOMSale> domSales = new ArrayList<>();

    private final List<DOWSale> dowSales = new ArrayList<>();

    private final Map<String, Integer> mykTotalSale = new HashMap<>();

    private final Map<String, Map<String, FinalData>> mykDatas = new HashMap<>();

    private final Map<String, FinalData> finalDatas = new HashMap<>();

    private final Map<String, DOWTotalSale> dowTotalSales = new HashMap<>();

    private final Map<String, DOMTotalSale> domTotalSales = new TreeMap<>(new Comparator<String>() {
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
        Configuration config = Configuration.getInstance();

        comboDS.setJdbcUrl(config.getParam("jdbcURL", "jdbc:mysql://localhost:3306/sale_analytics"));
        comboDS.setUser(config.getParam("username", "maustin"));
        comboDS.setPassword(config.getParam("password", "maustin"));
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

    public void loadDPSales(String dealerNo, String schema) {
        if (!dpSalesLoaded.get()) {
            synchronized (lock) {
                if (!dpSalesLoaded.get()) {
                    _loadDPSales(dealerNo, schema);
                    dpSalesLoaded.set(true);
                }
            }
        }
    }

    private void _loadDPSales(String dealerNo, String schema) {

        try (Connection conn = comboDS.getConnection()) {
            String recentPartSalesQuery = "select PART_NO, SALES_DATE1, SALES_AMOUNT1, LOST_SALES_AMOUNT1, "
                    + "SALES_DATE2, SALES_AMOUNT2, LOST_SALES_AMOUNT2, "
                    + "SALES_DATE3, SALES_AMOUNT3, LOST_SALES_AMOUNT3, "
                    + "SALES_DATE4, SALES_AMOUNT4, LOST_SALES_AMOUNT4, "
                    + "SALES_DATE5, SALES_AMOUNT5, LOST_SALES_AMOUNT5 "
                    + "from %s.part_sale_tab "
                    + "where DEALER_NO = ? "
                    + "and IMPORTER_NO = 'USA' "
                    + "and MARKET = 'XX' ";
            recentPartSalesQuery = String.format(recentPartSalesQuery, schema);

            try (PreparedStatement pstmt = conn.prepareStatement(recentPartSalesQuery)) {
                pstmt.setString(1, dealerNo);

                try (ResultSet result = pstmt.executeQuery()) {

                    int index = 0;

                    while (result.next()) {
                        index++;

                        DPSale dpsRecord = new DPSale();
                        dpsRecord.index.set(index);
                        dpsRecord.dealerNumber.set(dealerNo);
                        dpsRecord.partNumber.set(result.getString("PART_NO"));

                        String fsd = result.getString("SALES_DATE1");
                        dpsRecord.firstSaleDate.set(fsd);
                        dpsRecord.firstSaleAmount.set(result.getInt("SALES_AMOUNT1"));

                        String ssd = result.getString("SALES_DATE2");
                        dpsRecord.secondSaleDate.set(ssd);
                        dpsRecord.secondSaleAmount.set(result.getInt("SALES_AMOUNT2"));

                        String tsd = result.getString("SALES_DATE3");
                        dpsRecord.thirdSaleDate.set(tsd);
                        dpsRecord.thirdSaleAmount.set(result.getInt("SALES_AMOUNT3"));

                        String fosd = result.getString("SALES_DATE4");
                        dpsRecord.fourthSaleDate.set(fosd);
                        dpsRecord.fourthSaleAmount.set(result.getInt("SALES_AMOUNT4"));

                        String fisd = result.getString("SALES_DATE5");
                        dpsRecord.fifthSaleDate.set(fisd);
                        dpsRecord.fifthSaleAmount.set(result.getInt("SALES_AMOUNT5"));

                        dpSales.add(dpsRecord);

                        extractFinalData(fsd, ssd, tsd, fosd, fisd, result);

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

                        extractDOWTTS(fsdow, ssdow, tsdow, fosdow, fisdow, result);

                        String fsdom = DateUtil.extractDOM(fsd);
                        String ssdom = DateUtil.extractDOM(ssd);
                        String tsdom = DateUtil.extractDOM(tsd);
                        String fosdom = DateUtil.extractDOM(fosd);
                        String fisdom = DateUtil.extractDOM(fisd);

                        extractDOMTTS(fsdom, ssdom, tsdom, fosdom, fisdom, result);
                    }

                    computeFinalData();
                    computeDOWTTSPercentage();
                    computeDOMTTSPercentage();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void extractFinalData(String fsd, String ssd, String tsd, String fosd, String fisd, final ResultSet result) {
        if (StringUtil.notNullOrEmpty(fsd)) {
            FinalData fd = getFinalData(fsd);

            try {
                fd.count += result.getInt("SALES_AMOUNT1");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(ssd)) {
            FinalData fd = getFinalData(ssd);

            try {
                fd.count += result.getInt("SALES_AMOUNT2");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(tsd)) {
            FinalData fd = getFinalData(tsd);

            try {
                fd.count += result.getInt("SALES_AMOUNT3");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(fosd)) {
            FinalData fd = getFinalData(fosd);

            try {
                fd.count += result.getInt("SALES_AMOUNT4");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(fisd)) {
            FinalData fd = getFinalData(fisd);

            try {
                fd.count += result.getInt("SALES_AMOUNT5");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void extractDOWTTS(String fsdow, String ssdow, String tsdow, String fosdow, String fisdow, final ResultSet result) {
        if (StringUtil.notNullOrEmpty(fsdow)) {
            DOWTotalSale dowtts = getDOWTotalSale(fsdow);
            try {
                int count = dowtts.getCount() + result.getInt("SALES_AMOUNT1");
                dowtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(ssdow)) {
            DOWTotalSale dowtts = getDOWTotalSale(ssdow);
            try {
                int count = dowtts.getCount() + result.getInt("SALES_AMOUNT2");
                dowtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if (StringUtil.notNullOrEmpty(tsdow)) {
            DOWTotalSale dowtts = getDOWTotalSale(tsdow);
            try {
                int count = dowtts.getCount() + result.getInt("SALES_AMOUNT3");
                dowtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if (StringUtil.notNullOrEmpty(fosdow)) {
            DOWTotalSale dowtts = getDOWTotalSale(fosdow);
            try {
                int count = dowtts.getCount() + result.getInt("SALES_AMOUNT4");
                dowtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        if (StringUtil.notNullOrEmpty(fisdow)) {
            DOWTotalSale dowtts = getDOWTotalSale(fisdow);
            try {
                int count = dowtts.getCount() + result.getInt("SALES_AMOUNT5");
                dowtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void extractDOMTTS(String fsdom, String ssdom, String tsdom, String fosdom, String fisdom, final ResultSet result) {
        if (StringUtil.notNullOrEmpty(fsdom)) {
            DOMTotalSale domtts = getDOMTotalSale(fsdom);
            try {
                int count = domtts.getCount() + result.getInt("SALES_AMOUNT1");
                domtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(ssdom)) {
            DOMTotalSale domtts = getDOMTotalSale(ssdom);
            try {
                int count = domtts.getCount() + result.getInt("SALES_AMOUNT2");
                domtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(tsdom)) {
            DOMTotalSale domtts = getDOMTotalSale(tsdom);
            try {
                int count = domtts.getCount() + result.getInt("SALES_AMOUNT3");
                domtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(fosdom)) {
            DOMTotalSale domtts = getDOMTotalSale(fosdom);
            try {
                int count = domtts.getCount() + result.getInt("SALES_AMOUNT4");
                domtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (StringUtil.notNullOrEmpty(fisdom)) {
            DOMTotalSale domtts = getDOMTotalSale(fisdom);
            try {
                int count = domtts.getCount() + result.getInt("SALES_AMOUNT5");
                domtts.count.set(count);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void computeFinalData() {
        for (String date : finalDatas.keySet()) {
            FinalData fd = finalDatas.get(date);
            String myk = fd.getMonYearKey();

            Map<String, FinalData> sameMonFds = getFinalDatasWithMYK(myk);
            int nbd = sameMonFds.size();

            //fd.daysInMon.set("" + nbd);
            Integer sum = mykTotalSale.get(myk);
            if (sum == null) {
                int s = 0;
                for (FinalData f : sameMonFds.values()) {
                    s += f.count;
                }

                sum = s;

                mykTotalSale.put(myk, sum);
            }

            double pcim = (fd.count * 100.0 * nbd) / sum;

            fd.percentageInMonth.set("" + pcim + "%");
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

    private FinalData getFinalData(String date) {
        FinalData fd = finalDatas.get(date);

        if (fd == null) {
            fd = new FinalData();
            fd.date.set(date);
            fd.dom.set(DateUtil.extractDOM(date));
            fd.dow.set(DateUtil.extractDOW(date));

            String myk = DateUtil.extractMYKey(date);
            fd.monYearKey.set(myk);

            finalDatas.put(date, fd);

            getFinalDatasWithMYK(myk).put(date, fd);
        }

        return fd;
    }

    private Map<String, FinalData> getFinalDatasWithMYK(String mykey) {
        Map<String, FinalData> fds = mykDatas.get(mykey);

        if (fds == null) {
            fds = new HashMap<String, FinalData>();

            mykDatas.put(mykey, fds);
        }

        return fds;
    }

    private void loadDPSales() {
        loadDPSales("519138", "RIMSPO_OPT");
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

    public Map<String, FinalData> getFinalDatas() {
        loadDPSales();

        return Collections.unmodifiableMap(finalDatas);
    }
}