package com.audree.infotech.pwo2.pages.masters;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

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

public class InitiatialReviewByQaPom extends CommonData {

	private ExtentTest test;
	public Properties pro;
	public String workOrderIdText;

	// Constructor
	@SuppressWarnings("deprecation")
	public InitiatialReviewByQaPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}

	@FindBy(xpath = "//div[contains(text(),'Initial Review')]")
	private WebElement InitialReviewtab;

	@FindBy(xpath = "//input[@formcontrolname='comments']")
	private WebElement comments;

	@FindBy(xpath = "(//a[@class='work-order-link'])[1]")
	private WebElement workOrderIdClick;

	@FindBy(xpath = "//input[@formcontrolname='comments']")
	private WebElement returnCommnets;

	public void initialReview(String equipIdOrRoomId) throws Exception {
		test.log(Status.INFO, "Clicking on the 'Initiated' tab.");
		InitialReviewtab.click();
		test.log(Status.PASS, "'Initiated' tab clicked successfully.");
		SearchBox(equipIdOrRoomId);
		test.log(Status.PASS, "Searched " + equipIdOrRoomId);
		String workOrderIdText = workOrderIdClick.getText();
		System.out.println(workOrderIdText);
		workOrderIdClick.click();
		test.log(Status.PASS, "clicked on  " + equipIdOrRoomId + "link");
		if (comments.getText().trim().isEmpty()) {
			System.out.println("Need Commnets : " + comments.getText());
			System.out.println(comments.getText());
		} else {
			comments.sendKeys(equipIdOrRoomId + " record initial reviewed by QA sucessfully");
			System.out.println(comments.getText());
		}
		test.log(Status.PASS, "given comment in comment box");
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

	public void returnAction(String returnto) throws Exception {
		returnButton();
		// Check if the dropdown with formcontrolname 'returnTo' is present
		if (isElementPresent(By.xpath("//select[@formcontrolname='returnTo']"))) {
			returnToDropdown.click();
			Thread.sleep(500);
			returnToDropdown.sendKeys("Initiator", Keys.ENTER);
			test.log(Status.PASS, "Value selected from 'returnTo' dropdown");
			returnComments.sendKeys("Returned to the : " + returnto);
		} else {
			test.log(Status.INFO, "'EN Initial Receiver' dropdown not present, moving to the next step");
		}
		returnButton();
		test.log(Status.PASS, "Return button clicked");
		yesButton();
		test.log(Status.PASS, "Yes button clicked");
		password.sendKeys(pro.getProperty("Password"));
		test.log(Status.PASS, "Given password");
		submitButton2();
		test.log(Status.PASS, "Submit button clicked again");
		okButton();
		test.log(Status.PASS, "OK button clicked");
	}

}
