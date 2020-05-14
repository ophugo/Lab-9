package mx.tec.lab;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ApplicationTests {

	private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/ophugo/Downloads/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @BeforeEach
    public void init() {
        driver.manage().deleteAllCookies();
    }

    @Test
    void givenAClient_whenEnreringAutomationPractice_thenPageTitleIsCorrect() throws Exception {
        // When
        driver.get("http://automationpractice.com/index.php");
        String title = driver.getTitle();

        // Then
        assertEquals("My Store", title);
    }

    @Test
    public void givenAClient_whenEnteringLoginCredentials_thenAccountPageIsDisplayed() throws Exception {
        // When
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("a01561688@itesm.mx");
        WebElement passwordField = driver.findElement(By.id("passwd"));
        passwordField.sendKeys("thisishelp10");
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
        submitButton.click();
        String title = driver.getTitle();

        // Then
        assertEquals("My account - My Store", title);
    }

    @Test
    public void givenAClient_whenEnteringWrongLoginCredentials_thenAuthenticationPageIsDisplayed() throws Exception {
        // When
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("a01561688@itesm.mx");
        WebElement passwordField = driver.findElement(By.id("passwd"));
        passwordField.sendKeys("saddfsf");
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
        submitButton.click();

        // Then
        assertEquals("Login - My Store", driver.getTitle());
    }

    @Test
    public void givenAClient_whenEnteringWrongLoginCredentials_thenErrorMessageIsDisplayed() throws Exception {
        // When
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys("a01561688@itesm.mx");
        WebElement passwordField = driver.findElement(By.id("passwd"));
        passwordField.sendKeys("saddfsf");
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
        submitButton.click();

        WebElement item = driver.findElement(By.xpath("//div[@class='alert alert-danger']/ol/li"));

        assertEquals("Authentication failed.", item.getText());
    }

    @Test
    public void givenAClient_whenSearchingNotExistingProduct_thenNoResultsDisplayed() throws Exception {
        driver.get("http://automationpractice.com/index.php");
        WebElement searchField = driver.findElement(By.id("search_query_top"));
        searchField.sendKeys("Not Exists");
        WebElement submitButton = driver.findElement(By.name("submit_search"));
        submitButton.click();

        WebElement counter = driver.findElement(By.xpath("//span[@class='heading-counter']"));

        assertEquals("0 results have been found.", counter.getText().trim());
    }

    @Test
    public void givenAClient_whenSearchingEmptyString_thenPleaseEnterDisplayed() throws Exception {
        // When
        driver.get("http://automationpractice.com/index.php");
        WebElement submitButton = driver.findElement(By.name("submit_search"));
        submitButton.click();
        WebElement p = driver.findElement(By.xpath("//p[@class='alert alert-warning']"));

        // Then
        assertEquals("Please enter a search keyword", p.getText().trim());
    }

    @Test
    public void givenAClient_whenSigningOut_thenAuthenticationPageIsDisplayed() throws Exception {
        // When
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys("a01561688@itesm.mx");
        WebElement passwordField = driver.findElement(By.id("passwd"));
        passwordField.sendKeys("thisishelp10");
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
        submitButton.click();
        WebElement logoutLink = driver.findElement(By.xpath("//a[@class='logout']"));
        logoutLink.click();

        // Then
        assertEquals("Login - My Store", driver.getTitle());
    }

}
