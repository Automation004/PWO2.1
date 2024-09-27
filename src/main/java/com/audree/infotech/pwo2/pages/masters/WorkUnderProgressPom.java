package com.audree.infotech.pwo2.pages.masters;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.audree.infotech.pwo2.utils.CommonData;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class WorkUnderProgressPom extends CommonData {
	private ExtentTest test;
	public Properties pro;
	public String workOrderIdText;

	// Constructor
	public WorkUnderProgressPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "(//div[contains(text(),'Work Under Progress')])[1]")
	WebElement workUnderProgresstdashBoard;

	@FindBy(xpath = "//input[@placeholder='Search...']")
	WebElement searchField;

	@FindBy(xpath = "(//a[@class='work-order-link'])[1]")
	private WebElement workOrderIdClick;

	@FindBy(xpath = "//input[@formcontrolname='activity']")
	WebElement activityFeild;

	@FindBy(xpath = "//textarea[@formcontrolname='comments']")
	WebElement commentsFeild;

	public void workUnderProgress() {
		test.log(Status.INFO, "Clicking on the 'Working Under Progress' tab.");
		workUnderProgresstdashBoard.click();
		test.log(Status.PASS, "'Working Under Progress' tab clicked successfully.");
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

	// Enter Activity and Save
	public void enterActivityAndSave(String activity) throws Exception {
		activityFeild.click();
		Thread.sleep(500);
		activityFeild.sendKeys(activity);
		test.log(Status.PASS, "Entered Activity: " + activity);
		saveButton();
		firstSaveEsigantureActionsForWUP();
		test.log(Status.PASS, "Clicked Save");
	}

	public void enterActivityAndSubmit(String activity) throws Exception {
		editButton();
		Thread.sleep(500);
		submitButton();
		firstSubmitEsigantureActionsForWUP();
		Thread.sleep(1000);
	}

	public void saveAction(String detailOfTrail) throws Exception {
		try {
			scrollPagedown();
			if (detailOfTrail.equalsIgnoreCase("Repaired")) {
				radioButton1();
				commentsFeild.sendKeys("Preferred with Repairable");
				test.log(Status.PASS, "Given comments as : " + "Preferred with Repairable");

			} else if (detailOfTrail.equalsIgnoreCase("Not Repairable")) {
				radioButton2();
				commentsFeild.sendKeys("Preferred with Not Repairable");
				test.log(Status.PASS, "Given comments as : " + "Preferred with Not Repairable");
			} else {
				radioButton3();
				commentsFeild.sendKeys("Preferred with NA");
				test.log(Status.PASS, "Given comments as : " + "Preferred with NA");
			}
			test.log(Status.INFO, "Saving with eSignature.");
			Thread.sleep(500);
			scrollPagedown();
			Save_Button2();
			firstSaveEsigantureActionsForWUP();
			test.log(Status.PASS, "Saved with eSignature successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to save with eSignature. Error: " + e.getMessage());
			throw e;
		}
	}

	public void submitAction() throws Exception {
		try {
			test.log(Status.INFO, "Submitting with eSignature.");
			Thread.sleep(500);
			scrollPagedown();
			submitButton2();
			firstSubmitEsigantureActionsForWUP();
			test.log(Status.PASS, "Submitted with eSignature successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to submit with eSignature. Error: " + e.getMessage());
			throw e;
		}
	}

}
