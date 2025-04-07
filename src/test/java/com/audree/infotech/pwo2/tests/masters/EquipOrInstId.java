package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.pages.masters.EquipOrInstIdPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;

public class EquipOrInstId extends BaseTest {

	public EquipOrInstIdPom equipOrInstIdPOM;
	Map<String, String> excelData = new HashMap<>();
	public Xls_Reader xls;

	public void setUp() throws Exception {
		xls = new Xls_Reader(System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");

		equipOrInstIdPOM = new EquipOrInstIdPom(driver, test, pro);
	}

	@Test
	public void Create() throws Exception {
		try {
			setUp();
			// Read the starting and ending rows from the properties file
			int startRow = Integer.parseInt(pro.getProperty("startRow"));
			int endRow = Integer.parseInt(pro.getProperty("endRow"));
			for (int i = startRow; i <= endRow; i++) {
				String EquipOrInstNameUpdate = xls.getCellData("MasterData", "EquipOrInstNameUpdate", i);
				String EquipOrInstIdUpdate = xls.getCellData("MasterData", "EquipOrInstIdUpdate", i);
				String BlockUpdate = xls.getCellData("MasterData", "BlockUpdate", i);
				String RoomIdUpdate = xls.getCellData("MasterData", "RoomIdUpdate", i);
				String ObjectiveType = xls.getCellData("MasterData", "ObjectiveType", i);
				String RoomNameUpdate = xls.getCellData("MasterData", "RoomNameUpdate", i);

				Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
				equipOrInstIdPOM.create(EquipOrInstNameUpdate, BlockUpdate, RoomIdUpdate, EquipOrInstIdUpdate,
						ObjectiveType, RoomNameUpdate);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
