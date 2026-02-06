package id.ac.ui.cs.advprog.eshop.functional;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

public abstract class BaseProductFunctionalTest {

    @LocalServerPort
    protected int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    protected String baseUrl;

    protected String productListUrl;
    protected String createUrl;

    protected void waitForPageLoad(ChromeDriver driver, String expectedUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // pastikan browser sudah pindah ke URL yang diinginkan
        wait.until(ExpectedConditions.urlToBe(expectedUrl));

        // pastikan browser sudah selesai me-render seluruh DOM
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected void setupUrls() {
        this.productListUrl = String.format("%s:%d/product/list", baseUrl, serverPort);
        this.createUrl = String.format("%s:%d/product/create", baseUrl, serverPort);
    }
}