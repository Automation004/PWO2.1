package com.audree.infotech.pwo2.tests.masters;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.SectionPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class Section extends BaseTest {

	public SectionPom sectionPom;
	private Map<String, String> excelData = new HashMap<>();

	public Section() {
		this.excelData = new HashMap<>();
	}

	@BeforeMethod()
	public void setUp() throws Exception {
		try {
			sectionPom = new SectionPom(driver, test, pro);
			// Pre-Load all required data from Excel
			// Read the starting and ending rows from the properties file
			int startRow = Integer.parseInt(pro.getProperty("startRow"));
			int endRow = Integer.parseInt(pro.getProperty("endRow"));
			for (int i = startRow; i <= endRow; i++) {
				excelData.put("Section", xls.getCellData("MasterData", "Section", i));
				excelData.put("SectionUpdate", xls.getCellData("MasterData", "SectionUpdate", i));

				excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
				excelData.put("Password", xls.getCellData("Credentials", "Password", i));
				excelData.put("EN Reviewer", xls.getCellData("Credentials", "EN Reviewer", i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Initialization failed", e);
		}
	}

	@Test
	public void Create() throws Exception {
		try {
			Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
			sectionPom.create(excelData.get("Section"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void Update() throws Exception {
		try {
			sectionPom.update(excelData.get("Section"), excelData.get("SectionUpdate"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}