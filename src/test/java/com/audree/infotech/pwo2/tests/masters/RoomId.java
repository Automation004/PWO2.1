package com.audree.infotech.pwo2.tests.masters;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.RoomIdPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class RoomId extends BaseTest {
    public RoomIdPom roomIdPom;
    Map<String, String> excelData = new HashMap<>();

    @BeforeMethod()
    public void setUp() throws Exception {
        roomIdPom = new RoomIdPom(driver, test, pro);
        // Pre-Load all required data from Excel
    	// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		for (int i = startRow; i <= endRow; i++) {            excelData.put("RoomNameUpdate", xls.getCellData("MasterData", "RoomNameUpdate", i));
            excelData.put("RoomId", xls.getCellData("MasterData", "RoomId", i));
            excelData.put("BlockDropdown", xls.getCellData("MasterData", "BlockUpdate", i));
            excelData.put("Location", xls.getCellData("MasterData", "Location", i));
            excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
            excelData.put("LocationUpdate", xls.getCellData("MasterData", "LocationUpdate", i));
            
			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));
			excelData.put("EN Reviewer", xls.getCellData("Credentials", "EN Reviewer", i));
        }
    }

    @Test
    public void Create() throws Exception {
        try {
			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
            roomIdPom.Create(excelData.get("RoomNameUpdate"), excelData.get("RoomId"), excelData.get("BlockDropdown"), excelData.get("Location"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void Update() throws Exception {
        try {
            roomIdPom.Update(excelData.get("RoomNameUpdate"), excelData.get("RoomIdUpdate"), excelData.get("LocationUpdate"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
