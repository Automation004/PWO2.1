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

public class BlockPom extends CommonData {
	private ExtentTest test;
	public Properties pro;

	// Constructor
	public BlockPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[normalize-space()='Create']")
	private WebElement createButtonClick;

	@FindBy(xpath = "//a[normalize-space()='Block']")
	protected WebElement BlockClick;

	@FindBy(xpath = "//input[@formcontrolname='name']")
	private WebElement enterDataBlock;

	@FindBy(css = "body app-root div[class='modal-body'] div div:nth-child(2)")
	private WebElement validationMessage;

	Actions a = new Actions(driver);

	public void create(String Block) throws Throwable {
		try {
			navigateToMasterBlock();
			clickBlock();
			clickCreateButton();
			submitFormWithoutData();
			verifyBlockValidationMessage();
			enterBlockData(Block);
			submitForm();
			handleConfirmationDialogs();
		} catch (Exception e) {
			test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
			throw e;
		}
	}

	private void navigateToMasterBlock() {
		// Click on the Masters section and wait for the dropdown to expand
		test.log(Status.INFO, "Navigating to Master section");
		a.moveToElement(masterClick).perform();
		test.log(Status.PASS, "Master section clicked successfully");
	}

	private void clickBlock() {
		test.log(Status.INFO, "Clicking on Block");
		BlockClick.click();
		test.log(Status.PASS, "Block clicked successfully");
	}

	private void clickCreateButton() {
		test.log(Status.INFO, "Clicking on Create button");
		createButtonClick.click();
		test.log(Status.PASS, "Create button clicked successfully");
	}

	private void submitFormWithoutData() throws Throwable {
		test.log(Status.INFO, "Submitting the form without giving data");
		submitButton();
		test.log(Status.PASS, "Submitted button clicked without giving data");
	}

	private void verifyBlockValidationMessage() {
		verifyValidationMessage(validationMessage, pro.getProperty("Block_ValidationMessage"));
	}

	private void enterBlockData(String Block) {
		test.log(Status.INFO, "Entering data in Block field");
		enterDataBlock.sendKeys(Block);
		test.log(Status.PASS, "Data entered in Block field successfully: " + Block);
	}

	private void submitForm() throws Exception {
		test.log(Status.INFO, "Submitting the form");
		submitButton();
		test.log(Status.PASS, "Submit button clicked");
	}

	private void handleConfirmationDialogs() throws Exception {
		noButton();
		test.log(Status.PASS, "No button clicked");
		submitButton();
		test.log(Status.PASS, "Submit button clicked again");

		EsigantureActions();
	}

	public void update(String Block, String BlockUpdate) throws Exception {
		try {
			test.log(Status.INFO, "Searching for Block: " + Block);
			SearchBox(Block);
			test.log(Status.PASS, "Block found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			editButton();
			test.log(Status.PASS, "Edit button clicked");

			test.log(Status.INFO, "Entering updated data in Block field");
			Thread.sleep(1000);
			enterDataBlock.clear();
			enterDataBlock.sendKeys(BlockUpdate);
			test.log(Status.PASS, "Updated data entered successfully with: " + BlockUpdate);

			test.log(Status.INFO, "Adding comments: " + Block + " updated");
			Comments(Block + " updated");
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
