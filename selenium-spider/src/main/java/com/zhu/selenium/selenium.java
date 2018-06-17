package com.zhu.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class selenium {

    public static void main(String[] args){
        System.setProperty("phantomjs.binary.path", "/Users/zhuhao/Downloads/phantomjs-2.1-2.1-macosx/bin/phantomjs");
        System.setProperty("webdriver.chrome.driver","c:/chromedriver2.38.exe");//chromedriver服务地址
       // WebDriver webDriver = new PhantomJSDriver();
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://daikuan.51kanong.com/Home/Daikuan/lists.html");
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        System.out.println(webElement.getAttribute("outerHTML"));
        webDriver.close();
    }
}
