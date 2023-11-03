import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.List;

public class BankAccount extends TestingParaBank{

    public BankAccount()
    {
        super();
    }
    //TODO Checking Balance
    private final By BALANCE = By.xpath("//b[@class=\"ng-binding\"]");
    public boolean getBalance()
    {
        return driver.findElement(BALANCE).isDisplayed();
    }
    //TODO Open New Account
    private final By BUTTON_OPEN_ACCOUNT = By.xpath("//a[text()='Open New Account']");
    private final By BUTTON_SELECT = By.xpath("//select[@id='type']");
    private final By BUTTON_ACCOUNT_SUBMIT = By.xpath("//input[@value='Open New Account']");
    private final By TEXT_TITLE_ACCOUNT = By.xpath("//div[@ng-if='showResult']/h1[@class='title']");
    public void openNewAccount(String typeOfAccount) throws InterruptedException {
        driver.findElement(BUTTON_OPEN_ACCOUNT).click();
        Select select = new Select(driver.findElement(BUTTON_SELECT));
        select.selectByValue(typeOfAccount);
        Thread.sleep(2000);
        driver.findElement(BUTTON_ACCOUNT_SUBMIT).click();
    }
    public String getTextTitleAccount()
    {
        return driver.findElement(TEXT_TITLE_ACCOUNT).getText();
    }

    //TODO BIll Payment

    private final By BUTTON_OVERVIEW = By.xpath("//div[@id='leftPanel']//a[text()='Accounts Overview']");
    private final By ROW_ACCOUNT_NUMBER = By.xpath("//table[@id='accountTable']/tbody/tr/td[1]/a");
    public int getAccountNumber() throws InterruptedException {
        driver.findElement(BUTTON_OVERVIEW).click();
        Thread.sleep(2000);
        List<WebElement> rows = driver.findElements(ROW_ACCOUNT_NUMBER);
        String accountNumber = rows.get(0).getText();
        return Integer.parseInt(accountNumber);
    }
    private final By BUTTON_BILL_PAY = By.xpath("//div[@id='leftPanel']//a[text() = 'Bill Pay']");
    private final By TEXT_ACCOUNT_NUMBER = By.xpath("//div[@id='rightPanel']//table/tbody/tr//input[@name='payee.accountNumber']");
    private final By TEXT_VERIFY_ACCOUNT_NUMBER = By.xpath("//div[@id='rightPanel']//table/tbody/tr//input[@name='verifyAccount']");
    private final By TEXT_AMOUNT = By.xpath("//div[@id='rightPanel']//table/tbody/tr//input[@name='amount']");
    private final By BUTTON_SEND_PAYMENT = By.xpath("//div[@id='rightPanel']//table/tbody/tr//input[@value='Send Payment']");
    private final By TEXT_ACCOUNT_INFO = By.xpath("//table[@class='form2']/tbody/tr//input");
    private final By TEXT_TITLE_BILL = By.xpath("//div[@ng-show='showResult']/h1[@class='title']");
    public String transferBetweenAccounts(int accountNumber, int amount) throws InterruptedException
    {
        driver.findElement(BUTTON_BILL_PAY).click();
        Thread.sleep(1500);
        List<WebElement> rows = driver.findElements(TEXT_ACCOUNT_INFO).subList(0,6);
        for(WebElement row: rows){
            row.sendKeys("oke");
        }
        driver.findElement(TEXT_ACCOUNT_NUMBER).sendKeys(String.valueOf(accountNumber));
        Thread.sleep(1500);
        driver.findElement(TEXT_VERIFY_ACCOUNT_NUMBER).sendKeys(String.valueOf(accountNumber));
        Thread.sleep(1500);
        driver.findElement(TEXT_AMOUNT).sendKeys(String.valueOf(amount));
        Thread.sleep(1500);
        driver.findElement(BUTTON_SEND_PAYMENT).click();
        Thread.sleep(1500);
        return  driver.findElement(TEXT_TITLE_BILL).getText();
    }

    //TODO Transaction ID

    private final By BUTTON_TRANSFER_SENT = By.xpath("//table[@id='transactionTable']/tbody/tr/td[2]");
    private final By HASHMAP_KEY_DATA = By.xpath("//div[@id='rightPanel']//tbody/tr/td/b");
    private final By HASHMAP_VALUE_DATA = By.xpath("//div[@id='rightPanel']//tbody/tr/td[2]");

    public static int transferId = 0;
    public HashMap<String, String> getAccountOverviewData()
    {
        driver.findElement(BUTTON_OVERVIEW).click();
        List<WebElement> rows = driver.findElements(ROW_ACCOUNT_NUMBER);
        rows.get(0).click();
        driver.findElement(BUTTON_TRANSFER_SENT).click();
        HashMap<String, String> tableData = new HashMap<>();
        List<WebElement> keyList = driver.findElements(HASHMAP_KEY_DATA);
        List<WebElement> valueList = driver.findElements(HASHMAP_VALUE_DATA);
        transferId = Integer.parseInt(valueList.get(0).getText());
        for(int i = 0; i < keyList.size(); i++)
        {
            tableData.put(keyList.get(i).getText(), valueList.get(i).getText());
        }
        return tableData;
    }

