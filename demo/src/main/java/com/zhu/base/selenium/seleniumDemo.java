package com.zhu.base.selenium;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Set;

public class seleniumDemo {

    public static void main(String[] args) {
        System.setProperty("webdriver.firefox.bin", "/Users/zhuhao/firefox-sdk/bin/Firefox");
        System.setProperty("webdriver.chrome.driver", "/usr/local/chromedriver/chromedriver");
        System.setProperty("phantomjs.binary.path", "/Users/zhuhao/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");
        //WebDriver webDriver = new PhantomJSDriver();
        WebDriver webDriver = new ChromeDriver();
        //WebDriver webDriver = new FirefoxDriver();
        webDriver.get("http://toutiao.manqian.cn/");

        List<WebElement> webElements = webDriver.findElements(By.xpath("/html/body/div[4]/main/div/ul/li/div/h3/a"));

        String hander = webDriver.getWindowHandle();

        webElements.stream().limit(2).forEach(webElement -> {
            webElement.click();
            switchToOtherWindow(webDriver, hander);
            System.out.println(webDriver.getTitle());
            closeOtherWindowByHandler(webDriver, hander);
        });
        //webDriver.getWindowHandles().stream().forEach(System.out::println);
        webDriver.close();
    }


    public static void closeOtherWindowByHandler(WebDriver driver, String handler) {
        try {
            Set<String> handles = driver.getWindowHandles();
            for (String s : handles) {
                if (s.equals(handler)) {
                    continue;
                } else {
                    driver.switchTo().window(s);
                    driver.close();
                }
            }
            driver.switchTo().window(handler);
        } catch (NoSuchWindowException e) {

        }
    }
    public static void switchToOtherWindow(WebDriver driver, String currentHandle) {
        try {
            if (StringUtils.isBlank(currentHandle)) {
                currentHandle = driver.getWindowHandle();
            }
            Set<String> handles = driver.getWindowHandles();
            for (String s : handles) {
                if (s.equals(currentHandle)) {
                    continue;
                } else {
                    driver.switchTo().window(s);
                    break;
                }
            }
        } catch (NoSuchWindowException e) {

        }

    }


    // https://sites.google.com/a/chromium.org/chromedriver/downloads
//    public static void main(String[] args){
//        System.setProperty("webdriver.firefox.bin", "/Users/zhuhao/firefox-sdk/bin/Firefox");
//        System.setProperty("webdriver.chrome.driver", "/usr/local/chromedriver/chromedriver");
//        System.setProperty("phantomjs.binary.path", "/Users/zhuhao/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");
//        //WebDriver webDriver = new PhantomJSDriver();
//        //WebDriver webDriver = new ChromeDriver();
//        WebDriver webDriver = new FirefoxDriver();
//        webDriver.get("http://daikuan.51kanong.com/Home/Daikuan/lists.html");
//        WebElement webElement = webDriver.findElement(By.xpath("/html"));
//        System.out.println(webElement.getAttribute("outerHTML"));
//
//        webDriver.close();
//    }
}
