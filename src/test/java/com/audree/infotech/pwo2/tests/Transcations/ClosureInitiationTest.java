package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.audree.infotech.pwo2.pages.masters.ClosureInitiationPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;

public class ClosureInitiationTest extends BaseTest {

    private ClosureInitiationPom closureInitiationPom;
    private Map<String, String> excelData = new HashMap<>();
	public Xls_Reader xls;


    @BeforeClass
    public void setUp() throws Exception {
		xls = new Xls_Reader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");

        // Initialize POM
        closureInitiationPom = new ClosureInitiationPom(driver, test, pro);

        // Read start and end rows from properties
        int startRow = Integer.parseInt(pro.getProperty("startRow"));
        int endRow = Integer.parseInt(pro.getProperty("endRow"));

        for (int i = startRow; i <= endRow; i++) {
            excelData.put("reasonForFailure", xls.getCellData("WorkUnderClosure", "reasonForFailure", i));
            excelData.put("quantity", xls.getCellData("WorkUnderClosure", "quantity", i));
            excelData.put("conclusion", xls.getCellData("WorkUnderClosure", "conclusion", i));

            // Already Given In Masters
            excelData.put("failureObjectUpdate", xls.getCellData("MasterData", "FailureObjectUpdate", i));
            excelData.put("MaterialCodeUpdate", xls.getCellData("MasterData", "MaterialCodeUpdate", i));
            excelData.put("MaterialNameUpdate", xls.getCellData("MasterData", "MaterialNameUpdate", i));

            excelData.put("RoomIdUpdate", xls.getCellData("MasterData", "RoomIdUpdate", i));
            excelData.put("EquipOrInstIdUpdate", xls.getCellData("MasterData", "EquipOrInstIdUpdate", i));

            excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
            excelData.put("Password", xls.getCellData("Credentials", "Password", i));

            Thread.sleep(500);
            Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
            Thread.sleep(1000);
            scrollPagedown();
            closureInitiationPom.closureInitiationActions();
        }
    }

//    @Test(priority = 1, description = "Selects a work order for closure initiation")
    public void testClosureInitiationSelect() throws Exception {
        Thread.sleep(500);
        closureInitiationPom.searchWorkOrder(excelData.get("EquipOrInstIdUpdate"));
        closureInitiationPom.selectWorkOrder();
    }

    @Test(priority = 2,description = "Submits the closure initiation record")
    public void testSubmittingRecord() throws Exception {
        testClosureInitiationSelect();

        Thread.sleep(500);
        closureInitiationPom.Comments(pro.getProperty("closureInitiationComments"));
        closureInitiationPom.submitButton();
        closureInitiationPom.yesButton();
        closureInitiationPom.inputPasswordPlaceHolder(pro.getProperty("Password2"));
        closureInitiationPom.clickSubmitButton2();
        closureInitiationPom.okButton();    }
}
