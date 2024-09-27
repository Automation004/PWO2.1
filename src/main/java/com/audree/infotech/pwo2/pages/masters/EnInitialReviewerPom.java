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

public class EnInitialReviewerPom extends CommonData {
	private ExtentTest test;
	public Properties pro;

	public EnInitialReviewerPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	// Locators for the elements
	@FindBy(xpath = "//div[text()='EN Initial Receiver']")
	WebElement enInitialReceiverTab;

	@FindBy(xpath = "//div[@class='dashBoardMain mb-3']//a[2]")
	WebElement QAReturnedTab;

	@FindBy(xpath = "//input[@placeholder='Search...']")
	WebElement searchBox;

	@FindBy(css = "body > app-root:nth-child(1) > div:nth-child(1) > app-layout:nth-child(1) > div:nth-child(2) > app-child-dashboard:nth-child(2) > section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > app-audree-grid:nth-child(1) > div:nth-child(1) > table:nth-child(3) > tbody:nth-child(3) > tr:nth-child(1) > td:nth-child(2) > a:nth-child(1)")
	WebElement workOrderIdLink;

	@FindBy(xpath = "(//input[@type='text'])[1]")
	WebElement selecttypeOfWork;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	WebElement selectSection;

	@FindBy(xpath = "(//input[@type='radio'])[1]")
	WebElement radioButton1;

	@FindBy(xpath = "(//input[@type='radio'])[2]")
	WebElement radioButton2;
	// Actions for the test steps with logs

	public void clickENInitialReceiver() {
		test.log(Status.INFO, "Clicking on EN Initial Receiver tab.");
		enInitialReceiverTab.click();
		test.log(Status.PASS, "Clicked on EN Initial Receiver tab successfully.");
	}

	public void QAReturnedTabClick() {
		test.log(Status.INFO, "Clicking on QA Returned tab.");
		QAReturnedTab.click();
		test.log(Status.PASS, "Clicked on QA Returned tab.");
	}

	public void enterSearchText(String text) {
		test.log(Status.INFO, "Entering text into Search box: " + text);
		searchBox.sendKeys(text);
		test.log(Status.PASS, "Entered text into Search box successfully.");
	}

	public void clickWoOrderIdLink() {
		test.log(Status.INFO, "Clicking on Work Order Id link");
		System.out.println(workOrderIdLink.getText());
		workOrderIdLink.click();
		test.log(Status.PASS, "Clicked on Work Order Id link");
	}

	public void selectTypeOfWork(String typeOfWorkUpdate) throws Exception {
		Thread.sleep(1000);
		test.log(Status.INFO, "Selecting typeOfWorkUpdate data from the dropdown.");
		selecttypeOfWork.click();
		Thread.sleep(500);
		selecttypeOfWork.sendKeys(typeOfWorkUpdate, Keys.ENTER);
		test.log(Status.PASS, "Selected typeOfWorkUpdate data successfully: " + typeOfWorkUpdate);
	}

	public void selectSectionOption(String sectionUpdate) throws InterruptedException {
		test.log(Status.INFO, "Selecting 'section' data from the dropdown.");
		selectSection.click();
		Thread.sleep(500);
		selectSection.sendKeys(sectionUpdate, Keys.ENTER);
		test.log(Status.PASS, "Selected 'section' data successfully.");
	}

	public void radioButton(String workCategorization) {
		test.log(Status.INFO, "Selecting radio Button");
		if (workCategorization.equalsIgnoreCase("EN")) {
			radioButton1.click();
		} else {
			radioButton2.click();
		}
		test.log(Status.PASS, "Selected radio Button");
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

	public void returnAction() throws Exception {
		returnButton();
		returnButton();
		returnComments.sendKeys("Returned to the Work Order Initiator");
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
