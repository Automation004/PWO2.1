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

public class MaterialNamePom extends CommonData {
	private ExtentTest test;
	public Properties pro;
	String expectedValidationMessage = "Please Enter Material Name";

	// Constructor
	public MaterialNamePom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}
	Actions a = new Actions(driver);
	
	@FindBy(xpath = "//button[normalize-space()='Create']")
	private WebElement createButtonClick;

	@FindBy(xpath = "//a[normalize-space()='Material Name']")
	protected WebElement materialNameClick;

	@FindBy(xpath = "//input[@formcontrolname='name']")
	private WebElement enterDataMaterialName;

	@FindBy(css = "body app-root div[class='modal-body'] div div:nth-child(2)")
	private WebElement validationMessage;

	public void create(String materialName) throws Exception {
		try {
			// Click on the Masters section and wait for the dropdown to expand
			test.log(Status.INFO, "Navigating to Master section");
			a.moveToElement(masterClick).perform();
			test.log(Status.PASS, "Master section clicked successfully");
			
			test.log(Status.INFO, "Clicking on Material Name");
			materialNameClick.click();
			test.log(Status.PASS, "Material Name clicked successfully");

			test.log(Status.INFO, "Clicking on Create button");
			createButtonClick.click();
			test.log(Status.PASS, "Create button clicked successfully");

			test.log(Status.INFO, "Submitting the form without giving data");
			submitButton();
			test.log(Status.PASS, "Submitted button clicked without giving data");

			// verification of validation message
			verifyValidationMessage(validationMessage, pro.getProperty("MaterialName_ValidationMessage"));

			test.log(Status.INFO, "Entering data in Material Name field");
			enterDataMaterialName.sendKeys(materialName);
			test.log(Status.PASS, "Data entered in Material Name field successfully: " + materialName);

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

	public void update(String materialName, String materialNameUpdate) throws Exception {
		try {
			test.log(Status.INFO, "Searching for Material Name: " + materialName);
			SearchBox(materialName);
			test.log(Status.PASS, "Material Name found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			editButton();
			test.log(Status.PASS, "Edit button clicked");

			Thread.sleep(1000);

			test.log(Status.INFO, "Clearing existing data in Material Name field");
			enterDataMaterialName.clear();
			test.log(Status.PASS, "Existing data cleared");

			test.log(Status.INFO, "Entering updated data in Material Name field");
			enterDataMaterialName.sendKeys(materialNameUpdate);
			test.log(Status.PASS, "Updated data entered successfully with: " + materialNameUpdate);

			test.log(Status.INFO, "Adding comments: " + materialName + " updated");
			Comments(materialName + " updated");
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
