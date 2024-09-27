package com.audree.infotech.pwo2.pages.masters;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.audree.infotech.pwo2.utils.CommonData;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class WorkUnderClosurePom extends CommonData {
	private ExtentTest test;
	public Properties pro;
	public String workOrderIdText;

	// Constructor
	public WorkUnderClosurePom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//div[contains(text(),'Work Under Closure')])[1]")
	WebElement workUnderclosuredashBoard;

	@FindBy(xpath = "//input[@placeholder='Search...']")
	WebElement searchField;

	@FindBy(xpath = "(//a[@class='work-order-link'])[1]")
	private WebElement workOrderIdClick;

	// Locators for each element
	@FindBy(xpath = "(//input[@type='text'])[1]")
	WebElement selectDropdown;

	@FindBy(xpath = "//input[@formcontrolname='failureReason']")
	WebElement reasonForFailureInput;

	@FindBy(xpath = "//div[@class='ng-select-container']//input[@type='text']")
	WebElement materialCodeDropdown;

	@FindBy(xpath = "//input[@formcontrolname='quantity']")
	WebElement quantityInput;

	@FindBy(xpath = "//button[normalize-space()='Add']")
	WebElement addButton;

	@FindBy(xpath = "//input[@formcontrolname='comments']")
	WebElement conclusionInput;

	@FindBy(xpath = "//button[normalize-space()='Save']")
	WebElement saveButton;

	public void workUnderClosure() {
		test.log(Status.INFO, "Clicking on the 'Work Under Closure' tab.");
		workUnderclosuredashBoard.click();
		test.log(Status.PASS, "'Work Under Closure' tab clicked successfully.");
	}

	public void searchWorkOrder(String equipOrRoomId) throws InterruptedException {
		test.log(Status.INFO, "Searching for Work Order with ID: " + equipOrRoomId);
		searchField.click();
		searchField.sendKeys(equipOrRoomId);
		Thread.sleep(1000); // Allow time for search results
		test.log(Status.PASS, "Searched for Work Order: " + equipOrRoomId);
	}

	// Select Work Order
	public void selectWorkOrder() {
		test.log(Status.INFO, "Selecting the first Work Order from the search results.");
		workOrderIdClick.click();
		test.log(Status.PASS, "Selected the first Work Order successfully.");
	}

	// Method to select Failure Object from dropdown
	public void failureObject(String failureObject) throws InterruptedException {
		test.log(Status.INFO, "Selecting Failure Object: " + failureObject);
		selectDropdown.click();
		Thread.sleep(1000);
		selectDropdown.sendKeys(failureObject, Keys.ENTER);
		test.log(Status.PASS, "Selected Failure Object: " + failureObject);
	}

	// Method to enter Reason for Failure
	public void enterReasonForFailure(String reason) {
		test.log(Status.INFO, "Entering Reason for Failure: " + reason);
		reasonForFailureInput.clear();
		reasonForFailureInput.sendKeys(reason);
		test.log(Status.PASS, "Entered Reason for Failure: " + reason);
	}

	// Method to select Material Code option
	public void selectMaterialCode(String materialCode) throws InterruptedException {
		test.log(Status.INFO, "Selecting Material Code from dropdown.");
		materialCodeDropdown.click();Thread.sleep(1000);
		materialCodeDropdown.sendKeys(materialCode,Keys.ENTER);
		test.log(Status.PASS, "Material Code selected.");
	}

	// Method to enter Quantity
	public void enterQuantity(String quantity) {
		test.log(Status.INFO, "Entering Quantity: " + quantity);
		quantityInput.clear();
		quantityInput.sendKeys(quantity);
		test.log(Status.PASS, "Entered Quantity: " + quantity);
	}

	// Method to enter Quantity
	public void addButton() {
		test.log(Status.INFO, "Clicking on the Add button.");
		addButton.click();
		test.log(Status.PASS, "Clicked on the Add button.");
	}

	// Method to enter Conclusion
	public void enterConclusion(String conclusion) {
		test.log(Status.INFO, "Entering Conclusion: " + conclusion);
		conclusionInput.clear();
		conclusionInput.sendKeys(conclusion);
		test.log(Status.PASS, "Entered Conclusion: " + conclusion);
	}

	public void saveAction() throws Exception {
		try {
			test.log(Status.INFO, "Saving with eSignature.");
			Thread.sleep(500);
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
