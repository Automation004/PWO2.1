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

public class SectionPom extends CommonData {
	private ExtentTest test;
	public Properties pro;
	String expectedValidationMessage = "Please Enter Section";

	// Constructor
	public SectionPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	Actions a = new Actions(driver);

	@FindBy(xpath = "//button[normalize-space()='Create']")
	private WebElement createButtonClick;

	@FindBy(xpath = "//a[normalize-space()='Section']")
	protected WebElement SectionClick;

	@FindBy(xpath = "//input[@formcontrolname='name']")
	private WebElement enterDataSection;

	@FindBy(css = "body app-root div[class='modal-body'] div div:nth-child(2)")
	private WebElement validationMessage;

	public void create(String Section) throws Exception {
		try {
			// Click on the Masters section and wait for the dropdown to expand
			test.log(Status.INFO, "Navigating to Master section");
			a.moveToElement(masterClick).perform();
			test.log(Status.PASS, "Master section clicked successfully");

			test.log(Status.INFO, "Clicking on Section");
			SectionClick.click();
			test.log(Status.PASS, "Section clicked successfully");

			test.log(Status.INFO, "Clicking on Create button");
			createButtonClick.click();
			test.log(Status.PASS, "Create button clicked successfully");

			test.log(Status.INFO, "Submitting the form without giving data");
			submitButton();
			test.log(Status.PASS, "Submitted button clicked without giving data");

			verifyValidationMessage(validationMessage, pro.getProperty("Section_ValidationMessage"));

			test.log(Status.INFO, "Entering data in Section field");
			enterDataSection.sendKeys(Section);
			test.log(Status.PASS, "Data entered in Section field successfully: " + Section);

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

	public void update(String Section, String SectionUpdate) throws Exception {
		try {
			test.log(Status.INFO, "Searching for Section: " + Section);
			SearchBox(Section);
			test.log(Status.PASS, "Section found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			editButton();
			test.log(Status.PASS, "Edit button clicked");

			Thread.sleep(1000);

			test.log(Status.INFO, "Clearing existing data in Section field");
			enterDataSection.clear();
			test.log(Status.PASS, "Existing data cleared");

			test.log(Status.INFO, "Entering updated data in Section field");
			enterDataSection.sendKeys(SectionUpdate);
			test.log(Status.PASS, "Updated data entered successfully with: " + SectionUpdate);

			test.log(Status.INFO, "Adding comments: " + Section + " updated");
			Comments(Section + " updated");
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
