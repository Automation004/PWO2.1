package com.audree.infotech.pwo2.tests.masters;
import org.testng.annotations.Test;
import com.audree.infotech.pwo2.pages.masters.BlockPom;
import com.audree.infotech.pwo2.testcomponents.BaseTest;
import com.audree.infotech.pwo2.testcomponents.Xls_Reader;

public class BlockTest extends BaseTest {
	public BlockPom blockPom;
	public Xls_Reader xls;
	String Block;
	String BlockUpdate;

	public void setUp() throws Exception {
		xls = new Xls_Reader(System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");
		blockPom = new BlockPom(driver, test, pro);
	}

	@Test
	public void Create() throws Throwable {
		try {
			setUp();
			// Read the starting and ending rows from the properties file
			int startRow = Integer.parseInt(pro.getProperty("startRow"));
			int endRow = Integer.parseInt(pro.getProperty("endRow"));
			for (int i = startRow; i <= endRow; i++) {
				Block = xls.getCellData("MasterData", "Block", i);
				BlockUpdate = xls.getCellData("MasterData", "BlockUpdate", i);

				Login(pro.getProperty("Initiator"), pro.getProperty("Password"));
				blockPom.navigateToMasterBlock();
				blockPom.clickBlock();
				blockPom.clickCreateButton();
				blockPom.submitButton();
				blockPom.enterBlockData(Block);
				blockPom.submitButton();
				blockPom.noButton();
				blockPom.submitButton();
				blockPom.yesButton();
				blockPom.Password_Fill(pro.getProperty("Password"));
				blockPom.submitButton();
				blockPom.okButton();
				Thread.sleep(300);
				Update();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void Update() throws Exception {
		try {
			blockPom.update(Block, BlockUpdate);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
