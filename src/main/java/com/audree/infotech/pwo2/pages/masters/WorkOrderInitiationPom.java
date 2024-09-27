package com.audree.infotech.pwo2.pages.masters;

import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.audree.infotech.pwo2.utils.CommonData;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class WorkOrderInitiationPom extends CommonData {
	private ExtentTest test;
	public Properties pro;
	public String workOrderIdText;

	// Constructor
	public WorkOrderInitiationPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='mb-3']//a[1]")
	private WebElement InitiateTabClick;

	@FindBy(xpath = "//div[contains(text(),'Initiated')]")
	private WebElement InitiatedtabClick;

	@FindBy(xpath = "//div[contains(text(),'InProgress')]")
	private WebElement InProgresstabClick;

	@FindBy(xpath = "(//*[contains(text(),'Re-Initiate')])[2]")
	private WebElement reInitiateButton;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement workRelatedToDropdown;

	@FindBy(xpath = "(//input[@type='text'])[3]")
	private WebElement roomId_EquipOrInstId;

	@FindBy(xpath = "(//input[@type='text'])[9]")
	private WebElement natureOfWorkDropdown;

	@FindBy(xpath = "//textarea[@formcontrolname='description']")
	private WebElement descriptionOfWorkSk;

	@FindBy(xpath = "(//a[@class='work-order-link'])[1]")
	private WebElement workOrderIdClick;

	@FindBy(xpath = "(//*[@class='fa fa-eye'])[1]")
	private WebElement eyeButton;

	@FindBy(xpath = "//tr[1]/td[3]")
	private WebElement departmentNameText;

	@FindBy(xpath = "//tr[1]/td[4]")
	private WebElement equipInstNameText;

	@FindBy(xpath = "//tr[1]/td[5]")
	private WebElement equipInstIdText;

	@FindBy(xpath = "//tr[1]/td[6]")
	private WebElement roomNameText;

	@FindBy(xpath = "//tr[1]/td[7]")
	private WebElement roomIdText;

	@FindBy(xpath = "//tr[1]/td[8]")	
	private WebElement descriptionOfWorkText;

	@FindBy(xpath = "//tr[1]/td[9]")
	private WebElement initiatedByText;

	@FindBy(xpath = "//tr[1]/td[10]")
	private WebElement statusText;

	@FindBy(xpath = "//body//app-root//a[4]")
	private WebElement returnTab;

	public void initiation() {
		test.log(Status.INFO, "Clicking on the 'Initiate' tab.");
		InitiateTabClick.click();
		test.log(Status.PASS, "'Initiate' tab clicked successfully.");
	}

	public void reinitiationWithActions(String equipmentOrInstId) throws Exception {
		test.log(Status.INFO, "Clicking on the 'Return' tab.");
		returnTab.click();
		test.log(Status.PASS, "'Return' tab clicked successfully.");
		SearchBox(equipmentOrInstId);
		test.log(Status.PASS, "Searched the material with id:" + equipmentOrInstId);
		workOrderIdClick.click();
		test.log(Status.PASS, "clicked on  " + equipmentOrInstId + "link");
		Thread.sleep(1000);
		scrollPagedown();
		reInitiateButton.click();
		test.log(Status.PASS, "clicked on ReInitiate Button");
		yesButton();
		test.log(Status.PASS, "clicked on Yes Button");
		password.sendKeys(pro.getProperty("Password"));
		test.log(Status.PASS, "Given Valid credentials");
		submitAction();
		test.log(Status.PASS, "clicked on Submit Button");
		okButton();
		test.log(Status.PASS, "clicked on Ok Button");

	}

	public void inProgressTab() throws Exception {
		test.log(Status.INFO, "Clicking on the 'InProgress' tab.");
		InProgresstabClick.click();
		test.log(Status.PASS, "'InProgress' tab clicked successfully.");
	}

	public void initiatedTab(String equipIdOrRoomId) throws Exception {
		test.log(Status.INFO, "Clicking on the 'Initiated' tab.");
		InitiatedtabClick.click();
		test.log(Status.PASS, "'Initiated' tab clicked successfully.");
		SearchBox(equipIdOrRoomId);
		test.log(Status.PASS, "Searched " + equipIdOrRoomId);

		/*
		 * workOrderIdClick.click(); test.log(Status.PASS, "clicked on  " +
		 * equipIdOrRoomId + "link"); eyeButton.click(); test.log(Status.PASS,
		 * "clicked on preview button"); closeOpenedFile(); test.log(Status.PASS,
		 * "closed the opened file");
		 */
	}

	public void inProgress(String DescriptionUpdate) throws Exception {
		SearchBox("Under Initiation");
		test.log(Status.PASS, "Searched: Under Initiation status");
		editButton();
		test.log(Status.PASS, "Edit button clicked successfully.");
		Thread.sleep(1000);
		inProgressSumbitAction(DescriptionUpdate);
	}

	public void inProgressSumbitAction(String DescriptionUpdate) throws Exception {
		descriptionOfWorkSk.clear();
		descriptionOfWorkSk.sendKeys(DescriptionUpdate);
		test.log(Status.PASS, "sent updated data of Description Of Work successfully.");
		submitAction();
		eyeButton.click();
		test.log(Status.PASS, "clicked on preview button");
	}

	public void workRelatedToDropdownAction(String WorkRelatedTo) {
		try {
			test.log(Status.INFO, "Selecting '" + WorkRelatedTo + "' in 'Work Related To' dropdown.");
			workRelatedToDropdown.click();
			workRelatedToDropdown.sendKeys(WorkRelatedTo, Keys.ENTER);
			test.log(Status.PASS, "Selected '" + WorkRelatedTo + "' successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL,
					"Failed to select '" + WorkRelatedTo + "' in 'Work Related To' dropdown. Error: " + e.getMessage());
		}
	}

	// Combined method to handle both Room and Equipment/Instrument dropdowns
	public void roomOrEquipDropdownAction(String id) {
		try {
			test.log(Status.INFO, "Selecting Room/Equip/Inst ID: " + id);
			roomId_EquipOrInstId.click();
			roomId_EquipOrInstId.sendKeys(id, Keys.ENTER);
			test.log(Status.PASS, "Room/Equip/Inst ID '" + id + "' selected successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select Room/Equip/Inst ID: " + id + ". Error: " + e.getMessage());
		}
	}

	public void natureOfWork(String natureOfWork) {
		try {
			test.log(Status.INFO, "Selecting Nature of Work: " + natureOfWork);
			natureOfWorkDropdown.click();
			natureOfWorkDropdown.sendKeys(natureOfWork, Keys.ENTER);
			test.log(Status.PASS, "Nature of Work '" + natureOfWork + "' selected successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to select Nature of Work: " + natureOfWork + ". Error: " + e.getMessage());
		}
	}

	public void descriptionOfWork(String descriptionOfWork) {
		try {
			test.log(Status.INFO, "Entering description of work: " + descriptionOfWork);
			descriptionOfWorkSk.sendKeys(descriptionOfWork);
			test.log(Status.PASS, "Description of work entered successfully.");
		} catch (Exception e) {
			test.log(Status.FAIL, "Failed to enter description of work. Error: " + e.getMessage());
		}
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

	public void saveValidationCheck() throws Exception {
		try {
			saveButton();
			test.log(Status.PASS, "Save Button Clicked without given data");
			Ok_Button();
			test.log(Status.PASS, "Ok Button Clicked ");
		} catch (Exception e) {
			test.log(Status.FAIL,
					"Failed to get expected validation message save before giving data. Error: " + e.getMessage());
			throw e;
		}
	}

	public void validateWorkOrderInTable(String expectedDepartment, String expectedEquipInstName,
			String expectedEquipInstId, String expectedRoomName, String expectedRoomId,
			String expectedDescriptionOfWork, String expectedInitiatedBy) {
		SoftAssert softAssert = new SoftAssert(); // Create a SoftAssert instance

		try {
			// Fetch and log actual values
			String departmentName = departmentNameText.getText();
			String equipInstName = equipInstNameText.getText();
			String equipInstId = equipInstIdText.getText();
			String roomName = roomNameText.getText();
			String roomId = roomIdText.getText();
			String descriptionOfWork = descriptionOfWorkText.getText();
			String initiatedBy = initiatedByText.getText();
			String status = statusText.getText();

			// Log actual values
			test.log(Status.INFO, "Fetched values from table: ");
			test.log(Status.INFO, "Department Name: " + departmentName);
			test.log(Status.INFO, "Equip/Inst Name: " + equipInstName);
			test.log(Status.INFO, "Equip/Inst ID: " + equipInstId);
			test.log(Status.INFO, "Room Name: " + roomName);
			test.log(Status.INFO, "Room ID: " + roomId);
			test.log(Status.INFO, "Description of Work: " + descriptionOfWork);
			test.log(Status.INFO, "Initiated By: " + initiatedBy);
			test.log(Status.INFO, "Status: " + status);

			// Perform soft assertions
			softAssert.assertEquals(departmentName, expectedDepartment, "Department Name mismatch");
			softAssert.assertEquals(equipInstName, expectedEquipInstName, "Equip/Inst Name mismatch");
			softAssert.assertEquals(equipInstId, expectedEquipInstId, "Equip/Inst ID mismatch");
			softAssert.assertEquals(roomName, expectedRoomName, "Room Name mismatch");
			softAssert.assertEquals(roomId, expectedRoomId, "Room ID mismatch");
			softAssert.assertEquals(descriptionOfWork, expectedDescriptionOfWork, "Description of Work mismatch");
			softAssert.assertTrue(initiatedBy.startsWith(expectedInitiatedBy),
					"Initiated By mismatch: Expected it to start with " + expectedInitiatedBy);

			// Log success if all validations pass
			test.log(Status.PASS, "All data validations passed successfully.");

		} catch (Exception e) {
			test.log(Status.FAIL, "Error: " + e.getMessage());
			throw e; // Ensure the exception is propagated
		} finally {
			// Ensure softAssert.assertAll() is always called
			softAssert.assertAll(); // This will throw AssertionError if any assertions fail
		}
	}

}
