package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.MaterialCodePom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class MaterialCodeTest extends BaseTest {
	public MaterialCodePom materialCodePom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass()
	public void setUp() throws Exception {
		materialCodePom = new MaterialCodePom(driver, test, pro);
		// Pre-Load all required data from Excel
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		for (int i = startRow; i <= endRow; i++) {			excelData.put("MaterialCode", xls.getCellData("MasterData", "MaterialCode", i));
			excelData.put("MaterialCodeUpdate", xls.getCellData("MasterData", "MaterialCodeUpdate", i));
			excelData.put("MaterialNameUpdate", xls.getCellData("MasterData", "MaterialNameUpdate", i));
			
			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));
			excelData.put("EN Reviewer", xls.getCellData("Credentials", "EN Reviewer", i));
		}
	}

	@Test(priority = 1)
	public void Create() throws Exception {
		try {
			Login(pro.getProperty("EnReviewer"), pro.getProperty("Password"));
			materialCodePom.create(excelData.get("MaterialName"), excelData.get("MaterialCode"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test(priority = 2)
	public void Update() throws Exception {
		try {
			materialCodePom.update(excelData.get("MaterialNameUpdate"),excelData.get("MaterialCode"), excelData.get("MaterialCodeUpdate"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
