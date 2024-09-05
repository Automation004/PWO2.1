package com.audree.infotech.pwo2.tests.masters;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.pages.masters.BlockPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;

public class BlockTest extends BaseTest {
	public BlockPom blockPom;
	Map<String, String> excelData = new HashMap<>();

	@BeforeClass
	public void setUp() throws Exception {
		blockPom = new BlockPom(driver, test, pro);
		// Pre-Load all required data from Excel
		// Read the starting and ending rows from the properties file
		int startRow = Integer.parseInt(pro.getProperty("startRow"));
		int endRow = Integer.parseInt(pro.getProperty("endRow"));
		for (int i = startRow; i <= endRow; i++) {
			excelData.put("Block", xls.getCellData("MasterData", "Block", i));
			excelData.put("BlockUpdate", xls.getCellData("MasterData", "BlockUpdate", i));
			
			excelData.put("Initiator", xls.getCellData("Credentials", "Initiator", i));
			excelData.put("Password", xls.getCellData("Credentials", "Password", i));
			excelData.put("EN Reviewer", xls.getCellData("Credentials", "EN Reviewer", i));

		}
	}

	@Test
	public void Create() throws Throwable {
		try {
			Login(pro.getProperty("EnReviewer"), pro.getProperty("Password"));
			blockPom.create(excelData.get("Block"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void Update() throws Exception {
		try {
			blockPom.update(excelData.get("Block"), excelData.get("BlockUpdate"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
