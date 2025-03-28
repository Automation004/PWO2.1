package com.audree.infotech.pwo2.tests.masters;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.audree.infotech.pwo2.utils.CommonData;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class WorkOrderLogPom extends CommonData {
	private WebDriver driver;
	private ExtentTest test;
	private Actions actions;

	// Constructor
	public WorkOrderLogPom(WebDriver driver, ExtentTest test) {
		super(driver, test, pro);
		this.driver = driver;
		this.test = test;
		this.actions = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	// Web Elements
	@FindBy(xpath = "//a[normalize-space()='Reports']")
	private WebElement reportsTab;

	@FindBy(xpath = "//a[normalize-space()='Work Order Trend']")
	private WebElement workOrderTrend;

	@FindBy(xpath = "//a[normalize-space()='Work Order Log']")
	private WebElement workOrderLogTab;

	@FindBy(xpath = "(//div[@aria-haspopup='listbox'])[1]")
	private WebElement departmentName;

	@FindBy(xpath = "//input[@formcontrolname='fromDate']")
	private WebElement fromDate;

	@FindBy(xpath = "(//input[@type='date'])[2]")
	private WebElement toDate;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement workRelatedTo;

	@FindBy(xpath = "//i[@class='fa fa-check-circle']")
	private WebElement getButton;

	@FindBy(xpath = "//input[@id='yearly']")
	private WebElement radioYearly;

	// Actions

	// Navigate to Work Order Log
	public void navigateToWorkOrderLog() throws Exception {
		test.log(Status.INFO, "Clicking on the 'Work order log' link.");
		Thread.sleep(1000);
		actions.moveToElement(reportsTab).perform();
		test.log(Status.PASS, "'Reports' tab clicked successfully.");
		workOrderLogTab.click();
		test.log(Status.PASS, "'Work Order Log' tab clicked successfully.");
	}

	// Navigate to Work Order Log
	public void navigateToWorkOrderTrend() throws Exception {
		test.log(Status.INFO, "Clicking on the 'Work order log' link.");
		Thread.sleep(1000);
		actions.moveToElement(reportsTab).perform();
		test.log(Status.INFO, "Clicking on the 'Work order trend' link.");
		Thread.sleep(1000);
		workOrderTrend.click();
		test.log(Status.PASS, "'Work Order trend' tab clicked successfully.");
	}

	public void selectRadioYearly() {
		test.log(Status.PASS, "Selecting the radio button Yearly");
		radioYearly.click();
		test.log(Status.PASS, "Selected the radio button Yearly");
	}
	
    public void FromDate(String date) {
		test.log(Status.INFO, "Entering 'From Date': " + date);
        fromDate.click();
        fromDate.sendKeys(Keys.TAB,Keys.TAB.ENTER);
		test.log(Status.PASS, "From Date entered successfully.");
    }

	// Select Department from ComboBox
	public void selectInComboBox(String department) {
		try {
			test.log(Status.INFO, "Selecting department: " + department);
			departmentName.click();
			Thread.sleep(500);
			System.out.println(department);
			textBoxOne(department);
			Thread.sleep(500);
			test.log(Status.PASS, "Department '" + department + "' selected successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select department: " + e.getMessage());
		}
	}

	// Enter From Date
	public void enterFromDate(String x) {
		test.log(Status.INFO, "Entering 'From Date");
		fromDate.sendKeys(x);
		test.log(Status.PASS, "From Date entered successfully.");
	}

	// Enter To Date
	public void enterToDate(String date) throws InterruptedException {
		test.log(Status.INFO, "Entering 'To Date': " + date);
		Thread.sleep(300);
		toDate.sendKeys(date);
		test.log(Status.PASS, "To Date entered successfully.");
	}

	// Select Work Related To
	public void selectWorkRelatedTo(String workRelatedToData) throws Throwable {
		test.log(Status.INFO, "Selecting 'Work Related To': " + workRelatedToData);
		workRelatedTo.click();
		Thread.sleep(500);
		workRelatedTo.sendKeys(workRelatedToData);
		Thread.sleep(500);
		workRelatedTo.sendKeys(Keys.ENTER);
		test.log(Status.PASS, "'Work Related To' selected successfully.");
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
}
