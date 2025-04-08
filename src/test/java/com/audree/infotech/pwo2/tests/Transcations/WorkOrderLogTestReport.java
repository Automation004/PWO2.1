package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;
import com.audree.infotech.pwo2.tests.masters.WorkOrderLogPom;

public class WorkOrderLogTestReport extends BaseTest {

	private WorkOrderLogPom workOrderLogPom;
	Map<String, String> excelData = new HashMap<>();
	private String fromDate;
	private String toDate;
	public Xls_Reader xls;


	@BeforeClass
	public void setup() throws Exception {
		xls = new Xls_Reader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");
		// Initialize the WorkOrderLogPom Page Object with driver and test instances
		workOrderLogPom = new WorkOrderLogPom(driver, test);

		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		for (int i = startRow; i <= endRow; i++) {
			excelData.put("Department", xls.getCellData("MasterData", "Department", i));
			excelData.put("WorkRelatedTo", xls.getCellData("Initiator", "WorkRelatedTo", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));
		}
		fromDate = pro.getProperty("fromDate");
		toDate = pro.getProperty("toDate");
		
		Login(pro.getProperty("Initiator"), pro.getProperty("Password"));

	}

	@Test
	public void testWorkOrderLog() throws Throwable {
		// Navigating to Work Order Log page
		workOrderLogPom.navigateToWorkOrderLog();

		// Performing actions using the values read from properties
		workOrderLogPom.selectInComboBox(excelData.get("Department"));
		workOrderLogPom.enterFromDate(fromDate);Thread.sleep(500);
		workOrderLogPom.enterToDate(toDate);
		workOrderLogPom.selectWorkRelatedTo(excelData.get("WorkRelatedTo"));

		// Submit the form
		workOrderLogPom.clickGetButton();

		workOrderLogPom.SearchBox(excelData.get("EquipOrInstIdUpdate"));

		// Scroll for visibility (optional)
		workOrderLogPom.scrollDown();
		workOrderLogPom.scrollUp();
	}
}
