package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.SectionPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;
import com.aventstack.extentreports.Status;

public class Section extends BaseTest {

	public SectionPom sectionPom;
	private Map<String, String> excelData = new HashMap<>();
	public Xls_Reader xls;

	public void setUp() throws Exception {
		try {
			xls = new Xls_Reader(System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");

			sectionPom = new SectionPom(driver, test, pro);
			// Pre-Load all required data from Excel
			// Read the starting and ending rows from the properties file
			int startRow = Integer.parseInt(pro.getProperty("startRow"));
			int endRow = Integer.parseInt(pro.getProperty("endRow"));
			for (int i = startRow; i <= endRow; i++) {
				excelData.put("Section", xls.getCellData("MasterData", "Section", i));
				excelData.put("SectionUpdate", xls.getCellData("MasterData", "SectionUpdate", i));

				excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
				excelData.put("Password", xls.getCellData("Credentials", "Password", i));
				excelData.put("EN Reviewer", xls.getCellData("Credentials", "EN Reviewer", i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Initialization failed", e);
		}
	}

	@Test
	public void Create() throws Exception {
		try {
			setUp();
			
			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));

			test.log(Status.INFO, "Navigating to Master section");
			sectionPom.a.moveToElement(sectionPom.masterClick).perform();
			test.log(Status.PASS, "Master section clicked successfully");

			test.log(Status.INFO, "Clicking on Section");
			sectionPom.SectionClick.click();
			test.log(Status.PASS, "Section clicked successfully");
			Thread.sleep(1000);

			test.log(Status.INFO, "Clicking on Create button");
			sectionPom.createButtonClick.click();
			test.log(Status.PASS, "Create button clicked successfully");

			test.log(Status.INFO, "Submitting the form without giving data");
			sectionPom.submitButton();

			// sectionPom.verifyValidationMessage(pro.getProperty("Section_ValidationMessage"));

			test.log(Status.INFO, "Entering data in Section field");
			sectionPom.enterDataSection.sendKeys(excelData.get("Section"));
			test.log(Status.PASS, "Data entered in Section field successfully: " + excelData.get("Section"));

			test.log(Status.INFO, "Submitting the form");
			sectionPom.submitButton();

			sectionPom.noButton();

			sectionPom.submitButton();

			sectionPom.yesButton();

			sectionPom.Password_Fill(pro.getProperty("Password"));

			sectionPom.submitButton();

			sectionPom.okButton();
			Thread.sleep(300);
			Update();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

		public void Update() throws Exception {
		try {
			test.log(Status.INFO, "Searching for Section: " + excelData.get("Section"));
			sectionPom.SearchBox(excelData.get("Section"));
			test.log(Status.PASS, "Section found and selected");

			test.log(Status.INFO, "Clicking on Edit button");
			sectionPom.editButton();
			test.log(Status.PASS, "Edit button clicked");

			Thread.sleep(1000);

			test.log(Status.INFO, "Clearing existing data in Section field");
			sectionPom.enterDataSection.clear();
			test.log(Status.PASS, "Existing data cleared");

			test.log(Status.INFO, "Entering updated data in Section field");
			sectionPom.enterDataSection.sendKeys(excelData.get("SectionUpdate"));
			test.log(Status.PASS, "Updated data entered successfully with: " + excelData.get("SectionUpdate"));

			test.log(Status.INFO, "Adding comments: " + excelData.get("Section") + " updated");
			sectionPom.Comments(excelData.get("Section") + " updated");
			test.log(Status.PASS, "Comments added successfully");

			test.log(Status.INFO, "Clicking on Update button");
			sectionPom.UpdateButton();

			sectionPom.noButton();

			sectionPom.UpdateButton();

			sectionPom.yesButton();

			sectionPom.Password_Fill(pro.getProperty("Password"));

			sectionPom.submitButton();

			sectionPom.okButton();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}