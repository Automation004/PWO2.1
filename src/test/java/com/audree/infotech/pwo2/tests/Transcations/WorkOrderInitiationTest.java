package com.audree.infotech.pwo2.tests.Transcations;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.pages.masters.WorkOrderInitiationPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class WorkOrderInitiationTest extends BaseTest {
	public WorkOrderInitiationPom workOrderInitiationPom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		// Pre-Load all required data from Excel
		workOrderInitiationPom = new WorkOrderInitiationPom(driver, test, pro);
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {
			excelData.put("WorkRelatedTo", xls.getCellData("Initiator", "WorkRelatedTo", i));
			excelData.put("NatureOfWork", xls.getCellData("Initiator", "NatureOfWork", i));
			excelData.put("DescriptionOfWork", xls.getCellData("Initiator", "DescriptionOfWork", i));

			// Already Given In Masters
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));
		}
	}

	@Test(priority = 1)
	public void createWorkOrder() throws Exception {
		try {
			Login(pro.getProperty("EnReviewer"), pro.getProperty("Password"));
			workOrderInitiationPom.initiation();
			workOrderInitiationPom.workRelatedToDropdownAction(excelData.get("WorkRelatedTo"));
			Thread.sleep(1000);
			if (excelData.get("WorkRelatedTo").equalsIgnoreCase("Facility")) {
				workOrderInitiationPom.roomOrEquipDropdownAction(excelData.get("RoomIdUpdate"));
			} else if (excelData.get("WorkRelatedTo").equalsIgnoreCase("Equipment/Instrument")) {
				workOrderInitiationPom.roomOrEquipDropdownAction(excelData.get("EquipOrInstIdUpdate"));
				System.out.println(excelData.get("EquipOrInstIdUpdate"));
			} else {
				System.out.print("Work Related To not done");
			}
			workOrderInitiationPom.natureOfWork(excelData.get("NatureOfWork"));
			workOrderInitiationPom.descriptionOfWork(excelData.get("DescriptionOfWork"));

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test(priority = 3)
	public void submittingRecord() throws Exception {
		try {
			createWorkOrder();
			workOrderInitiationPom.submitAction();
			System.out.println("Record Submited Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

	@Test(priority = 2)
	public void savingRecord() throws Exception {
		try {
			workOrderInitiationPom.saveAction();
			System.out.println("Record Saved Successfully");
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}
}
