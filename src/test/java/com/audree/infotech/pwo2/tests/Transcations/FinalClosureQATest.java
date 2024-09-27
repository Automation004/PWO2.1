package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.CategorizationOfWorkPom;
import com.audree.infotech.pwo2.pages.masters.FinalClosurePom;
import com.audree.infotech.pwo2.pages.masters.ImpactAssessmentPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class FinalClosureQATest extends BaseTest {
	public FinalClosurePom finalClosurePom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		// Pre-Load all required data from Excel
		finalClosurePom = new FinalClosurePom(driver, test, pro);
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {

			// changable Feilds
			
			
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
		try {
			finalClosurePom.finalClosureTabClickActions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread.sleep(1000);
		// Search for "equipment Id"
		finalClosurePom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));
		// Click on Word Order Id link
		finalClosurePom.selectWorkOrder();
	}

	public void savingRecord() throws Exception {
		try {
			finalClosurePom.interimReleaseCheckBox();
			finalClosurePom.saveAction();
			System.out.println("Record Saved Successfully");
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			finalClosuerAction();
			savingRecord();
			finalClosuerAction();
			Thread.sleep(1000);
			// Enter comments
			finalClosurePom.enterComments("SuccessFully Completed the Process of Final Closure");
			scrollPagedown();
			finalClosurePom.submitAction();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

}
