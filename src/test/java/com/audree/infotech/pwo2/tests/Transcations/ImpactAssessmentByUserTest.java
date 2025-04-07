package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.ImpactAssessmentPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;

public class ImpactAssessmentByUserTest extends BaseTest {
	public ImpactAssessmentPom impactAssessmentPom;
	Map<String, String> excelData = new HashMap<>();
	public Xls_Reader xls;


	@BeforeClass
	public void setUp() throws Exception {
		xls = new Xls_Reader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");

		// Pre-Load all required data from Excel
		impactAssessmentPom = new ImpactAssessmentPom(driver, test, pro);
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {

			// Already Given In Masters
			excelData.put("conclusion", xls.getCellData("WorkUnderClosure", "conclusion", i));
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
			Thread.sleep(1000);

			// Click "impact Assessment" Tab
			impactAssessmentPom.impactAssessmentActions();

		}
	}

	public void impactAssessmentInitiation() throws Exception {

		// Search for "equipment Id"
		// impactAssessmentPom.searchWorkOrder(pro.getProperty("workOrderId"));
		impactAssessmentPom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));

		// Click on Word Order Id link
		impactAssessmentPom.selectWorkOrder();
	}

	@Test
	public void savingRecord() throws Exception {
		try {
			Thread.sleep(1000);
			impactAssessmentInitiation();
			Thread.sleep(1000);
			impactAssessmentPom.radioButtonActions(); // Change according to our need
			impactAssessmentPom.saveAction(); // Save the record
			System.out.println("Record Saved Successfully");
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			Thread.sleep(1000);
			savingRecord();
			impactAssessmentInitiation();
			scrollPagedown();
			// Enter comments
			impactAssessmentPom.enterComments("Impact Assessment Sucessfully Initiated");
			impactAssessmentPom.submitButton();
			impactAssessmentPom.yesButton();
			impactAssessmentPom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
			impactAssessmentPom.clickSubmitButton2();
			impactAssessmentPom.okButton();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

}
