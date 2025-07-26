package com.mercadolibre.base;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class BaseActions {
    private static WebDriver driver;
    private static WebDriverWait wait;


    private static final String CHROME_DRIVER_PATH = "C:\\Users\\Karlos\\IdeaProjects\\MercadoLibre\\driver\\chromedriver.exe";
    protected boolean runTestStatus = true;
    public static SoftAssert enlaceAssert = new SoftAssert();
    public static ThreadLocal<WebDriverWait> driverWait = new ThreadLocal<>();
    public static ThreadLocal<WebDriver> driverr = new ThreadLocal<>();

    public static void initializeDriver() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    public static void openURL(String url) {
        getDriver().get(url);
    }

    public static void maximizeWindow() {
        getDriver().manage().window().maximize();
    }

    public static void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public static void waitAndSendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    public static WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public void forceDelay(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {

        }
    }

    public void scrollUntilElementVisible(By locator) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        int maxScrolls = 20; // evita scrolls infinitos
        int currentScroll = 0;

        while (currentScroll < maxScrolls) {
            try {
                WebElement element = getDriver().findElement(locator);
                if (element.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                    System.out.println("Elemento visible [✓]");
                    return;
                }
            } catch (NoSuchElementException e) {
                // Sigue haciendo scroll
            }

            js.executeScript("window.scrollBy(0, 500);");
            currentScroll++;
            try {
                Thread.sleep(500); // pequeña pausa para carga de elementos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        throw new RuntimeException("No se encontró el elemento después de hacer scroll.");
    }


    // Metodo para tomar captura de pantalla y añadirla al reporte
    public void addScreenshotToAllureReport(String evidenceDescription) {
        Allure.addAttachment(evidenceDescription, new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES)));
    }

    // Toma captura en caso de que un test falle
    //@AfterMethod
    public void setTestStatus(ITestResult result) {
        if (runTestStatus) {
            if (result.getStatus() == ITestResult.FAILURE) {
                addScreenshotToAllureReport("Test Fallido");
            }
        }
    }
}
