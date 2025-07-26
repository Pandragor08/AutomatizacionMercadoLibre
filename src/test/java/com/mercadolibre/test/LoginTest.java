package com.mercadolibre.test;

import com.mercadolibre.base.BaseActions;
import com.mercadolibre.page.LoginMercadoLibre;
import com.mercadolibre.utils.AllureUtils;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class LoginTest extends BaseActions {

    public WebDriverWait wait;
    LoginMercadoLibre loginMercadoLibre = new LoginMercadoLibre();

    @BeforeMethod
    public void setUp() {
        this.wait = new WebDriverWait(BaseActions.getDriver(), Duration.ofSeconds(10));

    }

    @Test(priority = 1)
    @Story("TestPage MercadoLibre")
    @Description("Ingresar url de Mercado Libre")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginPage() {
        AllureUtils.addDescription("Ingresar url de Mercado libre");
        loginMercadoLibre.homePageMercadoLibre();
        AllureUtils.saveTextLog("Página de login abierta");
        AllureUtils.takeScreenshot(BaseActions.getDriver());
    }

    @Test(priority = 2)
    @Story("TestPage MercadoLibre")
    @Description("Se seleccionara el pais origen")
    @Severity(SeverityLevel.NORMAL)
    public void testVerifyCountry() {
        /*
        No hay seccion de pais, solo esta el body
        <body data-site="ML" data-country="MX">
         */
        AllureUtils.addDescription("Se seleccionara el pais origen");
        AllureUtils.saveTextLog("No hay seccion visible de seleccion de pais");
        AllureUtils.takeScreenshot(BaseActions.getDriver());
        System.out.println("<body data-site=\"ML\" data-country=\"MX\">");
    }

    @Test(priority = 3)
    @Story("TestPage MercadoLibre")
    @Description("Busqueda de articulos")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchArti() {
        //Busca el término “playstation 5”
        AllureUtils.addDescription("Busqueda de articulos");
        AllureUtils.saveTextLog("Se busca término playstation 5");
        loginMercadoLibre.searchBar();
        AllureUtils.takeScreenshot(BaseActions.getDriver());
    }

    @Test(priority = 4)
    @Story("TestPage MercadoLibre")
    @Description("Filtro by condictions")
    @Severity(SeverityLevel.NORMAL)
    public void testCondition() {
        //Filtrar por condición “Nuevos”
        System.out.println("Filtrar por condición Nuevos");
        AllureUtils.addDescription("Filtrar por condicion");
        AllureUtils.saveTextLog("Se selecciona etiqueta condicion Nuevos");
        loginMercadoLibre.filterCondition();
        AllureUtils.takeScreenshot(BaseActions.getDriver());
    }

    @Test(priority = 5)
    @Story("TestPage MercadoLibre")
    @Description("Filtro by locations")
    @Severity(SeverityLevel.NORMAL)
    public void testLocation() {
        //Filtrar por ubicación “Cdmx” (No encontre opcion CDMX sustitui por Local)
        System.out.println("Filtrar por ubicación");
        AllureUtils.addDescription("Se filtra por ubicacion");
        AllureUtils.saveTextLog("Se busca por venta local");
        loginMercadoLibre.filterLocation();
        AllureUtils.takeScreenshot(BaseActions.getDriver());
    }

    @Test(priority = 6)
    @Story("TestPage MercadoLibre")
    @Description("Order by mayor a menor")
    @Severity(SeverityLevel.NORMAL)
    public void testPrice() {
        //Order by “mayor a “menor precio”
        System.out.println("Order by mayor a menor precio");
        AllureUtils.addDescription("Order by mayor a menor");
        AllureUtils.saveTextLog("Ordenar por mayor precio");
        loginMercadoLibre.filterOrderMayoaMenor();
        AllureUtils.takeScreenshot(BaseActions.getDriver());
    }

    @Test(priority = 7)
    @Story("TestPage MercadoLibre")
    @Description("First five products")
    @Severity(SeverityLevel.NORMAL)
    public void testProducts() {
        //Obtenga el nombre y el precio de los primeros 5 productos
        System.out.println("Obtenga el nombre y el precio de los primeros 5 productos");
        AllureUtils.addDescription("Obtenga el nombre y el precio de los primeros 5 productos");
        AllureUtils.saveTextLog("Se mustra el print en el cmd");
        List<String> primeros5 = loginMercadoLibre.obtenerPrimeros5Productos();
        for (String p : primeros5) {
            System.out.println(p);
        }
        AllureUtils.takeScreenshot(BaseActions.getDriver());
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        enlaceAssert.assertAll();
        BaseActions.closeDriver();

    }

}

