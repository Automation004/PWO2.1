package com.audree.infotech.pwo2.tests.Transcations;

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
	private Actions a;

	// Constructor
	public WorkOrderLogPom(WebDriver driver, ExtentTest _test) {
		super(driver, _test, pro);
		this.driver = driver;
		this.test = _test;
		this.a = new Actions(driver);
		PageFactory.initElements(driver, this);
	}

	// Web Elements
	@FindBy(xpath = "//a[normalize-space()='Reports']")
	private WebElement reportsTab;

	@FindBy(xpath = "//a[normalize-space()='Work Order Log']")
	private WebElement workOrderLogTab;

	@FindBy(xpath = "//a[normalize-space()='Information Technology']")
	private WebElement departmentName;

	@FindBy(xpath = "//input[@formcontrolname='fromDate']")
	private WebElement fromDate;

	@FindBy(xpath = "//input[@formcontrolname='toDate']")
	private WebElement toDate;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement workRelatedTo;

	@FindBy(xpath = "//ng-select[@class='ng-select-searchable ng-select-clearable ng-select ng-select-single']//span[@class='ng-arrow-wrapper']")
	private WebElement selectDropdownArrow;

	@FindBy(xpath = "//i[@class='fa fa-check-circle']")
	private WebElement getButton;

	// Actions
	public void navigateToWorkOrderLog() {
		test.log(Status.INFO, "Clicking on the 'Reports' tab.");
		a.moveToElement(reportsTab).perform();
		test.log(Status.PASS, "'Reports' tab clicked successfully.");
		test.log(Status.INFO, "Clicking on the 'Work Order Log' tab.");
		workOrderLogTab.click();
		test.log(Status.PASS, "'Work Order Log' tab clicked successfully.");
	}

	public void selectInComboBox(String text) {
		try {
			test.log(Status.INFO, "Entering '" + text + "' in department Feild dropdown.");
			departmentName.click();
			departmentName.sendKeys(text, Keys.ENTER);
			test.log(Status.PASS, "Successfully entered '" + text + "' in combobox.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to enter '" + text + "' in combobox. Error: " + e.getMessage());
		}
	}

	public void enterFromDate(String date) {
		test.log(Status.INFO, "Entering 'From Date': " + date);
		fromDate.sendKeys(date);
		test.log(Status.PASS, "From Date entered successfully.");
	}

	public void enterToDate(String date) {
		test.log(Status.INFO, "Entering 'To Date': " + date);
		toDate.sendKeys(date);
		test.log(Status.PASS, "To Date entered successfully.");
	}

	public void selectEquipmentInstrument(String workRelatedToData) {
		test.log(Status.INFO, "Selecting 'work related to' from dropdown.");
		workRelatedTo.click();
		workRelatedTo.sendKeys(workRelatedToData, Keys.ENTER);
		test.log(Status.PASS, "'work related to' selected successfully.");
	}

	public void selectDropdownOption(String option) {
		test.log(Status.INFO, "Selecting '" + option + "' from dropdown.");
		selectDropdownArrow.click();
		departmentName.sendKeys(option, Keys.ENTER);
		test.log(Status.PASS, "'" + option + "' selected successfully.");
	}

	public void clickGetButton() {
		test.log(Status.INFO, "Clicking on 'Get' button.");
		getButton.click();
		test.log(Status.PASS, "'Get' button clicked successfully.");
	}

	// Additional methods to scroll down and up as per your requirement
	public void scrollDown() {
		test.log(Status.INFO, "Scrolling down the page.");
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
	}

	public void scrollUp() {
		test.log(Status.INFO, "Scrolling up the page.");
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0,-500)");
	}
}