    private final By BUTTON_FIND_TRANSACTION = By.xpath("//div[@id='leftPanel']//a[text()='Find Transactions']");
    private final By FIELD_TRANSACTION_ID = By.xpath("//input[@id='criteria.transactionId']");
    private final By BUTTON_FIND_TRANS_ID = By.xpath("//button[@ng-click=\"criteria.searchType = 'ID'\"]");
    private final By BUTTON_TRANSFER = By.linkText("Bill Payment to oke");
    public HashMap<String, String> findTransactionById(int transferId)
    {
        driver.findElement(BUTTON_FIND_TRANSACTION).click();
        driver.findElement(FIELD_TRANSACTION_ID).sendKeys(String.valueOf(transferId));
        driver.findElement(BUTTON_FIND_TRANS_ID).click();
        driver.findElement(BUTTON_TRANSFER).click();

        HashMap<String, String> tableData = new HashMap<>();
        List<WebElement> keyList = driver.findElements(HASHMAP_KEY_DATA);
        List<WebElement> valueList = driver.findElements(HASHMAP_VALUE_DATA);
        for(int i = 0; i < keyList.size(); i++)
        {
            tableData.put(keyList.get(i).getText(), valueList.get(i).getText());
        }
        return tableData;
    }

    //TODO Transfer money from an existing account to another one

    private final By BUTTON_TRANSFER_FUNDS = By.xpath("//div[@id='leftPanel']//a[text()='Transfer Funds']");
    private final By TEXT_AMOUNT_TO_SEND = By.xpath("//input[@id='amount']");
    private final By SELECT_TO_ACCOUNT = By.id("toAccountId");
    private final By BUTTON_TRANSFER_MONEY = By.xpath("//input[@value='Transfer']");
    private final By TEXT_SUCCESS_TRANSFER = By.xpath("//input[@value='Transfer']");


    public String transferMoney(int amount) throws InterruptedException {

        driver.findElement(BUTTON_TRANSFER_FUNDS).click();
        driver.findElement(TEXT_AMOUNT_TO_SEND).sendKeys(String.valueOf(amount));

        Select select = new Select(driver.findElement(SELECT_TO_ACCOUNT));
        select.getOptions().get(0).click();
        driver.findElement(BUTTON_TRANSFER_MONEY).click();
        return driver.findElement(TEXT_SUCCESS_TRANSFER).getText();
    }

    //TODO Request loan
    private final By BUTTON_REQUEST_LOAN = By.xpath("//div[@id='leftPanel']//a[text()='Request Loan']");
    private final By TEXT_AMOUNT_LOAN = By.id("amount");
    private final By TEXT_DOWN_AMOUNT = By.id("downPayment");
    private final By BUTTON_APPLY_NOW = By.xpath("//input[@value='Apply Now']");
    private final By TEXT_SUCCESS_LOAN = By.xpath("//div[@ng-if='loanResponse.approved']/p[1]");

    public String getLoan(int loanAmount)
    {
        driver.findElement(BUTTON_REQUEST_LOAN).click();
        driver.findElement(TEXT_AMOUNT_LOAN).sendKeys(String.valueOf(loanAmount));
        driver.findElement(TEXT_DOWN_AMOUNT).sendKeys(String.valueOf(loanAmount));
        driver.findElement(BUTTON_APPLY_NOW).click();
        return driver.findElement(TEXT_SUCCESS_LOAN).getText();
    }

    //TODO Update phone number

    private final By BUTTON_UPDATE_INFO = By.xpath("//div[@id='leftPanel']//a[text()='Update Contact Info']");
    //private final By TEXT_PHONE_NUMBER = By.xpath("//div[@ng-app='UpdateProfileApp']//tbody/tr//b[contains(text(), 'Pho')]/following::td[1]");
    private final By BUTTON_SUBMIT = By.xpath("//div[@ng-app='UpdateProfileApp']//tbody/tr/td/input[@type='submit']");
    private final By TEXT_PHONE_NUMBER = By.id("customer.phoneNumber");

    public int setNewPhoneNumber(int phoneNumber) throws InterruptedException {
        driver.findElement(BUTTON_UPDATE_INFO).click();
        Thread.sleep(1500);
        driver.findElement(TEXT_PHONE_NUMBER).clear();
        driver.findElement(TEXT_PHONE_NUMBER).sendKeys(String.valueOf(phoneNumber));
        Thread.sleep(1500);
        driver.findElement(BUTTON_SUBMIT).click();
        Thread.sleep(1500);
        driver.findElement(BUTTON_UPDATE_INFO).click();
        Thread.sleep(1500);
        try {
            int result = Integer.parseInt(driver.findElement(TEXT_PHONE_NUMBER).getText());
            return result;
        } catch (NumberFormatException e) {
            System.out.println("Phone number is not appropriate");
        }
        return 0;
    }
}
