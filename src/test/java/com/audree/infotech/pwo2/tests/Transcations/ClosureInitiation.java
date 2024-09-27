package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.ClosureInitiationPom;
import com.audree.infotech.pwo2.pages.masters.WorkOrderInitiationPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class ClosureInitiation extends BaseTest {
	public ClosureInitiationPom closureInitiationPom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		// Pre-Load all required data from Excel
		closureInitiationPom = new ClosureInitiationPom(driver, test, pro);
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
		}
	}

	public void closureInitiation() throws Exception {
		// Click "Closure Initiation"
		closureInitiationPom.closureInitiationActions();

		// Search for "equipment Id"
		closureInitiationPom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));

		// Click on Word Order Id link
		closureInitiationPom.selectWorkOrder();

	}

	@Test
	public void savingRecord() throws Exception {
		try {
			System.out.println("Closure Initiation Started");
			closureInitiation(); // Perform closure initiation steps
			System.out.println("Closure Initiation completed");
			String conclusion = excelData.get("conclusion"); // Fetch data from excelData map
			closureInitiationPom.enterComments(conclusion); // Enter comments
			closureInitiationPom.saveAction(); // Save the record
			System.out.println("Record Saved Successfully");
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			closureInitiation();
			closureInitiationPom.submitAction();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

}
