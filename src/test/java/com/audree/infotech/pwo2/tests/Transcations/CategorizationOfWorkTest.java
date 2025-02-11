package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.CategorizationOfWorkPom;
import com.audree.infotech.pwo2.pages.masters.ImpactAssessmentPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class CategorizationOfWorkTest extends BaseTest {
	public CategorizationOfWorkPom categorizationOfWorkPom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		// Pre-Load all required data from Excel
		categorizationOfWorkPom = new CategorizationOfWorkPom(driver, test, pro);
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		for (int i = startRow; i <= endRow; i++) {
			
			// changable Feilds
			excelData.put("CategorizationOfWork", xls.getCellData("CategoryOfWork", "CategorizationOfWork", i));
			excelData.put("TypeOfQMSTool", xls.getCellData("CategoryOfWork", "TypeOfQMSTool", i));
			excelData.put("QMS ID", xls.getCellData("CategoryOfWork", "QMS ID", i));

			// Already Given In Masters
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("WorkdoneEN"), pro.getProperty("Password2"));
			Thread.sleep(1000);
		}
	}

	public void impactAssessmentInitiation() throws Exception {
		// Click "impact Assessment Pom"
		Thread.sleep(1000);
		try {
			categorizationOfWorkPom.categorizationOfWorkActions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Search for "equipment Id"
		categorizationOfWorkPom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));
		Thread.sleep(1000);
		// Click on Word Order Id link
		try {
			categorizationOfWorkPom.selectWorkOrder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savingRecord() throws Exception {
		try {
			Thread.sleep(1000);
			categorizationOfWorkPom.setCategorizationOfWorkDropdown(excelData.get("CategorizationOfWork"));
			categorizationOfWorkPom.setTypeOfQmsTool(excelData.get("TypeOfQMSTool"));
			categorizationOfWorkPom.setQmsId(excelData.get("QMS ID"));
			scrollPagedown();
			Thread.sleep(1000);
			categorizationOfWorkPom.saveAction(); // Save the record
			Thread.sleep(1000);
			scrollPagedown();
			System.out.println("Record Saved Successfully");
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			Thread.sleep(1000);
			impactAssessmentInitiation();
			savingRecord();
			impactAssessmentInitiation();
			Thread.sleep(1000);
			// Enter comments
			categorizationOfWorkPom.enterComments("SuccessFully Completed the Process of Categorizatio Of Work");
			scrollPagedown();
			categorizationOfWorkPom.submitAction();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

}
