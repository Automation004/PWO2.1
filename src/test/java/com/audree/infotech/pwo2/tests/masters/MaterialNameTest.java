package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.MaterialNamePom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.aventstack.extentreports.Status;

public class MaterialNameTest extends BaseTest {
	private MaterialNamePom materialNamePom;
	private Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		materialNamePom = new MaterialNamePom(driver, test, pro);
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {
			excelData.put("MaterialName", xls.getCellData("MasterData", "MaterialName", i));
			excelData.put("MaterialNameUpdate", xls.getCellData("MasterData", "MaterialNameUpdate", i));
			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));
			excelData.put("EnReviewer", xls.getCellData("Credentials", "EN Reviewer", i));
		}
	}

	@Test
	public void Create() {
		try {
			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
			materialNamePom.create(excelData.get("MaterialName"));
			// Add assertion here to verify creation
		} catch (Exception e) {
			// Log the exception instead of printing
			test.log(Status.FAIL, "Create method failed: " + e.getMessage());
		}
	}

	@Test
	public void Update() {
		try {
			materialNamePom.update(excelData.get("MaterialName"), excelData.get("MaterialNameUpdate"));
			// Add assertion here to verify update
		} catch (Exception e) {
			// Log the exception instead of printing
			test.log(Status.FAIL, "Update method failed: " + e.getMessage());
		}
	}
}
