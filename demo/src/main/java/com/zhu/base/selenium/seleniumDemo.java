package com.zhu.base.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class seleniumDemo {

    // https://sites.google.com/a/chromium.org/chromedriver/downloads
    public static void main(String[] args){
        System.setProperty("webdriver.firefox.bin", "/Users/zhuhao/firefox-sdk/bin/Firefox");
        System.setProperty("webdriver.chrome.driver", "/usr/local/chromedriver/chromedriver");
        System.setProperty("phantomjs.binary.path", "/Users/zhuhao/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");
        //WebDriver webDriver = new PhantomJSDriver();
        //WebDriver webDriver = new ChromeDriver();
        WebDriver webDriver = new FirefoxDriver();
        webDriver.get("http://daikuan.51kanong.com/Home/Daikuan/lists.html");
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        System.out.println(webElement.getAttribute("outerHTML"));
        webDriver.close();
    }
}
