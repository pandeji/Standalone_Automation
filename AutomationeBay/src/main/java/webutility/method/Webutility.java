package webutility.method;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Webutility {

	private static Webutility wu;
	private Properties prop;
	private ExtentReports extReport;
	Random random;
	protected static ThreadLocal<ExtentTest> thextTest = new ThreadLocal<>();
	WebDriver driver = new ChromeDriver();

	public static Webutility getObject() {
		if (wu == null) {
			wu = new Webutility();
		}
		return wu;
	}
	
	@SuppressWarnings("deprecation")
	public WebDriver initializeBrowser() {
	    if (driver == null) {  // Only initialize if driver is null
	        String browser = loaderConfigFile().getProperty("browser");
	        if (browser.equalsIgnoreCase("chrome")) {
	            driver = new ChromeDriver();
	        } else if (browser.equalsIgnoreCase("firefox")) {
	            driver = new FirefoxDriver();
	        } else if (browser.equalsIgnoreCase("edge")) {
	            driver = new EdgeDriver();
	        } else {
	            throw new RuntimeException("Browser not supported");
	        }
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    }
	    return driver;
	}
	
	public void maximizeBrowser() {
        if (driver == null) {
            initializeBrowser();  // Ensure the driver is initialized
        }
        driver.manage().window().maximize();
    }
	
	public void minimizeRowser() {
		driver.manage().window().minimize();
	}
	

	public WebDriver getDriver() {
		return driver;
	}

	public void openUrl(String url) {
		driver.get(url);
	}

	public void quiteBrowser() {
		driver.quit();
	}

	public void closeBrowser() {
		driver.close();
	}

	public void backPage() {
		driver.navigate().back();
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}
	
	public String getUrl() {
		return driver.getCurrentUrl();
	}

	public void jsScrollOnElement(WebElement webEle) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", webEle);
	}

	public void jsScrollByValue(String value) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript(value);
	}

	public void jsScrollBottom() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public void jsScrollTop() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0,-document.body.scrollHeight)");
	}

	public void deleteValue(WebElement webEle) {
		webEle.sendKeys(Keys.CONTROL + "a" + Keys.DELETE, "Select successfully all text and delete");
	}

	public void iframe(WebElement webEle) {
		driver.switchTo().frame(webEle);
	}

	public void handleFrame() {
		driver.switchTo().defaultContent();
	}

	public void printExtentTestInfoMsg(String msg) {
		thextTest.get().log(Status.INFO, msg);
	}

	public void printExtentTestPassMsg(String msg) {
		thextTest.get().log(Status.PASS, msg);
	}

	public void printExtentTestFailedMsg(String msg) {
		thextTest.get().log(Status.FAIL, msg);
	}

	public void elementIsPresent(WebElement webEle) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.visibilityOf(webEle));
		} catch (StaleElementReferenceException e) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.visibilityOf(webEle));
		}
	}

	public void elementIsClickable(WebElement webEle) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(webEle));
		} catch (StaleElementReferenceException e) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(webEle));
		}
	}

	public void actionDragAndDrop(WebElement srcElement, WebElement targetElement, String msg) {
		Actions act = new Actions(driver);
		act.moveToElement(srcElement).pause(Duration.ofSeconds(1)).clickAndHold(srcElement).pause(Duration.ofSeconds(1))
				.moveByOffset(1, 0).moveToElement(targetElement).moveByOffset(1, 0).pause(Duration.ofSeconds(1))
				.release().perform();
		thextTest.get().log(Status.INFO, msg);
	}

	public void actionDragAndDrop1(WebElement srcElement, WebElement targetElement, String msg) {
		Actions act = new Actions(driver);
		act.moveToElement(srcElement).pause(Duration.ofSeconds(1)).clickAndHold(srcElement).pause(Duration.ofSeconds(1))
				.moveByOffset(1, 0).moveToElement(targetElement).moveByOffset(1, 20).pause(Duration.ofSeconds(1))
				.release().perform();
		thextTest.get().log(Status.INFO, msg);
	}

	public void sendkeys(WebElement webEle, String inputvalue, String msg) {
		try {
			webEle.sendKeys(inputvalue);
		} catch (ElementNotInteractableException | StaleElementReferenceException e) {
			webEle.clear();
			webEle.sendKeys(inputvalue);
		}
//		thextTest.get().log(Status.INFO, msg);
	}

	public void actionsSendkeys(WebElement webEle, String inputvalue, String msg) {
		try {
			Actions actions = new Actions(wu.getDriver());
			actions.moveToElement(webEle).click().sendKeys(inputvalue).build().perform();
		} catch (ElementNotInteractableException | StaleElementReferenceException e) {
			Actions actions = new Actions(wu.getDriver());
			actions.moveToElement(webEle).click().sendKeys(inputvalue).build().perform();
		}
//		thextTest.get().log(Status.INFO, msg);
	}

	public void click(WebElement webEle, String msg) {
		try {
			webEle.click();
		} catch (ElementClickInterceptedException | StaleElementReferenceException e) {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", webEle);
		}
//		thextTest.get().log(Status.INFO, msg);
	}

	public void actionClick(WebElement element, String msg) {
		try {
			new Actions(driver).moveToElement(element).build().perform();
			holdOn(3);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			holdOn(5);
			new Actions(driver).moveToElement(element).click().build().perform();
//			thextTest.get().log(Status.INFO, msg);
		} catch (StaleElementReferenceException e) {
			new Actions(driver).moveToElement(element).build().perform();
			holdOn(3);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			holdOn(5);
			new Actions(driver).moveToElement(element).click().build().perform();
//			thextTest.get().log(Status.INFO, msg);
		}
	}

	public void actionMoveToElement(WebElement wele, String msg) {
		Actions act = new Actions(driver);
		act.moveToElement(wele).build().perform();
		thextTest.get().log(Status.INFO, msg);
	}

	public void jsClick(WebElement element, String msg) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		thextTest.get().log(Status.INFO, msg);
	}

	public void selectByVisibleText(WebElement webEle, String selectText, String msg) {
		try {
			Select selobj = new Select(webEle);
			selobj.selectByVisibleText(selectText);
		} catch (StaleElementReferenceException e) {
			Select selobj = new Select(webEle);
			selobj.selectByVisibleText(selectText);
		}
		thextTest.get().log(Status.INFO, msg);
	}

	public void selectByIndex(WebElement webEle, int index, String msg) {
		try {
			Select selobj = new Select(webEle);
			selobj.selectByIndex(index);
		} catch (StaleElementReferenceException e) {
			Select selobj = new Select(webEle);
			selobj.selectByIndex(index);
		}
		thextTest.get().log(Status.INFO, msg);
	}

	public void verifyTitle(String expectedTitle) {
		String actualTitle = driver.getTitle();
		if (actualTitle.contains(expectedTitle)) {
			thextTest.get().log(Status.PASS,
					" Where Actual Title is :- " + actualTitle + " & Expected Title is :- " + expectedTitle);
		} else {
			thextTest.get().log(Status.FAIL,
					" Where Actual Title is :- " + actualTitle + " & Expected Title is :- " + expectedTitle);
		}
	}

	public String verifyFirstSelectedDropDownValue(WebElement webEle) {
		Select select = new Select(webEle);
		WebElement option = select.getFirstSelectedOption();
		return option.getText();
	}

	public void verifyDropdownValues(WebElement webEle, String msg) {
		Select select = new Select(webEle);
		List<WebElement> options = select.getOptions();
		for (WebElement we : options) {
			thextTest.get().log(Status.PASS, we.getText() + msg);
		}
	}

	public void holdOn(long miles) {
		try {
			Thread.sleep(miles * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	public Properties loaderConfigFile() {
		prop = new Properties();
		try (FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "/PropertiesFile/config.properties")) {
			prop.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public String timeStamp() {
		SimpleDateFormat sft = new SimpleDateFormat("dd-MM-yyyy hh_mm_ss ");
		return sft.format(new Date());
	}

	public String localDate() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		return myDateObj.format(date);
	}

	public void extReport(String reportResultName) {
		ExtentSparkReporter exthtmlreport = new ExtentSparkReporter(reportResultName);
		exthtmlreport.config().setReportName("Editor Regression Automation Reports");
		exthtmlreport.config().setDocumentTitle("Extent Reports");
		extReport = new ExtentReports();
		extReport.attachReporter(exthtmlreport);
		extReport.setSystemInfo("username", System.getProperty("user.name"));
		extReport.setSystemInfo("OS", System.getProperty("user.os"));
		extReport.setSystemInfo("Envoirment", "QA");
	}

	public void setExtentLogger(String testCaseName, String clName) {
		thextTest.set(extReport.createTest(testCaseName));
		thextTest.get().assignCategory(clName);
	}

	public ExtentTest getLogger() {
		return thextTest.get();
	}

	public void flushReport() {
		extReport.flush();
	}

	public String takeSnapShot(String snapshotname) {
		TakesScreenshot ts = (TakesScreenshot) getDriver();
		File source = ts.getScreenshotAs(OutputType.FILE);
		String time = timeStamp();
		String date = localDate();
		String destinationFile = System.getProperty("user.dir") + "/Report/snap/" + date + "/" + snapshotname + time
				+ ".png";
		try {
			FileUtils.copyFile(source, new File(destinationFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destinationFile;
	}

	public void snapShotCaptureReportattach(String imgPath) {
		try {
			thextTest.get().addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getWindowHandleByTitle(String expTitlevalue, String msg) {
		Set<String> allWindowValue = driver.getWindowHandles();
		for (String AllValue : allWindowValue) {
			driver.switchTo().window(AllValue);
			String getTitleValue = driver.getTitle();
			if (getTitleValue.contains(expTitlevalue)) {
				break;
			}
		}
		thextTest.get().log(Status.INFO, msg);
	}

	public void switchFocusOnNewWindow() {
		String currentHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(currentHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}

	public void getWindowHandleByUrl(String expUrlValue, String msg) {
		Set<String> allwindowvalue = driver.getWindowHandles();
		for (String AllValue : allwindowvalue) {
			driver.switchTo().window(AllValue);
			String getTitleValue = driver.getCurrentUrl();
			if (getTitleValue.contains(expUrlValue)) {
				break;
			}
		}
		thextTest.get().log(Status.INFO, msg);
	}

	public String getRandomNameWithSpecialChar(int count) {
		random = new Random();
		char[] choices = ("abc-#$%" + "abc-#$%ri%^fd" + "0-#$%" + "%^").toCharArray();
		StringBuilder salt = new StringBuilder(count);
		for (int i = 0; i < count; ++i)
			salt.append(choices[random.nextInt(choices.length)]);
		return salt.toString();
	}

	public String getrandomAlphabeticName(int count) {
		return RandomStringUtils.randomAlphabetic(count);
	}

	public String getrandomNameInt(int count) {
		char[] chars = "0123456789".toCharArray();
		StringBuilder sb = new StringBuilder(count);
		random = new Random();
		for (int i = 0; i < count; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

	public void verifyElementVisible(WebElement weEle, String msg) {
		try {
			boolean weStatus = weEle.isDisplayed();
			Dimension dim = weEle.getSize();
			int height = dim.getHeight();
			int width = dim.getWidth();
			if (weStatus && height > 0 && width > 0) {
				thextTest.get().log(Status.PASS, msg);
			} else {
				thextTest.get().log(Status.FAIL, "Element is Invisible");
			}
		} catch (Exception e) {
			e.printStackTrace();
//			thextTest.get().log(Status.FAIL, e.getMessage());
		}
	}

	public void verifyElementListVisible(List<WebElement> listWebele, String msg) {
		try {
			for (WebElement weEle : listWebele) {
				boolean weStatus = weEle.isDisplayed();
				Dimension dim = weEle.getSize();
				int height = dim.getHeight();
				int width = dim.getWidth();
				if (weStatus && height > 0 && width > 0) {
					thextTest.get().log(Status.PASS, msg);
				} else
					thextTest.get().log(Status.FAIL, "Element is not visible");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyElementVisibleGetText(WebElement weEle, String msg) {
		try {
			boolean weStatus = weEle.isDisplayed();
			Dimension dim = weEle.getSize();
			int height = dim.getHeight();
			int width = dim.getWidth();
			if (weStatus && height > 0 && width > 0) {
				thextTest.get().log(Status.PASS, weEle.getText() + " " + msg);
			} else {
				thextTest.get().log(Status.FAIL, "Element is Invisible");
			}
		} catch (Exception e) {
			e.printStackTrace();
			thextTest.get().log(Status.FAIL, e);
		}
	}

	public void verifyElementVisibleListGetText(List<WebElement> listWebele, String msg) {
		try {
			for (WebElement weEle : listWebele) {
				boolean weStatus = weEle.isDisplayed();
				Dimension dim = weEle.getSize();
				int height = dim.getHeight();
				int width = dim.getWidth();
				if (weStatus && height > 0 && width > 0) {
					thextTest.get().log(Status.PASS, weEle.getText() + " " + msg);
				} else
					thextTest.get().log(Status.FAIL, "Element is not visible");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String extractWordAfterACharacter(String str, String character, int position) {
		String[] arr = str.split(character);
		return arr[position];
	}

	public String subStringBetween(String inputString, String startDelimiter, String endDelimiter) {
		return StringUtils.substringBetween(inputString, startDelimiter, endDelimiter);
	}

	public String substringBeforeLast(String url, String remove) {
		return (StringUtils.substringBeforeLast(url, remove));
	}

	public String getCharacter(String url, String remove) {
		return (StringUtils.substringBeforeLast(url, remove));
	}

	public List<HashMap<String, String>> getJsonDataToHashmap(String jsonFileName) throws IOException {
		String jsonContent = FileUtils.readFileToString(
				new File(System.getProperty("user.dir") + "/resources/" + jsonFileName + ".json"),
				StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
		});
	}

	public String readJsonKeyValue(String json, String keyName) {
		String[] valueArr = json.split(",");
		String keyValue = "";
		for (int i = 0; i < valueArr.length; i++) {
			if (valueArr[i].contains(keyName)) {
				String p = valueArr[i];
				String[] priceValue = p.split(":");
				keyValue = priceValue[1].replace('"', ' ').replace(".00", " ").trim();
				break;
			}
		}
		return keyValue;
	}

	/**
	 * Pick up files from our local system
	 * 
	 * @param path
	 */
	public void robotMethod(String path) {
		try {
			StringSelection ss = new StringSelection(System.getProperty("user.dir") + path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			Robot robot = new Robot();
			robot.delay(250);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(90);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public String dateInMM_DD_YY(int days) {
		LocalDate myObj = LocalDate.now();
		String str = myObj.toString();
		String tomorrow = LocalDate.parse(str).plusDays(days).toString();
		String[] arr = tomorrow.split("-");
		String year = arr[0];
		String month = arr[1];
		String day = arr[2];
		return day + "/" + month + "/" + year;
	}
}
