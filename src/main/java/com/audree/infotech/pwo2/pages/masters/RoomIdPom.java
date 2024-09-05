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

public class RoomIdPom extends CommonData {
	private ExtentTest test;
	public Properties pro;

	// Constructor
	public RoomIdPom(WebDriver driver, ExtentTest _test, Properties _pro) {
		super(driver, _test, _pro);
		new Actions(driver);
		this.test = _test;
		this.pro = _pro;
		PageFactory.initElements(driver, this);
	}

	Actions a = new Actions(driver);

	@FindBy(xpath = "//button[normalize-space()='Create']")
	private WebElement createButtonClick;

	@FindBy(xpath = "//a[normalize-space()='Room ID']")
	protected WebElement RoomIdClick;

	@FindBy(xpath = "//input[@formcontrolname='name']")
	protected WebElement enterDataRoomName;

	@FindBy(xpath = "//select[@formcontrolname='roomName']")
	protected WebElement roomNameDropdown;

	@FindBy(xpath = "//input[@formcontrolname='number']")
	protected WebElement roomId;

	@FindBy(xpath = "//select[@formcontrolname='block']")
	protected WebElement blockDropdown;

	@FindBy(xpath = "//input[@formcontrolname='location']")
	protected WebElement location;

	@FindBy(css = "body app-root div[class='modal-body'] div div:nth-child(2)")
	private WebElement validationMessage;

	public void Create(String RoomNameUpdate, String RoomId, String BlockDropdown, String Location) throws Exception {
		try {
			// Click on the Masters section and wait for the dropdown to expand
			test.log(Status.INFO, "Navigating to Master section");
			a.moveToElement(masterClick).perform();
			test.log(Status.PASS, "Master section clicked successfully");

			test.log(Status.INFO, "Clicking on Room Name");
			RoomIdClick.click();
			test.log(Status.PASS, "Room Name clicked successfully");

			test.log(Status.INFO, "Clicking on Create button");
			createButtonClick.click();
			test.log(Status.PASS, "Create button clicked successfully");

			test.log(Status.INFO, "Adding the form");
			Add_Button();
			test.log(Status.PASS, "added button clicked");

			test.log(Status.INFO, "Clicking & Giving data on Room Name Dropdown");
			roomNameDropdown.click();
			roomNameDropdown.sendKeys(RoomNameUpdate, Keys.ENTER);
			test.log(Status.INFO, "Clicked & Given data to Room Name Dropdown: " + RoomNameUpdate);

			test.log(Status.INFO, "Giving data to Room Id");
			roomId.sendKeys(RoomId);
			test.log(Status.INFO, "Given data on Room Id");

			test.log(Status.INFO, "Clicking & Giving data to Block Dropdown");
			blockDropdown.click();
			blockDropdown.sendKeys(BlockDropdown, Keys.ENTER);
			test.log(Status.INFO, "Clicked & Given data to Block Dropdown: " + BlockDropdown);

			test.log(Status.INFO, "Giving data on location");
			location.sendKeys(Location);
			test.log(Status.INFO, "Given data to location");

			test.log(Status.INFO, "Again Adding the form");
			Add_Button();
			test.log(Status.PASS, "Again added button clicked");

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

	public void Update(String RoomNameUpdate, String RoomIdUpdate, String LocationUpdate) throws Exception {
		try {
			test.log(Status.INFO, "Searching for Room Name: " + RoomNameUpdate);
			SearchBox(RoomNameUpdate);
			test.log(Status.PASS, "Room Name found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			editButton();
			test.log(Status.PASS, "Edit button clicked");

			Thread.sleep(1000);

			test.log(Status.INFO, "Clearing existing data in Room Name field");
			roomId.clear();
			test.log(Status.PASS, "Existing data cleared");

			test.log(Status.INFO, "Entering updated data in Room Id field");
			roomId.sendKeys(RoomIdUpdate);
			test.log(Status.PASS, "Updated data entered successfully with: " + RoomIdUpdate);

			test.log(Status.INFO, "updating data to location");
			location.clear();
			location.sendKeys(LocationUpdate);
			test.log(Status.INFO, "update data to location: " + LocationUpdate);

			test.log(Status.INFO, "Adding comments: " + RoomIdUpdate + " updated");
			Comments(RoomIdUpdate + " updated");
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
