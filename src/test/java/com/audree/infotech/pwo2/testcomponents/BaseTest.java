package com.audree.infotech.pwo2.testcomponents;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

@SuppressWarnings("deprecation")
public class BaseTest {
	public static WebDriver driver;
	ExtentHtmlReporter Report;
	public static ExtentReports extent;
	public ExtentTest test;
	protected Properties pro;
	public Robot r;
	public WebDriverWait wait;// globally declared

	public Xls_Reader xls = new Xls_Reader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\com.exceldata\\pwo2.1.xlsx");

	@BeforeSuite(alwaysRun = true)
	public void suiteSetUp() throws IOException {
		if (driver == null) {
			pro = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\com.properties\\config.properties");
			pro.load(ip);
			System.setProperty("webdriver.http.factory", "jdk-http-client");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get(pro.getProperty("urlQa"));
		}
	}

	public static ExtentReports getReportObject(String testName) {
		if (extent == null) { // Only initialize if extent is null
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd--HH.mm.ss").format(new Date());// time stamp
			String repName = testName + " Test-Report-" + timeStamp + ".html";
			ExtentHtmlReporter reporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "/Reports/" + repName);
			reporter.config().setEncoding("utf-8");
			reporter.config().setReportName("Automation Test Result");
			reporter.config().setTheme(Theme.STANDARD);
			reporter.config().setDocumentTitle("WMPS 2.0"); // Tile of report
			reporter.config().setReportName("WMPS 2.0"); // name of the report

			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Organization", "Audree Infotech Pvt Ltd");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("Tester", "Sharuk Komminapalli");
		}
		return extent;
	}

	@BeforeClass(alwaysRun = true)
	public void initializeExtentTest() {
		// Get the singleton instance of ExtentReports
		extent = getReportObject(this.getClass().getSimpleName());
		test = extent.createTest(this.getClass().getSimpleName());
	}

	@AfterMethod()
	public void EndReport() {
		extent.flush();
		System.out.println("Flush Completed");
	}

	public static void Login(String LoginId, String Password) throws Exception {
		driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
		driver.findElement(By.xpath("//input[@placeholder='User ID']")).sendKeys(LoginId);
		driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(Password);
		driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
	}

	// @AfterMethod()
	// public void getResult(ITestResult result) throws IOException {
	// if (result.getStatus() == ITestResult.FAILURE) {
	// Test.log(Status.FAIL, "Test Case Failed2 " + result.getName());
	// Test.log(Status.FAIL, "Test Case Failed " + result.getThrowable()); // get
	// exception in extent Reports
	// String scrceenshotpath = BaseClass.getScreenshot(driver,
	// result.getMethod().getMethodName());
	// Test.fail(result.getName(),
	// MediaEntityBuilder.createScreenCaptureFromPath(scrceenshotpath).build()); //
	// To
	// // Add screenshot in extent report
	// } else if (result.getStatus() == ITestResult.SKIP) {
	// Test.log(Status.SKIP, "Test Case Skipped " + result.getName());
	// } else if (result.getStatus() == ITestResult.SUCCESS) {
	//
	// Test.log(Status.PASS, "Test Case Failed " + result.getName());
	// Test.log(Status.PASS, "Test Case Failed " + result.getThrowable()); // get
	// exception in extent Reports
	// String scrceenshotpath = BaseClass.getScreenshot1(driver, result.getName());
	// Test.pass(result.getName(),
	// MediaEntityBuilder.createScreenCaptureFromPath(scrceenshotpath).build()); //
	// To
	// // Add
	// // screensh
	// }
	// }

	// Takes Screenshot
	@SuppressWarnings("unused")
	protected String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		File source = ((TakesScreenshot) (driver)).getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedReport/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		org.apache.commons.io.FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@SuppressWarnings("unused")
	private String getScreenshot1(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		File source = ((TakesScreenshot) (driver)).getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/PassScreenshots/" + screenshotName + dateName + "png";
		File finalDestination = new File(destination);
		org.apache.commons.io.FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	// public void Quit() throws Exception {
	// WebElement icon = driver.findElement(By.xpath("//*[@class='avatarIcon']"));
	// // Actions a = new
	// //
	// Actions(driver);//*************************************************************************
	// // a.doubleClick(icon).perform();//***************************Double Click
	// icon.click();
	// Thread.sleep(3000);
	// driver.findElement(By.xpath("//a[contains(.,' Quit')]")).click(); // Quit
	// Test.log(Status.PASS, "User Clicked on Quit button");
	// Thread.sleep(3000);
	// driver.findElement(By.xpath("//a[contains(text(),'Yes')]")).click(); // Yes
	// Test.log(Status.PASS, "User Clicked on Yes button");
	// Thread.sleep(3000);
	// }

	public void Logout() throws Exception {
		// Logout
		// Test.log(Status.INFO, "System should display QMS Spectrum dashboard screen");
		driver.findElement(By.xpath("//*[@id='navbarDropdown1']")).click();
		// Test.log(Status.PASS, "Profile Clicked");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[contains(.,'Quit')]")).click();
		// Test.log(Status.PASS, "Quit Clicked");
		Thread.sleep(3000);
		// Test.log(Status.INFO, "System should display QMS Spectrum dashboard screen");
		driver.findElement(By.xpath("//a[contains(.,'Yes')]")).click();
		Thread.sleep(3000);
		// Test.log(Status.PASS, "yes Clicked");
		driver.findElement(By.xpath("//*[@id='navbarDropdown1']")).click();
		// Test.log(Status.PASS, "Profile Clicked");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[contains(.,'Logout')]")).click();
		// Test.log(Status.PASS, "Logout Clicked");
		Thread.sleep(3000);
		// Test.log(Status.INFO, "System should display Welcome to QMS Spectrum login
		// page");
		driver.findElement(By.xpath("//a[contains(.,'Yes')]")).click();
		// Test.log(Status.INFO, "Yes Clicked and displayed login page");
		Thread.sleep(3000);
	}

	public void Disable() throws Exception {
		driver.findElement(By.xpath("(//*[@title=\"Click to Edit\"])[1]")).click(); // Edit
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@formcontrolname=\"Comments\"]")).sendKeys(pro.getProperty("Comments"));// comments
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@type=\"checkbox\"]")).click(); // checkbox
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@title=\"Click to Update\"]")).click(); // Update
		Thread.sleep(3000);
	}

	public WebElement waitForWebElementToAppear(WebElement findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement isExistes = wait.until(ExpectedConditions.visibilityOf(findBy));
		return isExistes;
	}

	// ***************************************************************************************************
	public void scrollPagedown() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);
	}

	// ***************************************************************************************************
	public void scrollPageup() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		Thread.sleep(3000);
	}

	public void scrollPagedownSlow() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,100)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,200)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,300)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,400)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,500)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,600)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,700)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,800)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,900)");
		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,1000)");
		Thread.sleep(1000);
	}

	public void UploadFile(String path) throws Exception {

		r = new Robot();
		r.delay(3000);
		// put path to file in a clipboard
		StringSelection s = new StringSelection(path);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		// ctrl+V press
		r.keyPress(KeyEvent.VK_CONTROL);// press on ctrl key
		r.keyPress(KeyEvent.VK_V);// press on ctrl key
		r.delay(3000);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
		r.delay(3000);
		// Enter
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(6000);
		System.out.println("uploaded Successfully");
	}

	public void AttachFile() throws Exception {
		WebElement Color = driver.findElement(By.xpath("//*[@type='file']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 2px solid black;');", Color);
		WebElement AttachFile = driver.findElement(By.xpath("//*[@type='file']"));
		Thread.sleep(3000);
		Actions action = new Actions(driver);
		action.click(AttachFile).perform();
		Thread.sleep(3000);
	}

	// ***********************************************************************************************************************
	// MoveCursor
	@FindBy(how = How.XPATH, using = "//*[@type='submit' or contains(text(),'Submit') or contains(text(),'Verify')]")
	public WebElement submit;

	public void submitclick() {
		submit.click();
	}

	// *****************************************************************************************************************
	// public WebElement MoveCursor;
	public void MoveCursor() throws Exception {
		WebElement MoveCursor;
		Actions actions = new Actions(driver);
		MoveCursor = driver.findElement(By.xpath("//*[text()='Masters']"));
		Thread.sleep(2000);
		actions.moveToElement(MoveCursor).perform();
		Thread.sleep(1000);
	}

	/*******************************************/
	public void Close_Opened_File() throws Exception {
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

	public void Password(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		WebElement Color2 = driver
				.findElement(By.xpath("//*[@formcontrolname='password' or formcontrolname='Password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//*[@formcontrolname='password' or formcontrolname='Password']"))
				.sendKeys(pro.getProperty(Password));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	public void WMPS_Login(String username, String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		WebElement Color1 = driver.findElement(By.xpath("//*[@formcontrolname='LoginId']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//*[@formcontrolname='LoginId']")).sendKeys(pro.getProperty(username));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		WebElement Color2 = driver.findElement(By.xpath("//*[@formcontrolname='Password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//*[@formcontrolname='Password']")).sendKeys(pro.getProperty(Password));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		WebElement Color3 = driver.findElement(By.xpath("(//button[text()='Login'])"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("(//button[text()='Login'])")).click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		List<WebElement> termination = driver.findElements(By.xpath("//*[@id='BtnWApp']"));
		if (!termination.isEmpty()) {
			termination.get(0).click();
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		Thread.sleep(1000);

		WebElement Color4 = driver.findElement(By.xpath("//*[contains(text(),'WMPS - ')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		try {
			driver.findElement(By.xpath("//*[contains(text(),'WMPS - ')]")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ********************************************************************************************************************************

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public void WMPS_Login_Release(String username, String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color1 = driver.findElement(By.xpath("//*[@formcontrolname='LoginId']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//*[@formcontrolname='LoginId']")).sendKeys(pro.getProperty(username));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// Thread.sleep(2000);

		WebElement Color2 = driver.findElement(By.xpath("//*[@formcontrolname='Password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//*[@formcontrolname='Password']")).sendKeys(pro.getProperty(Password));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// Thread.sleep(2000);

		WebElement Color3 = driver.findElement(By.xpath("(//button[text()='Login'])"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("(//button[text()='Login'])")).click();

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		// Thread.sleep(2000);

		List<WebElement> termination = driver.findElements(By.xpath("//*[@id='BtnWApp']"));
		if (!termination.isEmpty()) {
			termination.get(0).click();
		}
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	}

	public void Disable(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color0 = driver.findElement(By.xpath("(//*[@title='Edit'])[1]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color0);
		driver.findElement(By.xpath("(//*[@title='Edit'])[1]")).click();
		Thread.sleep(3000);

		WebElement Color1 = driver
				.findElement(By.xpath("//*[@formcontrolname='comments' or @formcontrolname='Comments']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//*[@formcontrolname='comments' or @formcontrolname='Comments']"))
				.sendKeys("Disable_Comment");
		Thread.sleep(3000);

		WebElement CheckBox = driver.findElement(By.xpath("//*[@type='checkbox']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", CheckBox);
		driver.findElement(By.xpath("//*[@type='checkbox']")).click();
		Thread.sleep(3000);

		WebElement Color2 = driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit'  or @ title='Click to update' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit'  or @ title='Click to update' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color3 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(3000);

		WebElement Color4 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty(Password));
		Thread.sleep(3000);

		WebElement Color5 = driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color5);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color6 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color6);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(3000);// (Hima)
	}

	public void Enable(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color0 = driver.findElement(By.xpath("(//*[@title='Edit'])[1]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color0);
		driver.findElement(By.xpath("(//*[@title='Edit'])[1]")).click();
		Thread.sleep(3000);

		WebElement Color1 = driver
				.findElement(By.xpath("//*[@formcontrolname='comments' or @formcontrolname='Comments']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//*[@formcontrolname='comments' or @formcontrolname='Comments']"))
				.sendKeys("Enable_Comment");
		Thread.sleep(3000);

		WebElement CheckBox = driver.findElement(By.xpath("//*[@type='checkbox']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", CheckBox);
		driver.findElement(By.xpath("//*[@type='checkbox']")).click();
		Thread.sleep(3000);

		WebElement Color2 = driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit'  or @ title='Click to update' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit'  or @ title='Click to update' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color3 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(3000);

		WebElement Color4 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty(Password));
		Thread.sleep(3000);

		WebElement Color5 = driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color5);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color6 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color6);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(3000);// (Hima)
	}

	public void Update(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color1 = driver
				.findElement(By.xpath("//*[@formcontrolname='comments' or @formcontrolname='Comments']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//*[@formcontrolname='comments' or @formcontrolname='Comments']"))
				.sendKeys("Update_Comment");
		Thread.sleep(1000);

		WebElement Color2 = driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit'  or @ title='Click to update' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit'  or @ title='Click to update' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(1000);

		WebElement Color3 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(1000);

		WebElement Color4 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty(Password));
		Thread.sleep(1000);

		WebElement Color5 = driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color5);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color6 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color6);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(1000);// (Hima)
	}

	public void Submit_E_Signature(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color2 = driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit'  or @ title='Click to update' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @ title='submit'  or @ title='Click to update' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color3 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(3000);

		WebElement Color4 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty(Password));
		Thread.sleep(3000);

		WebElement Color5 = driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color5);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color6 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color6);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(3000);// (Hima)
	}

	public void E_Signature(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color1 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(3000);

		WebElement Color2 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty(Password));
		Thread.sleep(3000);

		WebElement Color3 = driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit' or contains(text(),'Submit') or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color4 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(3000);
	}

	public void Save(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color1 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(3000);

		WebElement Color2 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty(Password));
		Thread.sleep(3000);

		WebElement Color3 = driver.findElement(By.xpath("//button[contains(.,'Submit')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("//button[contains(.,'Submit')]")).click();
		Thread.sleep(3000);

		WebElement Color4 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(3000);
	}

	public void E_Signature_Masters(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color1 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(3000);

		WebElement Color2 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty(Password));
		Thread.sleep(3000);

		WebElement Color3 = driver.findElement(By.xpath("//*[@type='submit']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("//*[@type='submit']")).click();
		Thread.sleep(3000);

		WebElement Color4 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(3000);
	}

	public void E_Signature1(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color1 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(3000);

		WebElement Color2 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty("Password"));
		Thread.sleep(3000);

		WebElement Color3 = driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit'  or contains(text(),'Save') or contains(text(),'Verify')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath(
				"//*[@type='submit' or @value='Log In' or @title='submit'  or contains(text(),'Save') or contains(text(),'Verify')]"))
				.click();
		Thread.sleep(3000);

		WebElement Color4 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(3000);
	}

	public void E_Signature2(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;

		WebElement Color1 = driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color1);
		driver.findElement(By.xpath("//button[contains(text(),'Yes') or contains(text(),'yes')]")).click();
		Thread.sleep(3000);

		WebElement Color2 = driver.findElement(By.xpath("//input[@type='password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys(pro.getProperty("Password"));
		Thread.sleep(3000);

		WebElement Color3 = driver.findElement(By.xpath("(//*[@type='submit'])[2]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color3);
		driver.findElement(By.xpath("(//*[@type='submit'])[2]")).click();
		Thread.sleep(3000);

		WebElement Color4 = driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color4);
		driver.findElement(By.xpath("//*[contains(text(),'OK') or contains(text(),'Ok')]")).click();
		Thread.sleep(3000);
	}

	public void scrollPagedownWithActions(WebElement element) throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.perform();
		Thread.sleep(3000);
	}

	@SuppressWarnings("unlikely-arg-type")
	public void handleWhitecardPopup(int copyNumberGiven) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the whitecard element to be visible
		// WebElement whitecard = wait
		// .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("div[class='modal-body']")));
		// Scroll down to the whitecard (if necessary)
		WebElement copyNumber = driver.findElement(By.xpath("//tbody/tr[4]/td[1]"));
		scrollPagedownWithActions(copyNumber);
		String copyNumberTest = copyNumber.getText().trim();
		System.out.print(copyNumberTest);
		// Assert the copy number
		Assert.assertEquals(copyNumberTest, String.valueOf(copyNumberGiven), "Copy number mismatch!");

		// Interact with the whitecard elements
		driver.findElement(By.xpath("//*[@class='close']")).click(); // Update the XPath
	}

	@SuppressWarnings("unlikely-arg-type")
	public void openedPrintFile(int copyNumberGiven) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Wait for the whitecard element to be visible
		// WebElement whitecard = wait
		// .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("div[class='modal-body']")));
		// Scroll down to the whitecard (if necessary)
		WebElement copyNumber = driver.findElement(By.xpath("//tbody/tr[4]/td[1]"));
		scrollPagedownWithActions(copyNumber);
		String copyNumberTest = copyNumber.getText().trim();
		System.out.print(copyNumberTest);
		// Assert the copy number
		Assert.assertEquals(copyNumberTest, String.valueOf(copyNumberGiven), "Copy number mismatch!");

		// Interact with the whitecard elements
		driver.findElement(By.xpath("//*[@class='close']")).click(); // Update the XPath
	}

	public boolean doesValueExistInTable(List<WebElement> viewData, String value) {
		for (WebElement cell : viewData) {
			if (cell.getText().equals(value)) {
				return true;
			}
		}
		return false;
	}
}
