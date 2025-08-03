package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    // Locators (customized for dev-dash.janitri.in)
    private By emailInput = By.id("formEmail");
    private By passwordInput = By.id("formPassword");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.cssSelector(".error-msg, .error, .alert");
    private By togglePasswordButton = By.cssSelector(".password-visibility-toggle, .toggle-password");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Page Actions
    public void enterUserId(String email) {
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public boolean isLoginButtonEnabled() {
        return driver.findElement(loginButton).isEnabled();
    }

    public String getPasswordInputType() {
        return driver.findElement(passwordInput).getAttribute("type");
    }

    public void togglePasswordVisibility() {
        try {
            driver.findElement(togglePasswordButton).click();
        } catch (Exception e) {
            System.out.println("Password toggle button not found.");
        }
    }

    public String getErrorMessage() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean areElementsVisible() {
        return driver.findElement(emailInput).isDisplayed() &&
               driver.findElement(passwordInput).isDisplayed() &&
               driver.findElement(loginButton).isDisplayed();
    }
}
