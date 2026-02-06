package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.service.ProductService;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class EditAndDeleteProductFunctionalTest extends BaseProductFunctionalTest {

    @Autowired
    private ProductService service;

    @BeforeEach
    void setUp(ChromeDriver driver) {
        // reset state
        service.findAll().forEach(p -> service.delete(p.getProductId()));
        setupUrls();

        // setup data awal
        driver.get(createUrl);
        waitForPageLoad(driver, createUrl);
        driver.findElement(By.id("nameInput")).sendKeys("Mangga Hitam");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        waitForPageLoad(driver, productListUrl);
    }

    @Test
    void testEditProductIsSuccessful(ChromeDriver driver) {
        driver.get(productListUrl);
        waitForPageLoad(driver, productListUrl);

        driver.findElement(By.linkText("Edit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/edit/"));
        waitForPageLoad(driver, driver.getCurrentUrl());

        driver.findElement(By.id("nameInput")).clear();
        driver.findElement(By.id("nameInput")).sendKeys("Mangga Putih");
        driver.findElement(By.id("quantityInput")).clear();
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        waitForPageLoad(driver, productListUrl);

        assertEquals(productListUrl, driver.getCurrentUrl());
        assertTrue(driver.findElement(By.xpath("//td[text()='Mangga Putih']")).isDisplayed());
    }

    @Test
    void testDeleteProductIsSuccessful(ChromeDriver driver) {
        driver.get(productListUrl);
        waitForPageLoad(driver, productListUrl);

        driver.findElement(By.linkText("Delete")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/delete/"));
        waitForPageLoad(driver, driver.getCurrentUrl());

        driver.findElement(By.cssSelector("button.btn-danger")).click();

        waitForPageLoad(driver, productListUrl);

        assertEquals(productListUrl, driver.getCurrentUrl());
        assertTrue(driver.findElements(By.xpath("//td[text()='Mangga Hitam']")).isEmpty());
    }
}