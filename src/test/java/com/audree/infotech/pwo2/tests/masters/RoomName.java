package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.RoomNamePom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class RoomName extends BaseTest {
	public RoomNamePom roomNamePom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeMethod()
	public void setUp() throws Exception {
		roomNamePom = new RoomNamePom(driver, test, pro);
		// Pre-Load all required data from Excel
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		
		for (int i = startRow; i <= endRow; i++) {
			excelData.put("RoomName", xls.getCellData("MasterData", "RoomName", i));
			excelData.put("RoomNameUpdate", xls.getCellData("MasterData", "RoomNameUpdate", i));
			
			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));
			excelData.put("EN Reviewer", xls.getCellData("Credentials", "EN Reviewer", i));

		}
	}

	@Test
	public void Create() throws Exception {
		try {
			Login(pro.getProperty("EnReviewer"), pro.getProperty("Password"));
			roomNamePom.create(excelData.get("RoomName"));
		} catch (Exception e) {
			e.printStackTrace(); // This will give you more details about the exception
		}
	}

	@Test
	public void Update() throws Exception {
		try {
			roomNamePom.update(excelData.get("RoomName"), excelData.get("RoomNameUpdate"));
		} catch (Exception e) {
			e.printStackTrace(); // This will give you more details about the exception
		}
	}
}
