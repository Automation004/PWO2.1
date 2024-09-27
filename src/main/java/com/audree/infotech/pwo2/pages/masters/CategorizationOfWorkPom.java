package com.audree.infotech.pwo2.pages.masters;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.audree.infotech.pwo2.utils.CommonData;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class CategorizationOfWorkPom extends CommonData {
	private ExtentTest test;
	public Properties pro;
	public String workOrderIdText;

	public CategorizationOfWorkPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[contains(text(),'Categorization Of Work')]")
	WebElement categorizationOfWorkTabClick;

	@FindBy(xpath = "//input[@placeholder='Search...']")
	WebElement searchField;

	@FindBy(xpath = "(//a[@class='work-order-link'])[1]")
	private WebElement workOrderIdClick;

	@FindBy(xpath = "//input[@formcontrolname='comments']")
	WebElement commentsFeild;

	@FindBy(xpath = "//input[@formcontrolname='activity']")
	WebElement activityFeild;

	public void categorizationOfWorkActions() {
		test.log(Status.INFO, "Clicking on the 'Impact Assessment");
		categorizationOfWorkTabClick.click();
		test.log(Status.PASS, "'Impact Assessment' tab clicked successfully.");
	}

	// Search Work Order
	public void searchWorkOrder(String workOrder) throws InterruptedException {
		searchField.click();
		searchField.sendKeys(workOrder);
		Thread.sleep(1000); // Allow time for search results to populate
		test.log(Status.PASS, "Searched for Work Order: " + workOrder);
	}

	// Select Work Order
	public void selectWorkOrder() {
		workOrderIdClick.click();
		test.log(Status.PASS, "Selected Work Order");
	}

	// Method to set value for Categorization Of Work-dropdown
	public void setCategorizationOfWorkDropdown(String value) throws Exception {
		textBoxOne(value);
		test.log(Status.PASS, "Given value for Categorization Of Work: " + value);

	}

	// Method to set value for Type Of QMS Tool
	public void setTypeOfQmsTool(String value) throws Exception {
		textBoxTwo(value);
		test.log(Status.PASS, "Given value for Type Of Qms Tool: " + value);
	}

	// Method to set value for QMS ID
	public void setQmsId(String value) throws Exception {
		textBoxThree(value);
		test.log(Status.PASS, "Given value for QmsId: " + value);
	}

	// Method to enter comments
	public void enterComments(String comments) {
		test.log(Status.INFO, "Entering comments: " + comments);
		commentsFeild.clear();
		commentsFeild.sendKeys(comments);
		test.log(Status.PASS, "Entered comments: " + comments);
	}

	public void saveAction() throws Exception {
		try {
			test.log(Status.INFO, "Saving with eSignature.");
			saveEsigantureActions();
			test.log(Status.PASS, "Saved with eSignature successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to save with eSignature. Error: " + e.getMessage());
			throw e;
		}
	}

	public void submitAction() throws Exception {
		try {
			test.log(Status.INFO, "Submitting with eSignature.");
			submitEsigantureActions();
			test.log(Status.PASS, "Submitted with eSignature successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to submit with eSignature. Error: " + e.getMessage());
			throw e;
		}
	}
}
