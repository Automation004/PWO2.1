package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.EnInitialReviewerPom;
import com.audree.infotech.pwo2.pages.masters.InitiatialReviewByQaPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class InitialReviewByQA extends BaseTest {
	public InitiatialReviewByQaPom initiatialReviewByQaPom;
	Map<String, String> excelData = new HashMap<>();
	

	@BeforeClass
	public void setUp() throws Exception {
		// Pre-Load all required data from Excel
		initiatialReviewByQaPom = new InitiatialReviewByQaPom(driver, test, pro);

		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {
			// need to update
			excelData.put("ReturnTo", xls.getCellData("Reviewer", "ReturnTo", i));

			// Already Given In Masters
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("QAReview"), pro.getProperty("Password"));
			Thread.sleep(1000);

		}
	}

	@Test
	public void initialReviewWorkFlow() throws Exception {
		// Actions of initial Review WorkFlow
		initiatialReviewByQaPom.initialReview(excelData.get("EquipOrInstIdUpdate"));
		
	}

	@Test
	public void savingRecord() throws Exception {
		try {
			initiatialReviewByQaPom.saveAction();
			System.out.println("Record Saved Successfully");
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			initiatialReviewByQaPom.submitAction();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

	@Test
	public void ReturnRecord() throws Exception {
		try {
			initiatialReviewByQaPom.returnAction(excelData.get("returnTo"));
			System.out.println("Record Returned Successfully");
		} catch (Exception e) {
			System.out.println("Record Returned  failed" + e);
		}
	}

}
