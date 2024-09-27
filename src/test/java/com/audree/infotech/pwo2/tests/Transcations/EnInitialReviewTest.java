package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.pages.masters.EnInitialReviewerPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class EnInitialReviewTest extends BaseTest {
	public EnInitialReviewerPom enInitialReviewer;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		// Pre-Load all required data from Excel
		enInitialReviewer = new EnInitialReviewerPom(driver, test, pro);
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {
			excelData.put("WorkCategorization", xls.getCellData("Initiator", "WorkCategorization", i));
			excelData.put("DescriptionOfWorkUpdate", xls.getCellData("Initiator", "DescriptionOfWorkUpdate", i));
			excelData.put("ReturnTo", xls.getCellData("Reviewer", "ReturnTo", i));

			// Already Given In Masters
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));
			excelData.put("TypeOfWorkUpdate", xls.getCellData("MasterData", "TypeOfWorkUpdate", i));
			excelData.put("SectionUpdate", xls.getCellData("MasterData", "SectionUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("EnReviewer"), pro.getProperty("Password"));
			Thread.sleep(1000);

		}
	}

	@Test
	public void EnInitialReviewWorkFlow() throws Exception {
		// Click "EN Initial Receiver"
		enInitialReviewer.clickENInitialReceiver();

		// Search for "equipment Id"
		enInitialReviewer.enterSearchText(excelData.get("EquipOrInstIdUpdate"));

		// Click on Word Order Id link
		enInitialReviewer.clickWoOrderIdLink();
		Thread.sleep(1000);

		// select TypeOfWork data from dropdown
		enInitialReviewer.selectTypeOfWork(excelData.get("TypeOfWorkUpdate"));

		// Select Section data from dropdown
		enInitialReviewer.selectSectionOption(excelData.get("SectionUpdate"));

		enInitialReviewer.radioButton(excelData.get("WorkCategorization"));
	}

	public void EnInitialReviewReturn() throws Exception {
		// Click "EN Initial Receiver"
		enInitialReviewer.QAReturnedTabClick();

		// Search for "equipment Id"
		enInitialReviewer.enterSearchText(excelData.get("EquipOrInstIdUpdate"));

		// Click on Word Order Id link
		enInitialReviewer.clickWoOrderIdLink();
		Thread.sleep(1000);
	}

	@Test
	public void savingRecord() throws Exception {
		try {
			enInitialReviewer.saveAction();
			System.out.println("Record Saved Successfully");
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			enInitialReviewer.submitAction();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

	@Test
	public void ReturnRecord() throws Exception {
		try {
			EnInitialReviewReturn();
			enInitialReviewer.returnAction();
			System.out.println("Record Returned Successfully");
		} catch (Exception e) {
			System.out.println("Record Returned  failed" + e);
		}
	}
}
