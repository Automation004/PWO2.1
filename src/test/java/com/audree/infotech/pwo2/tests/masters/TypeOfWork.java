package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.TypeOfWorkPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;

public class TypeOfWork extends BaseTest {
	public TypeOfWorkPom typeOfWorkPom;
	Map<String, String> excelData = new HashMap<>();
	public Xls_Reader xls;


	public void setUp() throws Exception {
		xls = new Xls_Reader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");

		typeOfWorkPom = new TypeOfWorkPom(driver, test, pro);
		// Pre-Load all required data from Excel
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));

		for (int i = startRow; i <= endRow; i++) {
			excelData.put("TypeOfWork", xls.getCellData("MasterData", "TypeOfWork", i));
			excelData.put("TypeOfWorkUpdate", xls.getCellData("MasterData", "TypeOfWorkUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));
			excelData.put("EN Reviewer", xls.getCellData("Credentials", "EN Reviewer", i));
		}
	}

	@Test
	public void Create() throws Exception {
		try {
			setUp();
			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
			typeOfWorkPom.create(excelData.get("TypeOfWork"));
			Thread.sleep(300);
			Update();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Update() throws Exception {
		try {
			typeOfWorkPom.update(excelData.get("TypeOfWork"), excelData.get("TypeOfWorkUpdate"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
