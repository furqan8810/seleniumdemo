package com.demo.factory;

public class ChromeFactory implements BrowserFactory{
    @Override
    public Browser createBrowser() {

            return new ChromeBrowser();

    }
}
