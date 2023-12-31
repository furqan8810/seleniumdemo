package com.demo.factory;

public class FirefoxFactory implements BrowserFactory{
    @Override
    public Browser createBrowser() {
        return new FireFoxBrowser();
    }
}
