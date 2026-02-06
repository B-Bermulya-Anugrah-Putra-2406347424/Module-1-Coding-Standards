package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.service.ProductService;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest extends BaseProductFunctionalTest {

    @Autowired
    private ProductService service;

    @BeforeEach
    void setUp() {
        // reset state
        service.findAll().forEach(p -> service.delete(p.getProductId()));
        setupUrls();
    }

    @Test
    void testCreateProductIsSuccessful(ChromeDriver driver) {
        driver.get(createUrl);
        waitForPageLoad(driver, createUrl);

        driver.findElement(By.id("nameInput")).sendKeys("Mangga Hitam");
        driver.findElement(By.id("quantityInput")).sendKeys("10");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        waitForPageLoad(driver, productListUrl);

        assertTrue(driver.findElement(By.xpath("//td[text()='Mangga Hitam']")).isDisplayed());
    }
}