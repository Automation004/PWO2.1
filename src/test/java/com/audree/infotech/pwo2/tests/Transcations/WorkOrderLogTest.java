package com.audree.infotech.pwo2.tests.Transcations;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.testcomponents.BaseTest;

public final class WorkOrderLogTest extends BaseTest {

	private WorkOrderLogPom workOrderLogPom;

	@BeforeClass
	public void setup() throws Exception {
		workOrderLogPom = new WorkOrderLogPom(driver, test); // Pass WebDriver and ExtentTest instances
		Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
	}

	@Test
	public void testWorkOrderLog() throws Exception {
		workOrderLogPom.navigateToWorkOrderLog();
		workOrderLogPom.selectInComboBox("information Technology");
		workOrderLogPom.enterFromDate("2024-09-23");
		workOrderLogPom.enterToDate("2024-09-24");
		workOrderLogPom.selectEquipmentInstrument("Equipments/Instrument");
		workOrderLogPom.clickGetButton();
		workOrderLogPom.scrollDown();
		workOrderLogPom.scrollUp();
	}
}
