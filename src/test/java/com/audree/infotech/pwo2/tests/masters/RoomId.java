package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.RoomIdPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;

public class RoomId extends BaseTest {
	public RoomIdPom roomIdPom;
	Map<String, String> excelData = new HashMap<>();
	public Xls_Reader xls;

	public void setUp() throws Exception {
		xls = new Xls_Reader(System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");
		roomIdPom = new RoomIdPom(driver, test, pro);
	}

	@Test
	public void Create() throws Exception {
		try {
			setUp();
			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
			// Pre-Load all required data from Excel
			// Read the starting and ending rows from the properties file
			int startRow = Integer.parseInt(pro.getProperty("startRow"));
			int endRow = Integer.parseInt(pro.getProperty("endRow"));
			for (int i = startRow; i <= endRow; i++) {
				String RoomNameUpdate = xls.getCellData("MasterData", "RoomNameUpdate", i);
				String RoomId = xls.getCellData("MasterData", "RoomId", i);
				String BlockDropdown = xls.getCellData("MasterData", "BlockUpdate", i);
				String LocationUpdate = xls.getCellData("MasterData", "LocationUpdate", i);
				roomIdPom.Create(RoomNameUpdate, RoomId, BlockDropdown, LocationUpdate);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
