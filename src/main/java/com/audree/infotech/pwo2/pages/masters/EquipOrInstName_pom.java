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

public class EquipOrInstName_pom extends CommonData {
	private ExtentTest test;
	public Properties pro;
	public Actions a = new Actions(driver);

	// String expectedValidationMessage = "Please Enter Equip/Inst Name";

	// Constructor
	public EquipOrInstName_pom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[normalize-space()='Equip/Inst Name']")
	protected WebElement equipOrInstNameClick;

	@FindBy(xpath = "//label[contains(text(),'Show')]")
	protected WebElement EquipMasterLabel;

	@FindBy(xpath = "//button[normalize-space()='Create']")
	public WebElement createButtonClick;

	@FindBy(css = "div[class='form-group'] div")
	private WebElement validationMessage;

	@FindBy(xpath = "//input[@formcontrolname='name']")
	private WebElement enterDataEquipOrInstName;

	public void create(String equipOrInstName) throws Exception {
		try {
			// Click on the Masters section and wait for the dropdown to expand
			test.log(Status.INFO, "Navigating to Master section");
			a.moveToElement(masterClick).perform();
			test.log(Status.PASS, "Master section clicked successfully");

			// Wait for the Equip/Inst Name link to appear after clicking Masters
			waitForWebElementToAppear(equipOrInstNameClick);

			// Click on the Equip/Inst Name
			test.log(Status.INFO, "Clicking on Equip/Inst Name");
			equipOrInstNameClick.click();
			test.log(Status.PASS, "Equip/Inst Name clicked successfully");

			test.log(Status.INFO, "Clicking on Create button");
			createButtonClick.click();
			test.log(Status.PASS, "Create button clicked successfully");

			test.log(Status.INFO, "Submitting the form without giving data");
			submitButton();
			test.log(Status.PASS, "Submitted button clicked without giving data");

			verifyValidationMessage(validationMessage, pro.getProperty("Equip/Inst_ValidationMessage"));

			test.log(Status.INFO, "Entering data in Equip/Inst Name field");
			enterDataEquipOrInstName.sendKeys(equipOrInstName);
			test.log(Status.PASS, "Data entered in Equip/Inst Name field successfully: " + equipOrInstName);

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

	public void update(String equipOrInstName, String EquipOrInstNameUpdate) throws Exception {
		try {
			test.log(Status.INFO, "Searching for Equip/Inst Name: " + equipOrInstName);
			SearchBox(equipOrInstName);
			test.log(Status.PASS, "Equip/Inst Name found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			editButton();
			test.log(Status.PASS, "Edit button clicked");

			Thread.sleep(1000);

			test.log(Status.INFO, "Clearing existing data in Equip/Inst Name field");
			enterDataEquipOrInstName.clear();
			test.log(Status.PASS, "Existing data cleared");

			test.log(Status.INFO, "Entering updated data in Equip/Inst Name field");
			enterDataEquipOrInstName.sendKeys(EquipOrInstNameUpdate);
			test.log(Status.PASS, "Updated data entered successfully with: " + EquipOrInstNameUpdate);

			test.log(Status.INFO, "Adding comments: " + equipOrInstName + " updated");
			Comments(equipOrInstName + " updated");
			test.log(Status.PASS, "Comments added successfully");

			test.log(Status.INFO, "Clicking on Update button");
			UpdateButton();
			test.log(Status.PASS, "Update button clicked");

			noButton();
			test.log(Status.PASS, "No button clicked");

			UpdateButton();
			test.log(Status.PASS, "Update button clicked again");

			EsigantureActions();

		} catch (Exception e) {
			test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
			throw e;
		}
	}
}
