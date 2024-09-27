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

public class EquipOrInstIdPom extends CommonData {
	private ExtentTest test;
	public Properties pro;

	public EquipOrInstIdPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	Actions a = new Actions(driver);

	@FindBy(xpath = "//a[normalize-space()='Equip/Inst ID']")
	protected WebElement equipOrInstIdClick;

	@FindBy(xpath = "//select[@formcontrolname='equipmentId']")
	protected WebElement equipmentNameDropdown;

	@FindBy(xpath = "//input[@formcontrolname='number']")
	protected WebElement equipOrInstID;

	@FindBy(xpath = "//input[@formcontrolname='objectiveType']")
	protected WebElement objectiveType;

	@FindBy(xpath = "//select[@formcontrolname='blockId']")
	protected WebElement blockDropdown;

	@FindBy(xpath = "//select[@formcontrolname='roomId']")
	protected WebElement roomName;

	@FindBy(xpath = "//select[@formcontrolname='roomBlockId']")
	protected WebElement roomIdDropdown;

	@FindBy(xpath = "//button[normalize-space()='Create']")
	public WebElement createButtonClick;

	@FindBy(css = "div[class='form-group'] div")
	private WebElement validationMessage;

	@FindBy(xpath = "//input[@formcontrolname='name']")
	private WebElement enterDataEquipOrInstName;

	public void create(String EquipOrInstNameUpdate, String BlockUpdate, String RoomIdUpdate, String EquipOrInstID,
			String ObjectiveType, String RoomNameUpdate) throws Exception {
		try {
			// Click on the Masters section and wait for the dropdown to expand
			test.log(Status.INFO, "Navigating to Master section");
			a.moveToElement(masterClick).perform();
			test.log(Status.PASS, "Master section clicked successfully");
			
			test.log(Status.INFO, "Clicking on Equip/Inst Name");
			equipOrInstIdClick.click();
			test.log(Status.PASS, "Equip/Inst Name clicked successfully");

			test.log(Status.INFO, "Clicking on Create button");
			createButtonClick.click();
			Thread.sleep(1000);
			test.log(Status.PASS, "Create button clicked successfully");

			test.log(Status.INFO, "Adding the form without giving data");
			Add_Button();
			test.log(Status.PASS, "added button clicked without given data");

			// verifyValidationMessage(validationMessage,
			// pro.getProperty("Equip/Inst_ValidationMessage"));

			test.log(Status.INFO, "Entering data in Equip/Inst Name dropdown");
			equipmentNameDropdown.click();
			equipmentNameDropdown.sendKeys(EquipOrInstNameUpdate);
			test.log(Status.PASS, "Data entered in Equip/Inst Name field successfully with: " + EquipOrInstNameUpdate);

			test.log(Status.INFO, "entering data to Equip/InstID field");
			equipOrInstID.sendKeys(EquipOrInstID);
			test.log(Status.PASS, "entered data to Equip/InstID field");

			test.log(Status.INFO, "entering data to Objective Type field");
			objectiveType.sendKeys(ObjectiveType);
			test.log(Status.PASS, "entered data to Equip/InstID field");

			test.log(Status.INFO, "Entering data in Block Name dropdown");
			blockDropdown.click();
			blockDropdown.sendKeys(BlockUpdate);
			test.log(Status.PASS, "Data entered in Block Name field successfully with: " + BlockUpdate);

			test.log(Status.INFO, "Entering data in Room Name dropdown");
			roomName.click();
			roomName.sendKeys(RoomNameUpdate);
			test.log(Status.PASS, "Data entered in RoomId field successfully: " + RoomNameUpdate);

			test.log(Status.INFO, "Entering data in RoomId dropdown");
			roomIdDropdown.click();
			roomIdDropdown.sendKeys(RoomIdUpdate);
			test.log(Status.PASS, "Data entered in RoomId field successfully: " + RoomIdUpdate);

			test.log(Status.INFO, "Adding the form");
			Thread.sleep(500);
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

	public void update(String EquipOrInstNameUpdate, String EquipOrInstIdUpdate) throws Exception {
		try {
			test.log(Status.INFO, "Searching for Equip/Inst Name: " + EquipOrInstNameUpdate);
			SearchBox(EquipOrInstNameUpdate);
			test.log(Status.PASS, "Equip/Inst Name found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			editButton();
			test.log(Status.PASS, "Edit button clicked");

			test.log(Status.INFO, "Updating data to Equip/InstID field");
			Thread.sleep(1000);
			equipOrInstID.clear();
			equipOrInstID.sendKeys(EquipOrInstIdUpdate);
			test.log(Status.PASS, "Updated data to Equip/InstID field");

			test.log(Status.INFO, "Adding comments: " + EquipOrInstIdUpdate + " updated");
			Comments(EquipOrInstIdUpdate + " updated");
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
