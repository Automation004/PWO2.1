package com.audree.infotech.pwo2.pages.masters;

import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.audree.infotech.pwo2.utils.CommonData;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class MaterialCodePom extends CommonData {
	private ExtentTest test;
	public Properties pro;

	// Constructor
	public MaterialCodePom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	Actions a = new Actions(driver);

	@FindBy(xpath = "//button[normalize-space()='Create']")
	private WebElement createButtonClick;

	@FindBy(xpath = "//a[normalize-space()='Material Code']")
	protected WebElement materialCodeClick;

	@FindBy(xpath = "//select[@formcontrolname='materialId']")
	private WebElement MaterialNameDropdown;

	@FindBy(xpath = "//input[@formcontrolname='code']")
	private WebElement enterDataMaterialCode;

	@FindBy(xpath = "//input[@formcontrolname='uom']")
	private WebElement uom;

	@FindBy(css = "body app-root div[class='modal-body'] div div:nth-child(2)")
	private WebElement materialNameValidationMessage;

	@FindBy(css = "body app-root div[class='modal-body'] div div:nth-child(4)")
	private WebElement materialCodeValidationMessage;

	@FindBy(css = "body app-root div[class='modal-body'] div div:nth-child(6)")
	private WebElement UomValidationMessage;

	public void create(String materialName, String materialCode) throws Exception {
		try {
			// Click on the Masters section and wait for the dropdown to expand
			test.log(Status.INFO, "Navigating to Master section");
			a.moveToElement(masterClick).perform();
			test.log(Status.PASS, "Master section clicked successfully");

			test.log(Status.INFO, "Clicking on Material Code");
			materialCodeClick.click();
			test.log(Status.PASS, "Material Code clicked successfully");

			test.log(Status.INFO, "Clicking on Create button");
			createButtonClick.click();
			test.log(Status.PASS, "Create button clicked successfully");

			test.log(Status.INFO, "Adding the form without giving data");
			Add_Button();
			test.log(Status.PASS, "Added the form without giving data");

			// Verifying Validation Message
			verifyValidationMessage(materialNameValidationMessage,
					pro.getProperty("MaterialNameValidationMessageDropdown"));

			// Verifying Validation Message
			verifyValidationMessage(materialCodeValidationMessage, pro.getProperty("MaterialCode_ValidationMessage"));

			// Verifying Validation Message
			verifyValidationMessage(UomValidationMessage, pro.getProperty("Uom_ValidationMessage"));

			test.log(Status.INFO, "Clicking & Giving data on Material Name Dropdown");
			MaterialNameDropdown.click();
			MaterialNameDropdown.sendKeys(materialName, Keys.ENTER);
			test.log(Status.INFO, "Clicked & Given data on Material Name Dropdown");

			test.log(Status.INFO, "Entering data in Material Code field");
			enterDataMaterialCode.sendKeys(materialCode);
			test.log(Status.PASS, "Data entered in Material Code field successfully: " + materialCode);

			test.log(Status.INFO, "Entering data in uom field");
			uom.sendKeys("10KG");// NEED TO UPDATE
			test.log(Status.PASS, "Entered data in uom field" + materialCode);

			test.log(Status.INFO, "Adding the form");
			Add_Button();
			test.log(Status.PASS, "added button clicked");

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

	public void update(String MaterialNameUpdate, String materialCode, String materialCodeUpdate) throws Exception {
		try {
			test.log(Status.INFO, "Searching for Material Code: " + materialCode);
			SearchBox(MaterialNameUpdate);
			test.log(Status.PASS, "Material Code found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			editButton();
			test.log(Status.PASS, "Edit button clicked");

			test.log(Status.INFO, "Clearing existing data in Material Code field");
			Thread.sleep(500);
			enterDataMaterialCode.clear();
			test.log(Status.PASS, "Existing data cleared");

			test.log(Status.INFO, "Entering updated data in Material Code field");
			enterDataMaterialCode.sendKeys(materialCodeUpdate);
			test.log(Status.PASS, "Updated data entered successfully with: " + materialCodeUpdate);

			test.log(Status.INFO, "Adding comments: " + materialCode + " updated");
			Comments(materialCode + " updated");
			test.log(Status.PASS, "Comments added successfully");

			test.log(Status.INFO, "Clicking on Update button");
			UpdateButton();
			test.log(Status.PASS, "Update button clicked");

			noButton();
			test.log(Status.PASS, "No button clicked");
			Thread.sleep(1000);

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
