package com.audree.infotech.pwo2.tests.Transcations;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.pages.masters.WorkUnderProgressPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class WorkUnderProgressTest extends BaseTest {
	public WorkUnderProgressPom workUnderProgressPom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		// Pre-Load all required data from Excel
		workUnderProgressPom = new WorkUnderProgressPom(driver, test, pro);

		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {
			excelData.put("activity", xls.getCellData("WorkUnderProgress", "Activity", i));
			excelData.put("detailOfTrail", xls.getCellData("WorkUnderProgress", "DetailOfTrail", i));

			// Already Given In Masters
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("WorkdoneEN"), pro.getProperty("Password"));
			Thread.sleep(1000);
		}
	}

	@Test
	public void WorkUnderProgressSelect() throws Exception {

		workUnderProgressPom.workUnderProgress();

		// Search for the work order
		workUnderProgressPom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));

		// Select the work order from the search results
		workUnderProgressPom.selectWorkOrder();
	}

	@Test
	public void WorkUnderProgressActivitySave() throws Exception {
		// Enter activity and Detail of work and save
		workUnderProgressPom.enterActivityAndSave(excelData.get("activity"));
	}

	@Test
	public void WorkUnderProgressActivitySubmit() throws Exception {
		// Enter activity and Detail of work and save
		workUnderProgressPom.enterActivityAndSubmit(excelData.get("activity"));
	}

	@Test
	public void WorkUnderProgressSave() throws Exception {
		// Final save
		workUnderProgressPom.saveAction(excelData.get("detailOfTrail"));
	}

	@Test
	public	void WorkUnderProgressSubmit() throws Exception {
		// Final submit
		workUnderProgressPom.submitAction();
	}
}
