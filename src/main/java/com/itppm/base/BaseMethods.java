package com.itppm.base;

import com.google.common.util.concurrent.Uninterruptibles;
import com.itppm.constants.FrameworkConstants;
import com.itppm.report.ExtentLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseMethods {
    public static WebDriver driver;
    public static Logger log = LogManager.getLogger(BaseMethods.class.getName());
    public static Calendar obj = Calendar.getInstance();
    public static boolean flagforTitleScreen = true;
    public static ObjectMapper mapper;

    public WebDriver initializeDriver(String browser) throws IOException {
        try {
            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("chrome.switches", "--disable-extensions");
                options.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                if (browser.equalsIgnoreCase("headless")) {
                    options.addArguments("headless");
                }
                driver = new ChromeDriver(options);
                log.info("Chrome browser is successfully Launched");
            }
            else if(browser.equalsIgnoreCase("headless")){
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("chrome.switches", "--disable-extensions");
                    options.addArguments("--remote-allow-origins=*");
                    WebDriverManager.chromedriver().setup();
                    options.addArguments("headless");
                    driver = new ChromeDriver(options);
            }
            else if (browser.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
                log.info("Firefox browser is successfully Launched");
            }
            driver.get(getPropertyValue("url"));
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        } catch (WebDriverException e) {
            System.err.println("WebDriverException");
            log.info("Browser not launched");
            throw new RuntimeException();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return driver;
    }

    public static String getPropertyValue(String key) throws Exception {
        String value = "";
        Properties property = new Properties();
        FileInputStream file = new FileInputStream(FrameworkConstants.getPropertiesfile());
        property.load(file);
        value = property.getProperty(key);
        if (value == null) {
            throw new Exception("Property name " + key + " is not found. Please check data.properties file");
        }
        return value;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public String getScreenshot(String testCaseName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationPath = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationPath));
        return destinationPath;
    }

    public WebElement locateElement(String locator, String locValue) {
        try {
            switch (locator) {
                case ("id"):
                    return driver.findElement(By.id(locValue));
                case ("link"):
                    return driver.findElement(By.linkText(locValue));
                case ("xpath"):
                    return driver.findElement(By.xpath(locValue));
                case ("name"):
                    return driver.findElement(By.name(locValue));
                case ("class"):
                    return driver.findElement(By.className(locValue));
                case ("tag"):
                    return driver.findElement(By.tagName(locValue));
            }
        } catch (NoSuchElementException e) {
            log.error("The element with locator " + locator + " not found.");
        } catch (WebDriverException e) {
            log.error("Unknown exception occured while finding " + locator + " with the value " + locValue);
        }
        return null;
    }

    public List<WebElement> locateElements(String locator, String locValue) {
        try {
            switch (locator) {
                case ("id"):
                    return driver.findElements(By.id(locValue));
                case ("link"):
                    return driver.findElements(By.linkText(locValue));
                case ("xpath"):
                    return driver.findElements(By.xpath(locValue));
                case ("name"):
                    return driver.findElements(By.name(locValue));
                case ("class"):
                    return driver.findElements(By.className(locValue));
                case ("tag"):
                    return driver.findElements(By.tagName(locValue));
            }
        } catch (NoSuchElementException e) {
            log.error("The element with locator " + locator + " not found.");
        } catch (WebDriverException e) {
            log.error("Unknown exception occured while finding " + locator + " with the value " + locValue);
        }
        return null;
    }

    public WebElement locateElement(String locValue) {
        return driver.findElement(By.id(locValue));
    }

    public void type(WebElement ele, String data) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.clear();
            ele.sendKeys(data);
            String x = "" + ele;
            log.info(data + " is entered in the text field");
        } catch (InvalidElementStateException e) {
            log.error(e + " is occurred during execution while entering " + data + " in the field");
        } catch (WebDriverException e) {
            log.error(e + " is occurred during execution while entering " + data + " in the field ");
        }
    }

    public void type(WebElement ele, String data, String message) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.clear();
            ele.sendKeys(data);
            String x = "" + ele;
            log.info(data + " is entered in the text field");
            ExtentLogger.pass(message, true);
        } catch (InvalidElementStateException e) {
            log.error(e + " is occurred during execution while entering " + data + " in the field");
            ExtentLogger.fail(e + " is occurred during execution while entering " + data + " in the field",true);
        } catch (WebDriverException e) {
            log.error(e + " is occurred during execution while entering " + data + " in the field ");
            ExtentLogger.fail(e + " is occurred during execution while entering " + data + " in the field", true);
        }
    }

    public void click(WebElement ele) {
        String text = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            text = ele.getText();
            ele.click();
            log.info("The element " + text + " is clicked");
        } catch (InvalidElementStateException e) {
            log.error(e + " is occurred during execution when trying to click " + text);
        } catch (WebDriverException e) {
            log.error(e + " is occurred during execution when trying to click " + text);
        }
    }

    public void click(WebElement ele, String message) {
        String text = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            text = ele.getText();
            ele.click();
            ExtentLogger.pass(message ,true);
            log.info("The element " + text + " is clicked");
        } catch (InvalidElementStateException e) {
            log.error(e + " is occurred during execution when trying to click " + text);
            ExtentLogger.fail(e + " is occurred during execution when trying to click " + text , true);
        } catch (WebDriverException e) {
            ExtentLogger.fail(e + " is occurred during execution when trying to click " + text);
        }
    }

    public void pressEnter(WebElement ele, String message) {
        String text = "";
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            text = ele.getText();
            ele.sendKeys(Keys.ENTER);
            ExtentLogger.pass(message ,true);
            log.info("The enter key is pressed");
        } catch (InvalidElementStateException e) {
            log.error(e + " is occurred during execution when trying to press " + text);
            ExtentLogger.fail(e + " is occurred during execution when trying to press " + text , true);
        } catch (WebDriverException e) {
            ExtentLogger.fail(e + " is occurred during execution when trying to press " + text);
        }
    }



    public String getText(WebElement ele) {
        String bReturn = "";
        try {
            bReturn = ele.getText();
        } catch (WebDriverException e) {
            log.error("The element: " + ele + " could not be found.");
        }
        return bReturn;
    }

    public String getTitle() {
        String bReturn = "";
        try {
            bReturn = driver.getTitle();
        } catch (WebDriverException e) {
            log.error(e + " Occured While fetching Title");
        }
        return bReturn;
    }

    public void selectDropDownUsingText(WebElement ele, String value) {
        try {
            new Select(ele).selectByVisibleText(value);
            log.info("The dropdown is selected with text " + value);
        } catch (WebDriverException e) {
            log.error(e + " Occured While selecting dropdown with text " + value);
        }

    }

    public void selectDropDownUsingIndex(WebElement ele, int index) {
        try {
            new Select(ele).selectByIndex(index);
            log.info("The dropdown is selected with index " + index);
        } catch (WebDriverException e) {
            log.error(e + " Occured While selecting dropdown with index " + index);
        }

    }

    public void scrollIntoView(WebElement element, String message) {
        try{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true)", element);
            log.info("Scrolled into view");
            ExtentLogger.pass(message, true);
        }catch (WebDriverException e) {
            log.error(e + " Occured While scrolling into view ");
            ExtentLogger.fail("Unable to scroll into view", true);
        }
    }


    public boolean verifyPartialTitle(String title) {
        boolean bReturn = false;
        try {
            if (getTitle().contains(title)) {
                log.info("The title of the page contains the text :" + title);
                bReturn = true;
            } else {
                log.error("The title of the page:" + driver.getTitle() + " did not match with the value :" + title);
            }
        } catch (WebDriverException e) {
            log.error(e + " occured while verifying the title");
        }
        return bReturn;

    }

    public void verifyExactText(WebElement ele, String expectedText) {
        try {
            if (getText(ele).equals(expectedText)) {
                log.info("The text: " + getText(ele) + " matches with the value :" + expectedText);
                ExtentLogger.pass("The text: " + getText(ele) + " is matched with expected text: "+expectedText ,true);
            } else {
                System.err.println("The text " + getText(ele) + " doesn't matches the actual " + expectedText);
                log.info("The text " + getText(ele) + " doesn't matches the actual " + expectedText);
                ExtentLogger.fail("The text " + getText(ele) + " doesn't matches the actual " + expectedText, true);
            }
        } catch (WebDriverException e) {
            log.error(e + " occured while verifying the text");
            ExtentLogger.fail(e + " occured while verifying the text");
        }
    }

    public void verifyExactTextIgnoreCase(WebElement ele, String expectedText) {
        try {
            if (getText(ele).equalsIgnoreCase(expectedText)) {
                log.info("The text: " + getText(ele) + " matches with the value :" + expectedText);
                ExtentLogger.pass("The text: " + getText(ele) + " is matched with expected text: "+expectedText ,true);
            } else {
                System.err.println("The text " + getText(ele) + " doesn't matches the actual " + expectedText);
                log.info("The text " + getText(ele) + " doesn't matches the actual " + expectedText);
                ExtentLogger.fail("The text " + getText(ele) + " doesn't matches the actual " + expectedText, true);
            }
        } catch (WebDriverException e) {
            log.error(e + " occured while verifying the text");
            ExtentLogger.fail(e + " occured while verifying the text");
        }
    }

    public void verifyExactText(String actualText, String expectedText) {
        try {
            if (actualText.equals(expectedText)) {
                log.info("The text: " + actualText + " matches with the value :" + expectedText);
                ExtentLogger.pass("The text: " + actualText + " is matched with expected text: "+expectedText, true);
            } else {
                System.err.println("The text " + actualText + " doesn't matches the actual " + expectedText);
                log.info("The text " + actualText + " doesn't matches the actual " + expectedText);
                ExtentLogger.fail("The text " + actualText + " doesn't matches the actual " + expectedText, true);
            }
        } catch (Exception e) {
            log.error(e + " occured while verifying the text");
            ExtentLogger.fail(e + " occured while verifying the text");
        }
    }

    public void verifyPartialText(WebElement ele, String expectedText) {
        try {
            String s = getText(ele);
            if (getText(ele).contains(expectedText)) {
                log.info("The expected text contains the actual " + expectedText);
                ExtentLogger.pass("The expected text contains the actual " + expectedText, true);
            } else {
                System.err.println("The text " + getText(ele) + " doesn't contain the actual " + expectedText);
                log.info("The expected text doesn't contain the actual " + expectedText);
                ExtentLogger.fail("The expected text doesn't contain the actual " + expectedText, true);
            }
        } catch (WebDriverException e) {
            log.error(e + " occured while verifying the text");
            ExtentLogger.fail(e + " occured while verifying the text");
        }
    }

    public void verifySelected(WebElement ele, String message) {
        try {
            if (ele.isSelected()) {
                log.info("The element " + ele + " is selected");
                ExtentLogger.pass(message, true);
            } else {
                log.info("The element " + ele + " is not selected");
                ExtentLogger.fail(message, true);
            }
        } catch (WebDriverException e) {
            log.error("WebDriverException : " + e.getMessage());
        }
    }

    public void verifyDisplayed(WebElement ele) {
        try {
            if (ele.isDisplayed()) {
                log.info("The element " + ele + " is visible");
            } else {
                log.info("The element " + ele + " is not visible");
            }
        } catch (WebDriverException e) {
            log.error("WebDriverException : " + e.getMessage());
        }
    }

    public void verifyEnabledStatus(WebElement ele) {
        try {
            if (ele.isEnabled()) {
                log.info("The element " + ele + " is Enabled");
                ExtentLogger.pass("The element " + ele + " is Enabled", true);
            } else {
                log.info("The element " + ele + " is not Enabled");
                ExtentLogger.fail("The element " + ele + " is not Enabled", true);
            }
        } catch (WebDriverException e) {
            log.error("WebDriverException : " + e.getMessage());
            ExtentLogger.fail(e.getMessage(), true);
        }
    }

    public void verifyDisabledStatus(WebElement ele, String message) {
        try {
            if (!ele.isEnabled()) {
                log.info("The element " + ele + " is disabled");
                ExtentLogger.pass(message, true);
            } else {
                log.info("The element " + ele + " is not Enabled");
                ExtentLogger.fail(message, true);
            }
        } catch (WebDriverException e) {
            log.error("WebDriverException : " + e.getMessage());
            ExtentLogger.fail(e.getMessage(), true);
        }
    }

    public void closeCurrentWindow(){
        try {
            driver.close();
            switchToWindow(0);
            log.info("Current window closed successfully and returned to previous window");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Current window not closed successfully");
        }
    }

    public void switchToWindow(int index) {
        try {
            Set<String> allWindowHandles = driver.getWindowHandles();
            List<String> allHandles = new ArrayList<>();
            allHandles.addAll(allWindowHandles);
            driver.switchTo().window(allHandles.get(index));
        } catch (NoSuchWindowException e) {
            log.error("The driver could not move to the given window by index " + index);
        } catch (WebDriverException e) {
            log.error("WebDriverException : " + e.getMessage());
        }
    }

    public void switchToFrame(WebElement ele) {
        try {
            driver.switchTo().frame(ele);
            log.info("switch In to the Frame " + ele);
        } catch (NoSuchFrameException e) {
            log.info("NoSuchFrameException : " + e.getMessage());
        } catch (WebDriverException e) {
            log.info("WebDriverException : " + e.getMessage());
        }
    }
    
    public void switchToDefaultContent(){
        try {
            driver.switchTo().defaultContent();
            log.info("switch In to the defaultcontent ");
        } catch (WebDriverException e) {
            log.info("WebDriverException : " + e.getMessage());
        }
    }

    public void acceptAlert() {
        String text = "";
        try {
            Alert alert = driver.switchTo().alert();
            text = alert.getText();
            alert.accept();
            log.info("The alert " + text + " is accepted.");
        } catch (NoAlertPresentException e) {
            log.info("There is no alert present.");
        } catch (WebDriverException e) {
            log.info("WebDriverException : " + e.getMessage());
        }
    }

    public void dismissAlert() {
        String text = "";
        try {
            Alert alert = driver.switchTo().alert();
            text = alert.getText();
            alert.dismiss();
            log.info("The alert " + text + " is dismissed.");
        } catch (NoAlertPresentException e) {
            log.info("There is no alert present.");
        } catch (WebDriverException e) {
            log.info("WebDriverException : " + e.getMessage());
        }

    }

    public String getAlertText() {
        String text = "";
        try {
            Alert alert = driver.switchTo().alert();
            text = alert.getText();
        } catch (NoAlertPresentException e) {
            log.info("There is no alert present.");
        } catch (WebDriverException e) {
            log.info("WebDriverException : " + e.getMessage());
        }
        return text;
    }

    public void mouseOver(WebElement ele) {
        try {
            Actions ac = new Actions(driver);
            ac.moveToElement(ele).perform();
            log.info("Performed Mouse over action successfully");
        } catch (Exception e) {
            log.error("Mouse over is not working");
        }
    }

    public void refresh() {
        try {
            driver.navigate().refresh();
            log.info("Page refreshed successfully");
        } catch (Exception e) {
            log.error("An error occurred while refreshing the page");
        }
    }

    public void scrollDown(int yPixel) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"+yPixel+")");
            log.info("Performed scrollDown by "+yPixel+" pixel successfully");
        } catch (Exception e) {
            log.error("ScrollDown action not working");
        }
    }

    public String getXpath(String xpath, String value){
        return String.format(xpath, value);
    }

    public String getRandomNumber() {
        int rand = (new Random().nextInt(999999999));
        String subscription_ID = Integer.toString(rand);
        return subscription_ID;
    }

    public static Boolean containsSpecialCharacters(String str) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9 &&[^-_]]");
        Matcher matcher = pattern.matcher(str);
        boolean isStringContainsSpecialCharacter = matcher.find();
        if(isStringContainsSpecialCharacter)
            flag = true;

        return flag;
    }

    public static Boolean compareJSON(String json1, String json2)  {
        boolean flag = false;
        mapper = new ObjectMapper();
        JsonNode jsonNode1 = null;
        JsonNode jsonNode2 = null;
        try {
            jsonNode1 = mapper.readTree(json1);
            jsonNode2 = mapper.readTree(json2);
            if(jsonNode1.equals(jsonNode2))
                flag = true;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void customWait(int waitTimeinSecs){
        Uninterruptibles.sleepUninterruptibly(waitTimeinSecs, TimeUnit.SECONDS);
    }

    public static String getCurrentURL(){
        return driver.getCurrentUrl();
    }
    
}
