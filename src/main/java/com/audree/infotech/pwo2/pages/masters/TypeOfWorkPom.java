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

public class TypeOfWorkPom extends CommonData {
	private ExtentTest test;
	public Properties pro;
	String expectedValidationMessage = "Please Enter Type Of Work";

	// Constructor
	public TypeOfWorkPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	Actions a = new Actions(driver);

	@FindBy(xpath = "//button[normalize-space()='Create']")
	private WebElement createButtonClick;

	@FindBy(xpath = "//a[normalize-space()='Type Of Work']")
	protected WebElement TypeOfWorkClick;

	@FindBy(xpath = "//input[@formcontrolname='title']")
	private WebElement enterDataTypeOfWork;

	@FindBy(css = "body app-root div[class='modal-body'] div div:nth-child(2)")
	private WebElement validationMessage;

	public void create(String TypeOfWork) throws Exception {
		try {
			// Click on the Masters section and wait for the dropdown to expand
			test.log(Status.INFO, "Navigating to Master section");
			a.moveToElement(masterClick).perform();
			test.log(Status.PASS, "Master section clicked successfully");

			test.log(Status.INFO, "Clicking on Type Of Work");
			TypeOfWorkClick.click();
			test.log(Status.PASS, "Type Of Work clicked successfully");

			test.log(Status.INFO, "Clicking on Create button");
			createButtonClick.click();
			test.log(Status.PASS, "Create button clicked successfully");

			test.log(Status.INFO, "Submitting the form without giving data");
			submitButton();
			test.log(Status.PASS, "Submitted button clicked without giving data");

			// validation message checking
			verifyValidationMessage(validationMessage, pro.getProperty("TypeOfWork_ValidationMessage"));

			test.log(Status.INFO, "Entering data in Type Of Work field");
			enterDataTypeOfWork.sendKeys(TypeOfWork);
			test.log(Status.PASS, "Data entered in Type Of Work field successfully: " + TypeOfWork);

			test.log(Status.INFO, "Submitting the form");
			submitButton();
			test.log(Status.PASS, "Submit button clicked");

			noButton();
			test.log(Status.PASS, "No button clicked");

			submitButton();
			test.log(Status.PASS, "Submit button clicked again");

			yesButton();
			test.log(Status.PASS, "Yes button clicked");

			EsigantureActions();

		} catch (Exception e) {
			test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
			throw e;
		}
	}

	public void update(String TypeOfWork, String TypeOfWorkUpdate) throws Exception {
		try {
			test.log(Status.INFO, "Searching for Type Of Work: " + TypeOfWork);
			SearchBox(TypeOfWork);
			test.log(Status.PASS, "Type Of Work found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			editButton();
			test.log(Status.PASS, "Edit button clicked");

			Thread.sleep(1000);

			test.log(Status.INFO, "Clearing existing data in Type Of Work field");
			enterDataTypeOfWork.clear();
			test.log(Status.PASS, "Existing data cleared");

			test.log(Status.INFO, "Entering updated data in Type Of Work field");
			enterDataTypeOfWork.sendKeys(TypeOfWorkUpdate);
			test.log(Status.PASS, "Updated data entered successfully with: " + TypeOfWorkUpdate);

			test.log(Status.INFO, "Adding comments: " + TypeOfWork + " updated");
			Comments(TypeOfWork + " updated");
			test.log(Status.PASS, "Comments added successfully");

			test.log(Status.INFO, "Clicking on Update button");
			UpdateButton();
			test.log(Status.PASS, "Update button clicked");

			noButton();
			test.log(Status.PASS, "No button clicked");

			UpdateButton();
			test.log(Status.PASS, "Update button clicked again");

			yesButton();
			test.log(Status.PASS, "Yes button clicked");

			EsigantureActions();

		} catch (Exception e) {
			test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
			throw e;
		}
	}
}
