package com.demo.factory;

import java.util.Properties;

public class BrowserFactoryProvider {
    public static BrowserFactory getBrowserFactory(Properties config) {
        String browserType = config.getProperty("browser");

        if ("chrome".equalsIgnoreCase(browserType)) {
            return new ChromeFactory();
        } else if ("firefox".equalsIgnoreCase(browserType)) {
            return new FirefoxFactory();
        } else {
            throw new RuntimeException("Invalid browser type specified in Config.properties");
        }
    }
}
