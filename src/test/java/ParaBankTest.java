import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ParaBankTest {

    TestingParaBank bank = new TestingParaBank();
    private final String data = "maya";

    @BeforeEach
    public void init()
    {
        System.setProperty("webdriver.chrome.driver",  "chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("ignore-certificate-errors");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        //options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");

        bank.driver = new ChromeDriver(options);
        bank.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @AfterEach
    public void quitDriver()
    {
        bank.driver.quit();
    }

    @Test
    public void testLogin() throws InterruptedException {
        RegisterAndLogin registerAndLogin = new RegisterAndLogin();
        registerAndLogin.navigate();

        registerAndLogin.RegisterAccount(data);
        Thread.sleep(2000);
        registerAndLogin.LoginToAccount(data);
        Thread.sleep(2000);


        Assertions.assertEquals("Welcome " + data, registerAndLogin.getWelcomeMessage());
    }

    @Test
    public void testAccountBalance() throws InterruptedException {
        RegisterAndLogin registerAndLogin = new RegisterAndLogin();
        registerAndLogin.navigate();

        registerAndLogin.LoginToAccount(data);
        Thread.sleep(2000);
        BankAccount bankAccount = new BankAccount();
        Assertions.assertTrue(bankAccount.getBalance());
    }

    @Test
    public  void testOpenNewAccount() throws InterruptedException {
        RegisterAndLogin registerAndLogin = new RegisterAndLogin();
        registerAndLogin.navigate();

        registerAndLogin.LoginToAccount(data);
        Thread.sleep(2000);
        BankAccount bankAccount = new BankAccount();
        bankAccount.openNewAccount("1");

        Assertions.assertEquals("Account Opened!", bankAccount.getTextTitleAccount());
    }

    private void regAndLog() throws InterruptedException {
        RegisterAndLogin registerAndLogin = new RegisterAndLogin();
        registerAndLogin.navigate();

        registerAndLogin.RegisterAccount(data);
        Thread.sleep(2000);
        registerAndLogin.LoginToAccount(data);
        Thread.sleep(2000);
    }
    @Test
    public void testPayBillForSubAccount() throws InterruptedException {
        regAndLog();

        BankAccount bankAccount = new BankAccount();
        int accountNumber = bankAccount.getAccountNumber();
        String actual = bankAccount.transferBetweenAccounts(accountNumber, 10);

        Assertions.assertEquals("Bill Payment Complete", actual);
    }

    @Test
    public void testFindTransactionByID() throws InterruptedException {
        regAndLog();

        BankAccount bankAccount = new BankAccount();
        Object[] expected = bankAccount.getAccountOverviewData().entrySet().toArray();
        Object[] actual = bankAccount.findTransactionById(BankAccount.transferId).entrySet().toArray();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testTransferMoney() throws InterruptedException {
        regAndLog();

        BankAccount bankAccount = new BankAccount();

        Assertions.assertEquals("Transfer Complete!", bankAccount.transferMoney(10));
    }

    @Test
    public void testGetLoan() throws InterruptedException {
        regAndLog();

        BankAccount bankAccount = new BankAccount();
        String expected = "Congratulations, your loan has been approved.";

        Assertions.assertEquals(expected, bankAccount.getLoan(1000));
    }

    @Test
    public void testUpdatePhoneNumber() throws InterruptedException {
        regAndLog();

        BankAccount bankAccount = new BankAccount();
        int expected = 30555;
        Assertions.assertEquals(expected, bankAccount.setNewPhoneNumber(expected));
    }
}
