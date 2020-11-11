	package testClassCreatePaylink;

	import java.io.File;
	import java.io.IOException;
	import java.sql.DriverManager;
	import java.util.Calendar;
	import java.util.GregorianCalendar;

	import org.apache.commons.io.FileUtils;
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	import org.openqa.selenium.WebDriver;
	import org.testng.ITestContext;
	import org.testng.ITestListener;
	import org.testng.ITestResult;
	//import io.qameta.allure.Attachment;

import io.qameta.allure.Allure;


	public class testListener implements ITestListener {




	@Override
	public void onTestStart(ITestResult result) {
	// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {
	try {
	Allure.getLifecycle().addAttachment(result.getName(), "image/png", "png", saveScreenshot());
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	}

	@Override
	public void onTestFailure(ITestResult result) {
	try {
	Allure.getLifecycle().addAttachment(result.getName(), "image/png", "png", saveScreenshot());
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}
	}

	
	@Override
	public void onTestSkipped(ITestResult result) {
	// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
	// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
	// TODO Auto-generated method stub

	}
	public byte[] saveScreenshot() throws IOException {
		System.out.println(baseClass.driver+"--Log ");
		return ((TakesScreenshot)baseClass.driver).getScreenshotAs(OutputType.BYTES);
		}
	
	
	
	
	}

