package com.audree.infotech.pwo2.tests.Transcations;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.pages.masters.CategorizationOfWorkPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;

public class CategorizationOfWorkTest extends BaseTest {
	public CategorizationOfWorkPom categorizationOfWorkPom;
	Map<String, String> excelData = new HashMap<>();
	public Xls_Reader xls;


	@BeforeClass
	public void setUp() throws Exception {
		xls = new Xls_Reader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");
		// Per-Load all required data from Excel
		categorizationOfWorkPom = new CategorizationOfWorkPom(driver, test, pro);
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		for (int i = startRow; i <= endRow; i++) {

			// Changeable Fields
			excelData.put("CategorizationOfWork", xls.getCellData("CategoryOfWork", "CategorizationOfWork", i));
			excelData.put("TypeOfQMSTool", xls.getCellData("CategoryOfWork", "TypeOfQMSTool", i));
			excelData.put("QMS ID", xls.getCellData("CategoryOfWork", "QMS ID", i));

			// Already Given In Masters
			excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
			excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));

			Login(pro.getProperty("WorkdoneEN"), pro.getProperty("Password2"));
			Thread.sleep(1000);
			// Click "impact Assessment pom"
			categorizationOfWorkPom.categorizationOfWorkActions();

		}
	}

	public void impactAssessmentInitiation() throws Exception {
		Thread.sleep(1000);
		// Search for "equipment Id"
		categorizationOfWorkPom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));
		Thread.sleep(1000);
		// Click on Word Order Id link
		categorizationOfWorkPom.selectWorkOrder();

	}

	public void savingRecord() throws Exception {
		try {
			Thread.sleep(1000);
			categorizationOfWorkPom.setCategorizationOfWorkDropdown(excelData.get("CategorizationOfWork"));
			categorizationOfWorkPom.setTypeOfQmsTool(excelData.get("TypeOfQMSTool"));
			categorizationOfWorkPom.setQmsId(excelData.get("QMS ID"));
			scrollPagedown();
			Thread.sleep(1000);
			categorizationOfWorkPom.saveButton();
			categorizationOfWorkPom.yesButton();
			categorizationOfWorkPom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
			categorizationOfWorkPom.clickSubmitButton2();
			categorizationOfWorkPom.okButton();
			Thread.sleep(1000);
			System.out.println("Record Saved Successfully");
			System.out.println("Record updated Successfully");

		} catch (Exception e) {
			System.out.println("savingRecord method failed :" + e);
		}
	}

	@Test
	public void submittingRecord() throws Exception {
		try {
			Thread.sleep(1000);
			impactAssessmentInitiation();
			savingRecord();
			impactAssessmentInitiation();
			Thread.sleep(1000);
			// Enter comments
			categorizationOfWorkPom.enterComments("SuccessFully Completed the Process of Categorizatio Of Work");
			scrollPagedown();
			categorizationOfWorkPom.submitButton();
			categorizationOfWorkPom.yesButton();
			categorizationOfWorkPom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
			categorizationOfWorkPom.clickSubmitButton2();
			categorizationOfWorkPom.okButton();
			System.out.println("Record Submitted Successfully");
		} catch (Exception e) {
			System.out.println("submittingRecord method failed" + e);
		}
	}

}
