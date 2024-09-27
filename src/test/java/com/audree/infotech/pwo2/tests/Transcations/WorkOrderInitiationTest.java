package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.pages.masters.WorkOrderInitiationPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.aventstack.extentreports.Status;

public class WorkOrderInitiationTest extends BaseTest {
	public WorkOrderInitiationPom workOrderInitiationPom;
	Map<String, String> excelData = new HashMap<>();
	boolean isEquipment;

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
			excelData.put("DescriptionOfWorkUpdate", xls.getCellData("Initiator", "DescriptionOfWorkUpdate", i));

			// Already Given In Masters
			excelData.put("Department", xls.getCellData("MasterData", "Department", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));
			excelData.put("EquipOrInstNameUpdate", xls.getCellData("MasterData", "EquipOrInstNameUpdate", i));
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("RoomNameUpdate", xls.getCellData("MasterData", "RoomNameUpdate", i));
			excelData.put("InitiatedBy", xls.getCellData("MasterData", "InitiatedBy", i));
			excelData.put("Status", xls.getCellData("MasterData", "Status", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
			Thread.sleep(1000);
		}
	}

	@Test
	public void createWorkOrder() throws Exception {
		try {
			Thread.sleep(1000);
			workOrderInitiationPom.initiation();
			workOrderInitiationPom.workRelatedToDropdownAction(excelData.get("WorkRelatedTo"));
			Thread.sleep(1000);
			if (excelData.get("WorkRelatedTo").equalsIgnoreCase("Facility")) {
				workOrderInitiationPom.roomOrEquipDropdownAction(excelData.get("RoomIdUpdate"));
				isEquipment = false;// need to give roomId
			} else if (excelData.get("WorkRelatedTo").equalsIgnoreCase("Equipment/Instrument")) {
				workOrderInitiationPom.roomOrEquipDropdownAction(excelData.get("EquipOrInstIdUpdate"));
				System.out.println(excelData.get("EquipOrInstIdUpdate"));
				isEquipment = true;// need to give Equipment Id
			} else {
				System.out.print("Work Related To not done");
			}
			workOrderInitiationPom.natureOfWork(excelData.get("NatureOfWork"));
			workOrderInitiationPom.descriptionOfWork(excelData.get("DescriptionOfWork"));

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void submitRecord() throws Exception {
		try {
			workOrderInitiationPom.submitAction();
			System.out.println("Record Submitted Successfully");
			// After submitting the record, validate the Initiated tab
			initiatedTab();
		} catch (Exception e) {
			// Handling any other general exceptions
			System.out.println("Exception in submitRecord: " + e.getMessage());
			test.log(Status.FAIL, "Test execution failed due to error: " + e.getMessage());
			throw e; // Ensure the test is marked as failed
		}
	}

	@Test
	public void saveRecord() throws Exception {
		try {

			workOrderInitiationPom.saveAction();
			System.out.println("Record Saved Successfully");
			Thread.sleep(1000);
			inProgressTab();
		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void reInitiateRecord() throws Exception {
		try {
			workOrderInitiationPom.reinitiationWithActions(excelData.get("EquipOrInstIdUpdate"));
			
		} catch (Exception e) {
			System.out.println("InitiatedTab method failed :" + e);
		}
	}
	
	public void initiatedTab() throws Exception {
		try {
			if (isEquipment) {
				workOrderInitiationPom.initiatedTab(excelData.get("EquipOrInstIdUpdate"));

				workOrderInitiationPom.validateWorkOrderInTable(excelData.get("Department"),
						excelData.get("EquipOrInstNameUpdate"), excelData.get("EquipOrInstIdUpdate"),
						excelData.get("RoomNameUpdate"), excelData.get("RoomIdUpdate"),
						excelData.get("DescriptionOfWork"), excelData.get("InitiatedBy"));
			} else {
				workOrderInitiationPom.initiatedTab(excelData.get("RoomIdUpdate"));
			}
		} catch (AssertionError e) {
			System.out.println("Assertion validation failed in InitiatedTab Table: " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("InitiatedTab method failed: " + e.getMessage());
			test.log(Status.FAIL, "InitiatedTab method failed: " + e.getMessage());
			throw e; // Ensure the exception is propagated and test fails
		}
	}

	public void inProgressTab() throws Exception {
		try {
			workOrderInitiationPom.inProgressTab();
			workOrderInitiationPom.inProgress(excelData.get("DescriptionOfWorkUpdate"));
		} catch (Exception e) {
			System.out.println("InitiatedTab method failed :" + e);
		}
	}

}
