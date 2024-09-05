package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.EquipOrInstName_pom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class EquipOrInstName extends BaseTest {
	public EquipOrInstName_pom equipNamePOM;
	Map<String, String> excelData = new HashMap<>();

	@BeforeMethod()
	public void setUp() throws Exception {
		// Pre-Load all required data from Excel
		equipNamePOM = new EquipOrInstName_pom(driver, test, pro);
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		for (int i = startRow; i <= endRow; i++) {
			excelData.put("EquipOrInstName", xls.getCellData("MasterData", "EquipOrInstName", i));
			excelData.put("EquipOrInstNameUpdate", xls.getCellData("MasterData", "EquipOrInstNameUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));
			excelData.put("EN Reviewer", xls.getCellData("Credentials", "EN Reviewer", i));
		}
	}

	@Test
	public void Create() throws Exception {
		try {
			Login(pro.getProperty("EnReviewer"), pro.getProperty("Password"));
			equipNamePOM.create(excelData.get("EquipOrInstName"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	public void Update() throws Exception {
		try {
			equipNamePOM.update(excelData.get("EquipOrInstName"), excelData.get("EquipOrInstNameUpdate"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}