package webutility.method;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Baseclass {

	protected static Webutility wu = Webutility.getObject();
	WebDriver driver;

	@BeforeSuite
	public void tcOpenApp() {
		wu.loaderConfigFile();
		String date = wu.localDate();
		String time = wu.timeStamp();
		wu.extReport("Report/" + date + "/" + time + "_" + "editorReports.html");
		
	}

	@BeforeMethod
	public void loginPageMethod(Method method) {
		String clName = getClass().getName();
		String tcName = method.getName();
		wu.getDriver();
		driver = wu.initializeBrowser();
		wu.maximizeBrowser();
		String browserName = wu.loaderConfigFile().getProperty("browser");
		wu.setExtentLogger(browserName + "_" + tcName, browserName + "_" + clName);
	}

	@AfterMethod
	public void tclogOut(ITestResult result, Method method) {
		int status = result.getStatus();
		if (status == ITestResult.FAILURE) {
			String tcName = method.getName();
			String imgPath = wu.takeSnapShot(tcName);
			wu.snapShotCaptureReportattach(imgPath);
			wu.getLogger().log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
			wu.getLogger().log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
		}
		if (driver != null) {
	        driver.quit();
	        driver = null;  // Set driver to null to ensure reinitialization in the next test
	    }
	}

	@AfterClass(enabled = true)
	public void closeBrowser() {
		wu.quiteBrowser();
	}

	@AfterSuite
	public void closeApplication() {
		wu.flushReport();
	}
}
