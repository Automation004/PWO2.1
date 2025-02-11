package com.audree.infotech.pwo2.tests.masters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import com.audree.infotech.pwo2.pages.masters.EquipOrInstNamePom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class EquipOrInstName extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(EquipOrInstName.class);
//	public EquipOrInstNamePom equipNamePOM;
	// Read the starting and ending rows from the properties file
	public String equipOrInstName;
	public String equipOrInstNameUpdate;

	@BeforeTest
	public void setUp() throws Exception {
		// Ensure that 'pro' is initialized before accessing its properties
//		equipNamePOM = new EquipOrInstNamePom(driver, test, pro);
		// Initialize startRow and endRow after pro has been properly initialized
	}

	@Test()
	public void Create() throws Exception {
		try {
			logger.info("Starting Create test.");
			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
			logger.info("Login successful.");

			for (int i = Integer.parseInt(pro.getProperty("startRow")); i <= Integer
					.parseInt(pro.getProperty("endRow")); i++) {

				// Fetch data for each row
				equipOrInstName = xls.getCellData("MasterData", "EquipOrInstName", i);
				equipOrInstNameUpdate = xls.getCellData("MasterData", "EquipOrInstNameUpdate", i);

				logger.info("Creating equipment with name: {}", equipOrInstName);
//				equipNamePOM.create(equipOrInstName);
			}
			logger.info("Create test completed successfully.");
		} catch (Exception e) {
			logger.error("Error occurred during Create test: ", e);
			throw e;
		}
	}

	@Test()
	public void Update() throws Exception {
		try {
			logger.info("Starting Update test.");

			for (int i = Integer.parseInt(pro.getProperty("startRow")); i <= Integer
					.parseInt(pro.getProperty("endRow")); i++) {
				
				logger.info("Updating equipment from: {} to {}", equipOrInstName, equipOrInstNameUpdate);
//				equipNamePOM.update(equipOrInstName, equipOrInstNameUpdate);
			}
			logger.info("Update test completed successfully.");
		} catch (Exception e) {
			logger.error("Error occurred during Update test: ", e);
			throw e;
		}
	}
}
