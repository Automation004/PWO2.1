package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.FinalClosurePom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;

public class FinalClosureQATest extends BaseTest {
	public FinalClosurePom finalClosurePom;
	Map<String, String> excelData = new HashMap<>();
	public Xls_Reader xls;


	@BeforeClass
	public void setUp() throws Exception {
		xls = new Xls_Reader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");

		// Pre-Load all required data from Excel
		finalClosurePom = new FinalClosurePom(driver, test, pro);
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {

			// Changeable Fields
			
			
			// Already Given In Masters
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("Approver"), pro.getProperty("Password"));
			Thread.sleep(1000);
		}
	}

	public void finalClosuerAction() throws Exception {
		Thread.sleep(1000);
		// Search for "equipment Id"
		finalClosurePom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));
		// Click on Word Order Id link
		finalClosurePom.selectWorkOrder();
	}

	public void savingRecord() throws Exception {
		try {
			Thread.sleep(3000);
			finalClosurePom.interimReleaseCheckBox();
			finalClosurePom.saveButton();
			finalClosurePom.yesButton();
			finalClosurePom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
			finalClosurePom.clickSubmitButton2();
			finalClosurePom.okButton();
			System.out.println("Record Saved Successfully");
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			finalClosurePom.finalClosureTabClickActions();
			finalClosuerAction();
			savingRecord();
			finalClosuerAction();
			Thread.sleep(1000);
			// Enter comments
			finalClosurePom.enterComments("SuccessFully Completed the Process of Final Closure");
			scrollPagedown();
			finalClosurePom.submitButton();
			finalClosurePom.yesButton();
			finalClosurePom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
			finalClosurePom.clickSubmitButton2();
			finalClosurePom.okButton();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}
	@Test
	public void returningRecord() throws Exception {
		try {
			finalClosurePom.finalClosureTabClickActions();
			finalClosuerAction();
//			savingRecord();
//			finalClosuerAction();
			Thread.sleep(1000);
			// Enter comments
			finalClosurePom.enterComments("Returning the record from Final Closure");
			scrollPagedown();
			finalClosurePom.returnButton();
			Select select = new Select(finalClosurePom.returnTo);
			select.selectByVisibleText(pro.getProperty("returnTo"));
			finalClosurePom.returnButton();
			finalClosurePom.yesButton();
			finalClosurePom.submitButton();
			finalClosurePom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
			finalClosurePom.submitButton();
			finalClosurePom.okButton();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

}
