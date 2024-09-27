package com.audree.infotech.pwo2.utils;

import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class CommonData {
	public static WebDriver driver;
	public WebDriverWait wait;// globally declared
	private static ExtentTest test;
	public static Properties pro;

	@SuppressWarnings("deprecation")
	public CommonData(WebDriver driver, ExtentTest _test, Properties _pro)// constructor call
	{
		CommonData.driver = driver;
		CommonData.test = _test;
		CommonData.pro = _pro;
		PageFactory.initElements(driver, this);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@FindBy(xpath = "//*[@placeholder='Password']")
	protected WebElement password;

	@FindBy(xpath = "//*[@placeholder='Password']")
	private WebElement passwordWithSecondIndex;

	@FindBy(xpath = "//*[contains(text(),'Masters')]")
	protected WebElement masterClick;

	@FindBy(xpath = "//button[contains(text(),'Save')]")
	protected WebElement saveButton;

	@FindBy(xpath = "(//button[contains(text(),'Save')])[2]")
	protected WebElement saveButton2;

	@FindBy(xpath = "//select[@formcontrolname='returnTo']")
	protected WebElement returnToDropdown;

	@FindBy(xpath = "//input[@id='Check']")
	protected WebElement checkBox;

	@FindBy(xpath = "//input[@formcontrolname='comments']")
	protected WebElement returnComments;

	// Method to verify the validation message
	public void verifyValidationMessage(WebElement validationElement, String expectedValidationMessage) {
		SoftAssert softAssert = new SoftAssert();
		try {
			test.log(Status.INFO, "Verifying the validation message");
			String actualValidationMessage = validationElement.getText();
			System.out.println(actualValidationMessage);
			softAssert.assertTrue(validationElement.isDisplayed(), "Validation message is not displayed");
			softAssert.assertEquals(actualValidationMessage, expectedValidationMessage,
					"Validation message does not match");
			if (validationElement.isDisplayed() && actualValidationMessage.equals(expectedValidationMessage)) {
				test.log(Status.PASS, "Validation message verified successfully: " + actualValidationMessage);
			} else {
				test.log(Status.FAIL, "Validation message verification failed: Expected [" + expectedValidationMessage
						+ "] but found [" + actualValidationMessage + "]");
			}
		} catch (Exception e) {
			test.log(Status.FAIL, "Exception occurred while verifying the validation message: " + e.getMessage());
		}
		softAssert.assertAll(); // This will log the assertion results but will not stop the test immediately
	}

	public void waitForElementToAppear(By findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	public void waitForWebElementToAppear(WebElement findBy) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(findBy));

	}

	public void scrollPagedown() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
	}

	public void waitForElementToDisappear(WebElement ele) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}

	public void EsigantureActions() throws Exception {
		try {
			yesButton();
		} catch (Exception e) {
			e.printStackTrace();
		}
		test.log(Status.PASS, "Yes button clicked");
		test.log(Status.PASS, "giving password");
		password.sendKeys(pro.getProperty("Password"));
		submitButton();
		test.log(Status.PASS, "Submit button clicked again");
		okButton();
		test.log(Status.PASS, "OK button clicked");
	}

	public void saveEsigantureActions() throws Exception {
		Thread.sleep(1000);
		saveButton.click();
		test.log(Status.PASS, "Save button clicked");
		yesButton();
		test.log(Status.PASS, "Yes button clicked");
		password.sendKeys(pro.getProperty("Password"));
		test.log(Status.PASS, "password given by user");
		submitButton2();
		test.log(Status.PASS, "Submit button clicked again");
		okButton();
		test.log(Status.PASS, "OK button clicked");
	}

	public void submitEsigantureActions() throws Exception {
		submitButton();
		test.log(Status.PASS, "Submit button clicked");
		yesButton();
		test.log(Status.PASS, "Yes button clicked");
		password.sendKeys(pro.getProperty("Password"));
		test.log(Status.PASS, "Given valid password");
		submitButton2();
		test.log(Status.PASS, "Submit button clicked again");
		okButton();
		test.log(Status.PASS, "OK button clicked");
	}

	public void firstSaveEsigantureActionsForWUP() throws Exception {
		test.log(Status.PASS, "Save button clicked");
		yesButton();
		test.log(Status.PASS, "Yes button clicked");
		password.sendKeys(pro.getProperty("Password"));
		test.log(Status.PASS, "password given by user");
		submitButton3();
		test.log(Status.PASS, "Submit button clicked");
		okButton();
		test.log(Status.PASS, "OK button clicked");
	}

	public void firstSubmitEsigantureActionsForWUP() throws Exception {
		yesButton();
		test.log(Status.PASS, "Yes button clicked");
		password.sendKeys(pro.getProperty("Password"));
		test.log(Status.PASS, "password given by user");
		submitButton3();
		test.log(Status.PASS, "Submit button clicked");
		okButton();
		test.log(Status.PASS, "OK button clicked");
	}
	// Helper method to check if the element is present
	public boolean isElementPresent(By locator) {
		try {
			driver.findElement(locator);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void closeOpenedFile() throws Exception {
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		String parent = it.next();
		String child = it.next();
		driver.switchTo().window(child);
		driver.close();
		driver.switchTo().window(parent);
		Thread.sleep(2000);
		JavascriptExecutor jj = (JavascriptExecutor) driver;
		jj.executeScript("window.scrollTo(0,1000)", "");
		Thread.sleep(2000);
	}

	// **********************************************************************************************************

	// **********************************************************************************************************************
	// TITLE_SUBMIT
	@FindBy(how = How.XPATH, using = "//*[@title='Submit']")
	public WebElement TITLE_SUBMIT;

	public void TITLE_SUBMIT() throws Exception {

		WebElement Color = driver.findElement(By.xpath("//*[@title='Submit']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		TITLE_SUBMIT.click();
		Thread.sleep(500);
	}

	public void checkBox() throws Exception {
		checkBox.click();
		Thread.sleep(500);
	}

	// EDIT_TITLE
	@FindBy(how = How.XPATH, using = "(//*[@title='Edit'])[1]")
	public WebElement EDIT_TITLE;

	public void EDIT_TITLE() throws Exception {

		WebElement Color = driver.findElement(By.xpath("(//*[@title='Edit'])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		EDIT_TITLE.click();
		Thread.sleep(500);
	}

	// SubmitType12

	@FindBy(how = How.XPATH, using = "(//*[@type='submit'])[1]")
	public WebElement SubmitType1;
	@FindBy(how = How.XPATH, using = "(//*[@type='submit'])[2]")
	public WebElement SubmitType2;

	// SubmitText01
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Submit')])[1]")
	public WebElement SubmitText01;

	public void SubmitText01() throws Exception {
		WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Submit')])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		SubmitText01.click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// SubmitText04
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Submit')])[4]")
	public WebElement Submit_Button4;

	public void Submit_Button4() throws Exception {
		WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Submit')])[4]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Submit_Button4.click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// *****************************************************************************************************************
	// *****************************************************************************************************************
	// *****************************************************************************************************************
	// Save Button
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Save')])[1]")
	public WebElement Save1;
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Save')])[2]")
	public WebElement Save2;

	public void Save_Button_TCB() throws Exception {
		try {
			WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Save')])[1]"));
			JavascriptExecutor Js = (JavascriptExecutor) driver;
			Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);
			Save1.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// *****************************************************************************************************************
		try {
			WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Save')])[2]"));
			JavascriptExecutor Js = (JavascriptExecutor) driver;
			Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

			Save2.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// *****************************************************************************************************************
	// *****************************************************************************************************************

	public void saveButton() throws Exception {
		WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Save')])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);
		Save1.click();
	}

	// *****************************************************************************************************************
	public void Save_Button2() throws Exception {
		Save2.click();
	}
	// *********************************************************************************************************************
	// SUBMIT_BUTTON
	// *[@type='submit' or @value='Log In' or @title='submit' or
	// contains(text(),'Save') or contains(text(),'Verify')]"));

	// *[@type='submit' or @ title='submit' or contains(text(),'Submit') or
	// contains(text(),'Save') or contains(text(),'Verify')]

	@FindBy(how = How.XPATH, using = "//*[@type='submit' or @ title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]")
	public WebElement Submit;

	public void Submit() throws Exception {
		WebElement Color = driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Submit.click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Thread.sleep(500);
	}

	// *********************************************************************************************************************
	// Submit_Text
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Submit')])[1]")
	public WebElement Submit_Text_01;
	// *********************************************************************************************************************
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Submit')])[2]")
	public WebElement Submit_Text_02;
	// *********************************************************************************************************************
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Submit')])[3]")
	public WebElement Submit_Text_03;

	// *********************************************************************************************************************
	// *********************************************************************************************************************
	public void submitButton() throws Exception {
		Submit_Text_01.click();
	}

	// *********************************************************************************************************************
	// *********************************************************************************************************************
	public void submitButton2() throws Exception {
		Submit_Text_02.click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// *********************************************************************************************************************
	// *********************************************************************************************************************
	public void submitButton3() throws Exception {
		Submit_Text_03.click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// *********************************************************************************************************************
	// *********************************************************************************************************************
	public void Submit_Button_TCB() throws Exception {
		try {
			WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Submit')])[1]"));
			JavascriptExecutor Js = (JavascriptExecutor) driver;
			Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

			Submit_Text_01.click();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Submit')])[2]"));
			JavascriptExecutor Js = (JavascriptExecutor) driver;
			Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

			Submit_Text_02.click();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Submit')])[3]"));
			JavascriptExecutor Js = (JavascriptExecutor) driver;
			Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

			Submit_Text_03.click();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'OK') or contains(text(),'Ok')]")
	public WebElement h;

	public void Ok_Button() throws Exception {
		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),'OK') or contains(text(),'Ok')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);
		h.click();
		Thread.sleep(2500);
	}

	// SubmitType_1
	@FindBy(how = How.XPATH, using = "(//*[@type='submit'])[1]")
	public WebElement SubmitType_1;

	public void SubmitType_1() throws Exception {

		WebElement Color = driver.findElement(By.xpath("(//*[@type='submit'])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		SubmitType_1.click();
		Thread.sleep(500);
	}

	// SubmitType_2
	@FindBy(how = How.XPATH, using = "(//*[@type='submit'])[2]")
	public WebElement SubmitType_2;

	public void SubmitType_2() throws Exception {

		WebElement Color = driver.findElement(By.xpath("(//*[@type='submit'])[2]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		SubmitType_2.click();
		Thread.sleep(500);
	}

	// Submit_Type
	@FindBy(how = How.XPATH, using = "(//button[@type='submit'])[1]")
	public WebElement ST1;

	public void Submit_Type1() throws Exception {

		WebElement Color = driver.findElement(By.xpath("(//button[@type='submit'])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		ST1.click();
		Thread.sleep(500);
	}

	// Submit_Type
	@FindBy(how = How.XPATH, using = "(//*[@title='Submit'])[1]")
	public WebElement Submit_Type;

	public void Submit_Type() throws Exception {

		WebElement Color = driver.findElement(By.xpath("(//*[@title='Submit'])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Submit_Type.click();
		Thread.sleep(500);
	}

	// Add_Button_FFPC
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Add') or contains(text(),'ADD')]")
	public WebElement Add_Button;

	@SuppressWarnings("deprecation")
	public void Add_Button() throws Exception {

		Add_Button.click();
		Thread.sleep(1500);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Yes') or contains(text(),'yes')]")
	public WebElement Yes;

	public void yesButton() throws Exception {

		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);
		Yes.click();
	}

	// Ok
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'OK') or contains(text(),'Ok')]")
	public WebElement Ok;

	public void okButton() throws Exception {

		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),'OK') or contains(text(),'Ok')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);
		Ok.click();
	}

	// SEARCHBOX
	@FindBy(how = How.XPATH, using = "//input[@placeholder='Search...']")
	public WebElement SearchBox;

	public void SearchBox(String x) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", SearchBox);
		SearchBox.click();
		SearchBox.clear();
		SearchBox.sendKeys(x);
	}

	public void SearchBox2(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath("//input[@type='search']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		SearchBox.sendKeys(x);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// Comments
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='comments' or @formcontrolname='Comments']")
	public WebElement Comments;

	public void Comments(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath(
				"//*[@formcontrolname='comments' or @formcontrolname='Comments' or @formcontrolname='remarks']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Comments.clear();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// Thread.sleep(500);
		Comments.sendKeys(x);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// Thread.sleep(500);
	}

	// Comments
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='ExpiryDate' or @formcontrolname='ExpiryDate']")
	public WebElement ExpiryDate;

	public void ExpiryDate(String x) throws Exception {
		WebElement Color = driver
				.findElement(By.xpath("//*[@formcontrolname='ExpiryDate' or @formcontrolname='ExpiryDate']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		ExpiryDate.sendKeys(x);
		Thread.sleep(500);
	}

	// Date01SK
	@FindBy(how = How.XPATH, using = "(//*[@type='date'])[3]")
	public WebElement Date01SK;

	public void Date01SK(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath("(//*[@type='date'])[3]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Date01SK.sendKeys(x);
		Thread.sleep(500);
	}

	// Comments_Text
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Comment')]")
	public WebElement Comments_Text;

	public void Comments_Text(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath("//*[contains(text(),'Comment')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Comments_Text.sendKeys(x);
		Thread.sleep(500);
	}

	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='comments' or @formcontrolname='Comments']")
	public WebElement Comments55;

	public void Comment_Box(String x) throws Exception {
		WebElement Color = driver
				.findElement(By.xpath("//*[@formcontrolname='comments' or @formcontrolname='Comments']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Comments.clear();// Thread.sleep(500);
		Comments.sendKeys(x);// Thread.sleep(500);
	}

	// Comments_Id
	@FindBy(how = How.XPATH, using = "//*[@id='comments']")
	public WebElement Comments_Id;

	public void Comments_Id(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath("//*[@id='comments']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Comments_Id.sendKeys(x);
		Thread.sleep(500);
	}

	// Comments2
	@FindBy(how = How.XPATH, using = "(//*[@formcontrolname='comments' or @formcontrolname='Comments'])[2]")
	public WebElement Comments2;

	public void Comments2(String x) throws Exception {
		WebElement Color = driver
				.findElement(By.xpath("(//*[@formcontrolname='comments' or @formcontrolname='Comments'])[2]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Comments2.clear();
		Thread.sleep(500);
		Comments2.sendKeys(x);
		Thread.sleep(500);
	}

	// Remarks
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='noofContainers' or @formcontrolname='numberOfContainers' or @formcontrolname='numberOfContainers' or @formcontrolname='numberOfContainers' ]")
	public WebElement Container_No;

	public void Container_No(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath(
				"//*[@formcontrolname='noofContainers' or @formcontrolname='numberOfContainers' or @formcontrolname='numberOfContainers' or @formcontrolname='numberOfContainers' ]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Container_No.clear();
		Thread.sleep(1500);
		Container_No.sendKeys(x);
	}

	// DCEInvoiceNumber
	@FindBy(how = How.XPATH, using = "//input[@formcontrolname='challanNumber']")
	public WebElement DCEInvoiceNumber;

	public void DCEInvoiceNumber(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath("//input[@formcontrolname='challanNumber']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		// DCEInvoiceNumber.clear();
		DCEInvoiceNumber.sendKeys(x);
		Thread.sleep(500);
	}

	// VendorStorageCondition
	@FindBy(how = How.XPATH, using = "//input[@formcontrolname='vendorstorageLocation']")
	public WebElement VendorStorageCondition;

	public void VendorStorageCondition(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath("//input[@formcontrolname='vendorstorageLocation']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		VendorStorageCondition.clear();
		VendorStorageCondition.sendKeys(x);
		Thread.sleep(500);
	}

	// Quantity
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='quantity' or @formcontrolname='quantity']")
	public WebElement Quantity;

	public void Quantity(String x) throws Exception {
		WebElement Color = driver
				.findElement(By.xpath("//*[@formcontrolname='quantity' or @formcontrolname='quantity']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Quantity.clear();
		Thread.sleep(500);
		Quantity.sendKeys(x);
		Thread.sleep(500);
	}

	// RequestQuantity
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='requestQuantity' or @formcontrolname='requestQuantity']")
	public WebElement RequestQuantity;

	public void RequestQuantity(String x) throws Exception {
		WebElement Color = driver
				.findElement(By.xpath("//*[@formcontrolname='requestQuantity' or @formcontrolname='requestQuantity']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		RequestQuantity.clear();
		Thread.sleep(500);
		RequestQuantity.sendKeys(x);
		Thread.sleep(500);
	}

	// ReceivedQuantity
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='receivedQuantity' or @formcontrolname='receivedQuantity']")
	public WebElement ReceivedQuantity;

	public void ReceivedQuantity(String x) throws Exception {
		// WebElement Color =
		// driver.findElement(By.xpath("//*[@formcontrolname='receivedQuantity' or
		// @formcontrolname='receivedQuantity']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');",
				ReceivedQuantity);

		ReceivedQuantity.clear();
		Thread.sleep(500);
		ReceivedQuantity.sendKeys(x);
		Thread.sleep(500);
	}

	// Remarks
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='remark'  or @formcontrolname='returnedRemarks' or @formcontrolname='Remark' or @formcontrolname='remarks' or @formcontrolname='Remarks' ]")
	public WebElement Remarks;

	public void Remarks(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath(
				"//*[@formcontrolname='remark'  or @formcontrolname='returnedRemarks' or @formcontrolname='Remark' or @formcontrolname='remarks' or @formcontrolname='Remarks' ]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Remarks.clear();
		Thread.sleep(500);
		Remarks.sendKeys(x);
		Thread.sleep(500);
	}

	public void Remarks_SK(String x) throws Exception// ******************
	{
		WebElement Color = driver.findElement(By.xpath(
				"//*[@formcontrolname='remark'  or @formcontrolname='returnedRemarks' or @formcontrolname='Remark' or @formcontrolname='remarks' or @formcontrolname='Remarks' ]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Remarks.sendKeys(x);
		Thread.sleep(1500);
	}

	// *****************************************************************************************************
	// *****************************************************************************************************
	// Batch_Number
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='batchNumber']")
	public WebElement Batch_Number;

	public void Batch_Number(String x) throws Exception {
		WebElement Color = driver.findElement(By.xpath("//*[@formcontrolname='batchNumber']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Batch_Number.clear();
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		Batch_Number.sendKeys(x);
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	// Password_Fill
	@FindBy(how = How.XPATH, using = "//*[@formcontrolname='password' or formcontrolname='Password']")
	public WebElement Password_Fill;

	public void Password_Fill(String x) throws Exception {

		Password_Fill.sendKeys(x);
		Thread.sleep(500);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	// Return
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Return')]")
	public WebElement ReturnButton;

	public void returnButton() throws Exception {

		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),'Return')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);
		ReturnButton.click();
	}

	// Cancel_Button
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Cancel')]")
	public WebElement Cancel_Button;

	public void Cancel_Button() throws Exception {
		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),'Cancel')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Cancel_Button.click();
		Thread.sleep(500);
	}

	// UPDATE
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Update')]")
	public WebElement UpdateButton;

	public void UpdateButton() throws Exception {

		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),'Update')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		UpdateButton.click();
		Thread.sleep(500);
	}

	// Split_Button
	@FindBy(how = How.XPATH, using = "//button[contains(text(),' Split ')]")
	public WebElement Split_Button;

	public void Split_Button() throws Exception {

		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),' Split ')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Split_Button.click();
		Thread.sleep(500);
	}

	// Proceed_Button
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Proceed')]")
	public WebElement Proceed_Button;

	public void Proceed_Button() throws Exception {
		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),'Proceed')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);
		Proceed_Button.click();
		Thread.sleep(500);
	}

	// Get_Button //button[contains(text(),' Get ')]
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Get') or contains(text(),'Get')]")
	public WebElement Get_Button;

	public void Get_Button() throws Exception {

		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),'Get') or contains(text(),'Get')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		Get_Button.click();
		Thread.sleep(2500);
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
	}

	// Issue_Button
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Issue')]")
	public WebElement Issue_Button;

	public void Issue_Button() throws Exception {

		WebElement Color = driver.findElement(By.xpath("//button[contains(text(),' Issue')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Issue_Button.click();
		Thread.sleep(500);
	}

	// Back_Button
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Back')])[1]")
	public WebElement Back_Button;

	public void Back_Button() throws Exception {

		WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Back')])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Back_Button.click();
		Thread.sleep(500);
	}

	// Back_Button
	@FindBy(how = How.XPATH, using = "(//button[contains(text(),'Back')])[2]")
	public WebElement Back_Button2;

	public void Back_Button2() throws Exception {

		WebElement Color = driver.findElement(By.xpath("(//button[contains(text(),'Back')])[2]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Back_Button2.click();
		Thread.sleep(500);
	}

	// Print//Reprint button
	@FindBy(how = How.XPATH, using = "//button[contains(text(),'Print') or contains(text(),'RePrint')]")
	public WebElement Print_Button;

	public void Print_Button() throws Exception {

		WebElement Color = driver
				.findElement(By.xpath("//button[contains(text(),'Print') or contains(text(),'RePrint')]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		Print_Button.click();
		Thread.sleep(500);
	}

	// ***************************************************************************Merge
	// with multiple edits if possible
	// EDIT_BUTTON
	@FindBy(how = How.XPATH, using = "(//*[@title='Edit' or @actiontype='Edit'])[1]")
	public WebElement Edit_Action_Button;

	public void editActionButton() throws Exception {
		WebElement Color = driver.findElement(By.xpath("(//*[@title='Edit' or @actiontype='Edit'])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);
		Edit_Action_Button.click();
	}

	@FindBy(how = How.XPATH, using = "(//*[@class='fa fa-pencil'])[1]")
	public WebElement EditButton;

	public void editButton() throws Exception {
		EditButton.click();
	}

	// ActionEye01
	@FindBy(how = How.XPATH, using = "(//*[@title='View' or @class='fa fa-eye'])[2]")
	public WebElement ActionEye02;

	public void ViewButton2() throws Exception {
		try {
			WebElement Color = driver.findElement(By.xpath("(//*[@title='View' or @class='fa fa-eye'])[2]"));
			JavascriptExecutor Js = (JavascriptExecutor) driver;
			Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 2px solid black;');", Color);

			ActionEye02.click();
		} catch (Exception e) {
		}
		Thread.sleep(3000);
	}

	// ActionType_EditAction_Button
	@FindBy(how = How.XPATH, using = "(//*[@actiontype='editAction'])[1]")
	public WebElement ActionType_EditAction_Button;

	public void ActionType_EditAction_Button() throws Exception {
		WebElement Color = driver.findElement(By.xpath("(//*[@actiontype='editAction'])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color);

		ActionType_EditAction_Button.click();
		Thread.sleep(500);
	}

	@FindBy(how = How.XPATH, using = "(//button[contains(.,'No')])[1]")
	public WebElement noButton;

	public void noButton() throws Exception {
		WebElement Color = driver.findElement(By.xpath("(//button[contains(.,'No')])[1]"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid red;');", Color);
		noButton.click();
	}

	// Text Box
	@FindBy(xpath = "(//input[@type='text'])[1]")
	public WebElement textBoxOne;

	@FindBy(xpath = "(//input[@type='text'])[2]")
	public WebElement textBoxTwo;

	@FindBy(xpath = "(//input[@type='text'])[3]")
	public WebElement textBoxThree;

	public void textBoxOne(String x) throws Exception {
		textBoxOne.click();
		Thread.sleep(300);
		textBoxOne.sendKeys(x, Keys.ENTER);
	}

	public void textBoxTwo(String x) throws Exception {
		textBoxTwo.click();
		Thread.sleep(300);
		textBoxTwo.sendKeys(x, Keys.ENTER);
	}

	public void textBoxThree(String x) throws Exception {
		textBoxThree.click();
		Thread.sleep(300);
		textBoxThree.sendKeys(x, Keys.ENTER);
	}

	// Radio_Button_1
	@FindBy(xpath = "(//*[@type='radio'])[1]")
	public WebElement Radio_Button_1;

	@FindBy(xpath = "(//*[@type='radio'])[2]")
	public WebElement Radio_Button_2;

	@FindBy(xpath = "(//*[@type='radio'])[3]")
	public WebElement Radio_Button_3;

	public void radioButton1() throws Exception {
		Radio_Button_1.click();
		Thread.sleep(300);
	}

	public void radioButton2() throws Exception {
		Radio_Button_2.click();
		Thread.sleep(300);
	}

	public void radioButton3() throws Exception {
		Radio_Button_3.click();
		Thread.sleep(300);

	}
}
