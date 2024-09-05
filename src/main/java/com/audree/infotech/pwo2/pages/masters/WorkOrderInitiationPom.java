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

public class WorkOrderInitiationPom extends CommonData {
	private ExtentTest test;
	public Properties pro;

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

	@FindBy(xpath = "(//input[@type='text'])[2]")
	private WebElement workRelatedToDropdown;

	@FindBy(xpath = "(//input[@type='text'])[3]")
	private WebElement roomId_EquipOrInstId;

	@FindBy(xpath = "(//input[@type='text'])[9]")
	private WebElement natureOfWorkDropdown;

	@FindBy(xpath = "(//input[@type='text'])[9]")
	private WebElement descriptionOfWorkSk;

	public void initiation() {
		InitiateTabClick.click();
	}

	public void workRelatedToDropdownAction(String WorkRelatedTo) {
		workRelatedToDropdown.click();
		workRelatedToDropdown.sendKeys(WorkRelatedTo, Keys.ENTER);
	}

	// Combined method to handle both Room and Equipment/Instrument dropdowns
	public void roomOrEquipDropdownAction(String id) {
		roomId_EquipOrInstId.click();
		roomId_EquipOrInstId.sendKeys(id, Keys.ENTER);
	}

	public void natureOfWork(String natureOfWork) {
		natureOfWorkDropdown.click();
		natureOfWorkDropdown.sendKeys(natureOfWork, Keys.ENTER);
	}

	public void descriptionOfWork(String descriptionOfWork) {
		descriptionOfWorkSk.sendKeys(descriptionOfWork);
	}
	public void saveAction() throws Exception {
		saveEsigantureActions();
	}
	public void submitAction() throws Exception {
		submitEsigantureActions();
	}

}
