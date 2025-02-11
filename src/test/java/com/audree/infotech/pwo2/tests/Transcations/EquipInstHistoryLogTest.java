package com.audree.infotech.pwo2.tests.Transcations;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.tests.masters.EquipInstHistoryLogPom;

public class EquipInstHistoryLogTest extends BaseTest {

    private EquipInstHistoryLogPom equipInstHistoryLogPom;
    Map<String, String> excelData = new HashMap<>();
    private String fromDate;
    private String toDate;

    @BeforeClass
    public void setup() throws Exception {
        // Initialize the EquipInstHistoryLogPom Page Object with driver and test instances
        equipInstHistoryLogPom = new EquipInstHistoryLogPom(driver, test);

        int startRow = Integer.parseInt(pro.getProperty("startRow"));
        int endRow = Integer.parseInt(pro.getProperty("endRow"));
        for (int i = startRow; i <= endRow; i++) {
            excelData.put("Department", xls.getCellData("MasterData", "Department", i));
            // Add any other data you need from your Excel here
        }
        fromDate = pro.getProperty("fromDate");
        toDate = pro.getProperty("toDate");

        // Login to the application using the base method from BaseTest
        Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
    }

    @Test
    public void testEquipInstHistoryLog() throws Throwable {
        // Navigating to Equip/Inst History Log page
        equipInstHistoryLogPom.navigateToEquipInstHistoryLog();
        
        Thread.sleep(500);  // Sleep for stability; consider using WebDriverWait instead
        // Performing actions using the values read from properties
        equipInstHistoryLogPom.enterFromDate(fromDate);
        Thread.sleep(500);  // Sleep for stability; consider using WebDriverWait instead
        equipInstHistoryLogPom.enterToDate(toDate);
        
        // Submit the form
        equipInstHistoryLogPom.clickGetButton();

        // Scroll for visibility (optional)
        equipInstHistoryLogPom.scrollDown();
        equipInstHistoryLogPom.scrollUp();
        
        // Reset and submit again (if needed)
        equipInstHistoryLogPom.clickResetButton();
        equipInstHistoryLogPom.clickGetButton();

        // Final scroll actions
        equipInstHistoryLogPom.scrollDown();
        equipInstHistoryLogPom.scrollUp();
    }
}
