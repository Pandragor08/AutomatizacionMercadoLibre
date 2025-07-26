package com.mercadolibre.page;

import com.mercadolibre.base.BaseActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoginMercadoLibre extends BaseActions {

    private static WebDriverWait wait;
    private static WebDriver driver;


    public By barraBusqueda = By.id("cb1-edit");
    public By searchbtn = By.className("nav-search-btn");
    public By conditions = By.xpath("//h3[@class='ui-search-filter-dt-title' and text()='Condición']");
    public By getConditions = By.xpath("//span[@class='ui-search-filter-name' and text()='Nuevo']");
    public By locations = By.xpath("//h3[@class='ui-search-filter-dt-title' and text()='Origen del envío']");
    public By local = By.xpath("//a[@class='ui-search-link' and .//span[text()='Local']]");
    public By price = By.xpath("//div[contains(@class, 'ui-search-sort-filter')]//button[contains(@class, 'andes-dropdown__trigger')]");
    public By price2 = By.xpath("//li[@role='option' and .//span[text()='Mayor precio']]");


    public String urlMercadoLibre() {
        String url = "https://www.mercadolibre.com.mx/";
        return url;
    }

    public void homePageMercadoLibre() {
        openURL(urlMercadoLibre());
        maximizeWindow();
    }

    public void searchBar() {
        waitForElement(barraBusqueda);
        click(barraBusqueda);
        waitAndSendKeys(barraBusqueda, "playstation 5");
        forceDelay(2000);
        click(searchbtn);
    }

    public void filterCondition() {
        scrollUntilElementVisible(conditions);
        click(getConditions);
        forceDelay(2000);
    }

    public void filterLocation() {
        scrollUntilElementVisible(locations);
        click(local);
        forceDelay(2000);
    }

    public void filterOrderMayoaMenor() {
        click(price);
        click(price2);
        forceDelay(2000);
    }


    public List<String> obtenerPrimeros5Productos() {
        WebDriver driver = BaseActions.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By productos = By.xpath("//main/div/div/section/div/ol/li");
        wait.until(ExpectedConditions.visibilityOfElementLocated(productos));
        List<WebElement> listaProductos = driver.findElements(productos);
        List<String> primeros5 = new ArrayList<>();

        int limite = Math.min(5, listaProductos.size());

        for (int i = 0; i < limite; i++) {
            WebElement producto = listaProductos.get(i);

            try {
                String nombre = producto.findElement(By.xpath(".//div[@class='poly-card__content']//a[contains(@class, 'poly-component__title')]")).getText();
                String precio = producto.findElement(By.cssSelector("span.andes-money-amount__fraction")).getText();

                primeros5.add("Producto: " + nombre + " | Precio: " + precio);
            } catch (Exception e) {
                primeros5.add("Producto " + (i + 1) + ": No se pudo obtener nombre/precio");
            }
        }
        return primeros5;
    }


}
