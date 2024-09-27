//package com.audree.infotech.pwo2.testcomponents;
//
//import java.io.IOException;
//
//import org.openqa.selenium.WebDriver;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.Status;
//
//public class Listeners extends BaseTest implements ITestListener {
//	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); // Thread-safe ExtentTest instance
//
//	@Override
//	public void onStart(ITestContext context) {
//		synchronized (Listeners.class) {
//			// Initialize ExtentReports object
//			extent = BaseTest.getReportObject(context.getName());
//			if (extent == null) {
//				System.out.println("ExtentReports object is null!");
//			} else {
//				System.out.println("ExtentReports object initialized successfully.");
//			}
//		}
//	}
//
//	@Override
//	public void onTestStart(ITestResult result) {
//		test = extent.createTest(result.getMethod().getMethodName());
//		extentTest.set(test);
//		System.out.println("Test Started: " + result.getMethod().getMethodName());
//	}
//
//	@Override
//	public void onTestSuccess(ITestResult result) {
//		extentTest.get().log(Status.PASS, "Test Passed");
//		System.out.println("Test Passed: " + result.getMethod().getMethodName());
//	}
//
//	@Override
//	public void onTestFailure(ITestResult result) {
//		extentTest.get().fail(result.getThrowable());
//		System.out.println("Test Failed: " + result.getMethod().getMethodName());
//
//		try {
//			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//
//		String filePath = null;
//		try {
//			filePath = getScreenshot(driver, result.getMethod().getMethodName());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onTestSkipped(ITestResult result) {
//		extentTest.get().log(Status.SKIP, "Test Skipped");
//		System.out.println("Test Skipped: " + result.getMethod().getMethodName());
//	}
//
//	@Override
//	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//		// Implement if needed
//	}
//
//	@Override
//	public void onFinish(ITestContext context) {
//		synchronized (Listeners.class) {
//			extent.flush();
//			System.out.println("Test Suite Finished: " + context.getName());
//		}
//	}
//}
