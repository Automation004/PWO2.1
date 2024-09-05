package com.audree.infotech.pwo2.tests.masters;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.EquipOrInstIdPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class EquipOrInstId extends BaseTest {

	public EquipOrInstIdPom equipOrInstIdPOM;
	Map<String, String> excelData = new HashMap<>();

	@BeforeMethod
	public void setUp() throws Exception {
		equipOrInstIdPOM = new EquipOrInstIdPom(driver, test, pro);
		// Pre-Load all required data from Excel
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		for (int i = startRow; i <= endRow; i++) {
			excelData.put("EquipOrInstNameUpdate", xls.getCellData("MasterData", "EquipOrInstNameUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));
			excelData.put("BlockUpdate", xls.getCellData("MasterData", "BlockUpdate", i));
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstID", xls.getCellData("MasterData", "EquipOrInstID", i));
			excelData.put("ObjectiveType", xls.getCellData("MasterData", "ObjectiveType", i));
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
			equipOrInstIdPOM.create(excelData.get("EquipOrInstNameUpdate"), excelData.get("BlockUpdate"),
					excelData.get("RoomIdUpdate"), excelData.get("EquipOrInstID"), excelData.get("ObjectiveType"),
					excelData.get("RoomNameUpdate"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void Update() throws Exception {
		try {
			Login(pro.getProperty("EnReviewer"), pro.getProperty("Password"));
			equipOrInstIdPOM.update(excelData.get("EquipOrInstNameUpdate"), excelData.get("EquipOrInstIdUpdate"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
