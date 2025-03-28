package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.WorkUnderClosurePom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class WorkUnderClosure extends BaseTest {

	public WorkUnderClosurePom workUnderClosurePom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		// PreLoad all required data from Excel
		workUnderClosurePom = new WorkUnderClosurePom(driver, test, pro);

		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {
			excelData.put("reasonForFailure", xls.getCellData("WorkUnderClosure", "reasonForFailure", i));
			excelData.put("quantity", xls.getCellData("WorkUnderClosure", "quantity", i));
			excelData.put("conclusion", xls.getCellData("WorkUnderClosure", "conclusion", i));

			// Already Given In Masters
			excelData.put("failureObjectUpdate", xls.getCellData("MasterData", "FailureObjectUpdate", i));
			excelData.put("MaterialCodeUpdate", xls.getCellData("MasterData", "MaterialCodeUpdate", i));
			excelData.put("MaterialNameUpdate", xls.getCellData("MasterData", "MaterialNameUpdate", i));

			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("WorkdoneEN"), pro.getProperty("Password"));
			Thread.sleep(1000);
			workUnderClosurePom.workUnderClosure();
		}
	}

	public void WorkUnderClosureSelect() throws Exception {

		Thread.sleep(500);
		// Search for the work order id link
		workUnderClosurePom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));
		// Select the work order from the search results
		workUnderClosurePom.selectWorkOrder();
	}

	public void workUnderClosureDetails() throws Exception {
		// Enter activity and Detail of work and save
		System.out.println("workkUnderClosureDetails Invoked");
		Thread.sleep(2000);
		workUnderClosurePom.failureObject(excelData.get("failureObjectUpdate"));
		workUnderClosurePom.enterReasonForFailure(excelData.get("reasonForFailure"));
		workUnderClosurePom.selectMaterialCode(excelData.get("MaterialCodeUpdate"));
		workUnderClosurePom.selectMaterialName(excelData.get("MaterialNameUpdate"));
		workUnderClosurePom.enterUom("100");
		workUnderClosurePom.enterQuantity(excelData.get("quantity"));
		workUnderClosurePom.addButton();
		scrollPagedown();
	}

	@Test
	public void savingRecord() throws Exception {
		try {
			WorkUnderClosureSelect();
			workUnderClosureDetails();// values giving before save--it will give submit also
			workUnderClosurePom.saveButton();
			workUnderClosurePom.yesButton();
			workUnderClosurePom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
			workUnderClosurePom.clickSubmitButton2();
			workUnderClosurePom.okButton();
			System.out.println("Record Saved Successfully");
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			savingRecord();
			WorkUnderClosureSelect();
			scrollPagedown();
			scrollPagedownWithActions(workUnderClosurePom.Submit);
			workUnderClosurePom.enterConclusion(excelData.get("conclusion"));
			workUnderClosurePom.submitButton01();
			workUnderClosurePom.yesButton();
			workUnderClosurePom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
			workUnderClosurePom.clickSubmitButton2();
			workUnderClosurePom.okButton();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("InitiatedTab method failed :" + e);
		}
	}
}