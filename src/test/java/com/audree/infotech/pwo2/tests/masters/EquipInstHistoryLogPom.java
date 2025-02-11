package com.audree.infotech.pwo2.tests.masters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.audree.infotech.pwo2.utils.CommonData;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class EquipInstHistoryLogPom extends CommonData {
    private WebDriver driver;
    private ExtentTest test;
    private Actions actions;

    // Constructor
    public EquipInstHistoryLogPom(WebDriver driver, ExtentTest test) {
        super(driver, test, pro);
        this.driver = driver;
        this.test = test;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    // Web Elements
    @FindBy(xpath = "//a[normalize-space()='Reports']")
    private WebElement reportsTab;

    @FindBy(xpath = "//a[normalize-space()='Equip/Inst History Log']")
    private WebElement equipInstHistoryLogTab;

    @FindBy(xpath = "//input[@formcontrolname='fromDate']")
    private WebElement fromDate;

    @FindBy(xpath = "//input[@formcontrolname='toDate']")
    private WebElement toDate;

    @FindBy(xpath = "//button[normalize-space()='Get']")
    private WebElement getButton;

    @FindBy(xpath = "//button[@id='reset']")
    private WebElement resetButton;

    // Actions

    // Navigate to Equip/Inst History Log
    public void navigateToEquipInstHistoryLog() throws Exception {
        test.log(Status.INFO, "Clicking on the 'Reports' tab.");
        Thread.sleep(1000);
        actions.moveToElement(reportsTab).perform();
        test.log(Status.PASS, "'Reports' tab clicked successfully.");
        equipInstHistoryLogTab.click();
        test.log(Status.PASS, "'Equip/Inst History Log' tab clicked successfully.");
    }

    // Enter From Date
    public void enterFromDate(String date) throws Exception {
        test.log(Status.INFO, "Entering 'From Date': " + date);
        fromDate.clear(); // Clear any pre-filled value
        fromDate.sendKeys(date);Thread.sleep(300);
        test.log(Status.PASS, "'From Date' entered successfully.");
    }

    // Enter To Date
    public void enterToDate(String date) throws InterruptedException {
        test.log(Status.INFO, "Entering 'To Date': " + date);
        toDate.clear(); // Clear any pre-filled value
        toDate.sendKeys(date);Thread.sleep(300);
        test.log(Status.PASS, "'To Date' entered successfully.");
    }

    // Click Get Button
    public void clickGetButton() {
        test.log(Status.INFO, "Clicking on 'Get' button.");
        getButton.click();
        test.log(Status.PASS, "'Get' button clicked successfully.");
    }

    // Scroll Down
    public void scrollDown() {
        test.log(Status.INFO, "Scrolling down the page.");
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
    }

    // Scroll Up
    public void scrollUp() {
        test.log(Status.INFO, "Scrolling up the page.");
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0,-500)");
    }

    // Click Reset Button
    public void clickResetButton() {
        test.log(Status.INFO, "Clicking on 'Reset' button.");
        resetButton.click();
        test.log(Status.PASS, "'Reset' button clicked successfully.");
    }
}
