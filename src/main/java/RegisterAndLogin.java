import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RegisterAndLogin extends TestingParaBank{

    public RegisterAndLogin()
    {
        super();
    }

    private final String url = "https://parabank.parasoft.com/";
    public void navigate()
    {
        driver.navigate().to(url);
    }

    private final By BUTTON_REGISTER = By.linkText("Register");

    private final By TABLE_REGISTER = By.xpath("//table//tr/td[2]/input[@class='input']");
    private final By REGISTER_BUTTON = By.xpath("//table//tr/td[2]/input[@value='Register']");
    public void RegisterAccount(String data)
    {
        driver.findElement(BUTTON_REGISTER).click();
        List<WebElement> rows = driver.findElements(TABLE_REGISTER);
        for(WebElement row: rows){
            row.sendKeys(data);
        }
        driver.findElement(REGISTER_BUTTON).click();
    }

    private final By TEXT_USERNAME = By.xpath("//div[@class='login']/input[@name='username']");
    private final By TEXT_PASSWORD = By.xpath("//div[@class='login']/input[@name='password']");
    private final By BUTTON_LOGIN = By.xpath("//div[@class='login']/input[@value='Log In']");
    public void LoginToAccount(String data)
    {
        driver.findElement(TEXT_USERNAME).sendKeys(data);
        driver.findElement(TEXT_PASSWORD).sendKeys(data);
        driver.findElement(BUTTON_LOGIN).click();
    }

    private final By TEXT_TITLE = By.xpath("//h1[@class='title']");
    public String getWelcomeMessage()
    {
        return driver.findElement(TEXT_TITLE).getText();
    }
}
