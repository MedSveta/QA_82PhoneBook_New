package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WDListener implements WebDriverListener {
    Logger logger = LoggerFactory.getLogger(WDListener.class);

    @Override
    public void onError(Object target, Method method, Object[] args,
                        InvocationTargetException e) {
        WebDriverListener.super.onError(target, method, args, e);
        logger.error("created exception {}", e.toString());
        //logger.error("created exception "+ e);
    }

    @Override
    public void beforeGet(WebDriver driver, String url) {
        WebDriverListener.super.beforeGet(driver, url);
        logger.info("Before get {}", url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        WebDriverListener.super.afterGet(driver, url);
        logger.info("After get {}", url);
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        WebDriverListener.super.beforeFindElement(driver, locator);
        logger.info("Before Find element with locator --> {}",
                locator.toString());
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator,
                                 WebElement result) {
        WebDriverListener.super.afterFindElement
                (driver, locator, result);
        logger.info("After find element with locator --> {}",
                locator.toString());
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        WebDriverListener.super.beforeQuit(driver);
        logger.info("Before quit driver is --> {}", driver.toString());
    }

    @Override
    public void afterQuit(WebDriver driver) {
        WebDriverListener.super.afterQuit(driver);
        logger.info("Browser quit");
    }

    @Override
    public void beforeClick(WebElement element) {
        WebDriverListener.super.beforeClick(element);
        logger.info("Before click on element: {}", element.getTagName());
    }

    @Override
    public void afterClick(WebElement element) {
        WebDriverListener.super.afterClick(element);
        logger.info("After click on element: {}", element.getTagName());
    }

    @Override
    public void afterMaximize(WebDriver.Window window) {
        WebDriverListener.super.afterMaximize(window);
        logger.info("After maximize window size is {}",window.getSize());
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        WebDriverListener.super.afterSendKeys(element, keysToSend);
        logger.info("Use send keys to element {} type {}",
                element.getTagName(), keysToSend);
    }
}
