package com.fiverr.sanalytics.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/24/17
 */
public class Configuration {

    private static volatile Configuration instance;

    final Map<String, String> params = new HashMap<String, String>();

    private Configuration() {
        initialize();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            Configuration tmp = new Configuration();
            instance = tmp;
        }

        return instance;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String getParam(String key, String dfv) {
        String v = params.get(key);
        return v == null ? dfv : v;
    }

    private void initialize() {
        InputStream in = null;

        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/configuration.properties");

            Properties prop = new Properties();
            prop.load(in);

            for (String key : prop.stringPropertyNames()) {
                String v = prop.getProperty(key);

                params.put(key, v == null ? "" : v.trim());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}
