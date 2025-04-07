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
import java.util.List;
import java.util.Properties;
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
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

@SuppressWarnings("deprecation")
public class BaseTest {
	public static WebDriver driver;
	ExtentSparkReporter Report;
	public static ExtentReports extent;
	public ExtentTest test;
	public Properties pro;
	public Robot r;
	public WebDriverWait wait;// globally declared

	@BeforeClass(alwaysRun = true)
	public void suiteSetUp() throws Exception {
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
			// Get the singleton instance of ExtentReports
			extent = getReportObject(this.getClass().getSimpleName());
			test = extent.createTest(this.getClass().getSimpleName());
		}

	public static ExtentReports getReportObject(String testName) {
		if (extent == null) { // Only initialize if extent is null
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd--HH.mm.ss").format(new Date());// time stamp
			String repName = testName + " Test-Report-" + timeStamp + ".html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(
					System.getProperty("user.dir") + "/Reports/" + repName);
			reporter.config().setEncoding("utf-8");
			reporter.config().setReportName("Automation Test Result");
			reporter.config().setTheme(Theme.STANDARD);
			reporter.config().setDocumentTitle("PWO 2.1"); // Tile of report
			reporter.config().setReportName("PWO 2.1"); // name of the report

			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Organization", "Audree Infotech Pvt Ltd");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("Tester", "Sharuk Komminapalli");
		}
		return extent;
	}

//	@BeforeSuite(alwaysRun = true)
	public void initializeExtentTest() {
		// Get the singleton instance of ExtentReports
		extent = getReportObject(this.getClass().getSimpleName());
		test = extent.createTest(this.getClass().getSimpleName());
	}

//	@AfterClass
//	public void tearDown() {
//		if (driver != null) {
//			driver.quit(); // better than close() for full cleanup
//		}
//	}

	@AfterSuite()
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
		boolean terminationMessage = driver.findElement(By.xpath("(//button[@id='BtnWApp'])[1]")).isDisplayed();
		if (terminationMessage) {
			driver.findElement(By.xpath("(//button[@id='BtnWApp'])[1]")).click();
			System.out.println(terminationMessage);
		}
		Thread.sleep(1500);
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

	public WebElement waitForWebElementToAppear(WebElement findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement isExistes = wait.until(ExpectedConditions.visibilityOf(findBy));
		return isExistes;
	}

	// ***************************************************************************************************
	public void scrollPagedown() throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(2000);
		//Added
		System.out.println("CI/CD-integration-Jenkins");
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
		// Selenium can handle most interactions with web elements in a browser.
		// However, it cannot interact directly with file upload dialogs because they
		// are part of the operating system, not the web page.
		// This is where Java's Robot class can be used to mimic user interactions like
		// file uploads by handling OS-level dialogs, which Selenium alone can't
		// manipulate.
		r = new Robot();
		r.delay(1500);
		// put path to file in a clipboard
		StringSelection s = new StringSelection(path);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		// ctrl+V press
		r.keyPress(KeyEvent.VK_CONTROL);// press on ctrl key+copy
		r.keyPress(KeyEvent.VK_V);// press on ctrl key+paste
		r.delay(1500);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
		r.delay(1000);
		// Enter
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		r.delay(500);
		System.out.println("uploaded Successfully");
	}

	public void AttachFile() throws Exception {
		WebElement Color = driver.findElement(By.xpath("//*[@type='file']"));
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 2px solid black;');", Color);
		WebElement AttachFile = driver.findElement(By.xpath("//*[@type='file']"));
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

	// *******************************************/

	public void Password(String Password) throws Exception {
		JavascriptExecutor Js = (JavascriptExecutor) driver;
		WebElement Color2 = driver
				.findElement(By.xpath("//*[@formcontrolname='password' or formcontrolname='Password']"));
		Js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 4px solid black;');", Color2);
		driver.findElement(By.xpath("//*[@formcontrolname='password' or formcontrolname='Password']"))
				.sendKeys(pro.getProperty(Password));
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

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

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}
}
