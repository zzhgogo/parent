package com.zhu.base.selenium;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import java.util.Random;
import java.util.Set;

public class seleniumDemo {

    public static void main(String[] args) {
        System.setProperty("webdriver.firefox.bin", "/Users/zhuhao/firefox-sdk/bin/Firefox");
        System.setProperty("webdriver.chrome.driver", "/usr/local/chromedriver/chromedriver");
        System.setProperty("phantomjs.binary.path", "/usr/local/phantomjs-2.1.1-macosx/bin/phantomjs");
//        WebDriver webDriver = new PhantomJSDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        WebDriver webDriver = new ChromeDriver(chromeOptions);
        //WebDriver webDriver = new FirefoxDriver();
        webDriver.get("https://weixin.sogou.com/weixin?type=2&query=%E6%96%B0%E4%B8%89%E6%9D%BF&ie=utf8&s_from=input&_sug_=n&_sug_type_=1&w=01015002&oq=&ri=10&sourceid=sugg&sut=0&sst0=1553593763855&lkt=0%2C0%2C0&p=40040108");

//        System.out.println(webDriver.getTitle());
//        _inputAndSubmit(webDriver, "#query", "财富");

        sleepRandom(3000, 5000);
        System.out.println(webDriver.getPageSource());
        _clickBySelector(webDriver, "#tool_show");
        webDriver.close();
    }


    public static void _inputAndSubmit(WebDriver driver, String selector, String word) {
        WebElement we = driver.findElement(By.cssSelector(selector));
        if (we != null) {
            we.clear();
            for (int i = 0; i < word.length(); i++) {
                char item = word.charAt(i);
                we.sendKeys(String.valueOf(item));
            }
            we.submit();
        } else {

        }
    }


    public static void _clickBySelector(WebDriver driver, String selector) {
        WebElement webElement = driver.findElement(By.cssSelector(selector));
        if (webElement != null && webElement.isDisplayed()) {
            webElement.click();
        } else {

        }
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

    /**
     * 随机时间睡眠 多少毫秒到多少毫秒之间
     */
    public static void sleepRandom(int start, int end) {
        Random random = new Random();
        int mims = (int) Math.round(Math.random() * (end - start) + start);
        try {
            Thread.sleep(mims);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//    https://sites.google.com/a/chromium.org/chromedriver/downloads
//    public static void main(String[] args){
//        System.setProperty("webdriver.firefox.bin", "/Users/zhuhao/firefox-sdk/bin/Firefox");
//        System.setProperty("webdriver.chrome.driver", "/usr/local/chromedriver/chromedriver");
//        System.setProperty("phantomjs.binary.path", "/Users/zhuhao/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");
//        WebDriver webDriver = new PhantomJSDriver();
//        WebDriver webDriver = new ChromeDriver();
//        WebDriver webDriver = new FirefoxDriver();
//        webDriver.get("http://daikuan.51kanong.com/Home/Daikuan/lists.html");
//        WebElement webElement = webDriver.findElement(By.xpath("/html"));
//        System.out.println(webElement.getAttribute("outerHTML"));
//
//        webDriver.close();
//    }
}
