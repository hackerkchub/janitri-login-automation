package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginPageTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Automatically handles downloading the right ChromeDriver binary
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://dev-dash.janitri.in");
    }

    @Test(priority = 1)
    public void shouldAllowLoginButtonWhenFieldsAreEmpty() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        Assert.assertTrue(loginButton.isEnabled(), "Login button should be enabled when fields are empty (actual behavior)");
    }

    @Test(priority = 2)
    public void shouldTogglePasswordMasking() {
        WebElement passwordInput = driver.findElement(By.id("formPassword"));
        WebElement toggleButton = driver.findElement(By.cssSelector(".password-toggle-icon"));

        passwordInput.sendKeys("examplePassword");
        String typeBefore = passwordInput.getAttribute("type");

        toggleButton.click();

        String typeAfter = passwordInput.getAttribute("type");
        Assert.assertNotEquals(typeBefore, typeAfter, "Password type should toggle between 'password' and 'text'");
    }

    @Test(priority = 3)
    public void shouldShowErrorMessageForInvalidLogin() {
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

        emailInput.clear();
        passwordInput.clear();
        emailInput.sendKeys("invalid@email.com");
        passwordInput.sendKeys("wrongpassword");

        loginButton.click();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error-text")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message should be shown for invalid login");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
