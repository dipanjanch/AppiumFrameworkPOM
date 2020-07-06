package pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.offset.PointOption;
import utility.LogClass;

/**
 * This class contains all the important reusable methods
 * 
 * @author Dipanjan
 *
 */
public class BasePage {

	public static AppiumDriverLocalService service;
	public static AndroidDriver<AndroidElement> driver;
	public static ExtentReports report;
	public static ExtentTest reporter;
	public static Properties property;
	public static String screenshot;

	/**
	 * This method launches the server and initializes the driver.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void initialization() throws IOException, InterruptedException {
		initializeReporter();
		LogClass.info("Starting server");
		startAppiumServer();
		LogClass.startTestCase(this.getClass().getSimpleName());
		driver = getDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * This method and quits the driver and ends the session.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void tearDownMethod() throws InterruptedException, IOException {
		LogClass.endTestCase(this.getClass().getSimpleName());
		driver.quit();
		LogClass.info("Driver has been destroyed");
		stopAppiumServer();
	}

	/**
	 * The StartServer method is use for starting the server.
	 */
	public static void startAppiumServer() {
		String startServerFromCode = (String) property.get("startServerFromCode");
		if (startServerFromCode.equalsIgnoreCase("yes")) {
			service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
					.withLogFile(new File(System.getProperty("user.dir") + "/src/test/resources/logs/logResult.txt"))
					.withArgument(GeneralServerFlag.LOCAL_TIMEZONE));
			service.start();
		}
	}

	/**
	 * The method is use for stopping the server.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void stopAppiumServer() throws InterruptedException, IOException {
		String startServerFromCode = (String) property.get("startServerFromCode");
		if (startServerFromCode.equalsIgnoreCase("yes")) {
			LogClass.info("Stopping server");
			service.stop();
			LogClass.info("Server stopped");
		}
	}

	/**
	 * This method returns the the driver after creating the driver using the
	 * desired capabilities
	 * 
	 * @return driver object after creating the session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static AndroidDriver<AndroidElement> getDriver() throws IOException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String device = (String) property.get("device");
		String UDID = (String) property.get("UDID");
		String appPackage = (String) property.get("AppPackage");
		String appActivity = (String) property.get("AppActivity");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("udid", UDID);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 14);
		capabilities.setCapability("appPackage", appPackage);
		capabilities.setCapability("appActivity", appActivity);
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	/**
	 * This method sets ups the logger and the extent reporter
	 * 
	 * @throws IOException
	 */
	public void initializeReporter() throws IOException {
		property = new Properties();
		FileInputStream fileStream = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/resources/global.properties");
		property.load(fileStream);
		ExtentHtmlReporter extent = new ExtentHtmlReporter(
				new File(System.getProperty("user.dir") + "/Reports/addTocart" + getCurrentDateAndTime() + ".html"));
		report = new ExtentReports();
		report.attachReporter(extent);
		LogClass.info("Extent Report initialized");
	}

	/**
	 * This method is used to start reporting in the extent report
	 * 
	 * @param testName : The name of the test method
	 */
	public void startTestCase(String testName) {
		reporter = report.createTest(testName);
		reporter.info("Strat Testcase " + testName);
	}

	/**
	 * This method is used to get a base64 screenshot and attach it to the extent
	 * report
	 * 
	 * @throws IOException
	 */
	public static void attachScreenshotToReport() throws IOException {
		String path = System.getProperty("user.dir") + "/Screenshots/" + getCurrentDateAndTime() + ".png";
		File scrfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrfile, new File(path));
		screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		reporter.log(Status.INFO, "Snapshot below: ",
				MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());

	}

	/**
	 * This method gets the current time
	 * 
	 * @return String containing the current time
	 */
	public static String getCurrentDateAndTime() {
		DateFormat customFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		Date currentDate = new Date();
		return customFormat.format(currentDate);
	}

	/**
	 * This method is used to scroll to the given text
	 * 
	 * @param text text to be scrolled up
	 */
	public void scrollToText(String text) {
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));");
	}

	/**
	 * Method to perform click on a element
	 * 
	 * @param element            : will define WebElement value.
	 * @param elementDescription : the element description.
	 * @throws InterruptedException
	 */
	public void clickElement(WebElement element, String elementDescription) throws InterruptedException {
		try {
			element.click();
			LogClass.info("Element " + elementDescription + "  clicked");
			reporter.pass("Element " + elementDescription + "  clicked");
		} catch (NoSuchElementException e) {
			LogClass.info("Element NOT present - " + element);
			Assert.fail("Elelemnt " + element + " could not be clicked because - " + e);
		} catch (StaleElementReferenceException e1) {
			LogClass.info("Element  " + element + "   NOT present");
			Assert.fail("Elelemnt " + element + " could not be clicked because - " + e1);
		}
	}

	/**
	 * Method to set the value in the text box based on locator
	 * 
	 * @param element            : WebElement element to pass the locator.
	 * @param value              : value to type in Mobile element.
	 * @param elementDescription : the element description.
	 */
	public void setValueToField(WebElement element, String value, String elementDescription) {
		try {
			element.clear();
			element.sendKeys(value);
			LogClass.info("Entered the text" + value + "in the field " + elementDescription);
			reporter.pass("Entered the text" + value + "in the field " + elementDescription);
		} catch (NoSuchElementException e) {
			LogClass.info("Element NOT present - " + elementDescription);
			Assert.fail("Value could not be set in element - " + element + "   because - " + e);
		} catch (StaleElementReferenceException e1) {
			LogClass.info("Element NOT present");
			Assert.fail("Value could not be set in element - " + element + "  because - " + e1);
		} catch (Exception e2) {
			LogClass.info("Value could not be set because of error:  " + e2);
			Assert.fail("Value could not be set in element - " + element + "  because - " + e2);

		}
	}

	/**
	 * Method to check element displayed
	 * 
	 * @param element : WebElement element to pass the locator
	 */
	public boolean isElementDisplayed(WebElement element) {
		waitForElementPresent(element);
		return element.isDisplayed();
	}

	/**
	 * Method to check element presence using explicit wait condition
	 * 
	 * @param element - WebElement element to pass the locator
	 */
	public void waitForElementPresent(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Method to change the screen orientation
	 * 
	 * @param orientation
	 */
	public void verifyScreenRotation(ScreenOrientation orientation) {
		((AppiumDriver) driver).rotate(orientation);
		LogClass.info("Screen Orientation changed to " + orientation);
		reporter.pass("Screen Orientation changed to " + orientation);
	}

	/**
	 * Method to get the text based on the element
	 * 
	 * @param element : WebElement element to pass the locator
	 */
	public String getText(WebElement element) {
		LogClass.info("Getting text from the field " + element);
		return element.getText().trim();
	}

	/**
	 * This method is used to click the center of the screen
	 */
	public void clickCentreOfScreen() {
		Dimension dimension = driver.manage().window().getSize();
		int centreX = (int) (dimension.getWidth() * 0.5);
		int centreY = (int) (dimension.getHeight() * 0.5);
		TouchAction touchAction = new TouchAction(driver);
		touchAction.tap(PointOption.point(centreX, centreY)).perform();
	}

	/**
	 * @param ele  : WebElement element to pass the locator
	 * @param text : will define string value
	 * @return : will return the result in boolean.
	 */
	public boolean verifyValueInField(WebElement ele, String text) {
		boolean result = false;
		result = ele.getText().equalsIgnoreCase(text) ? true : false;
		return result;
	}

	/**
	 * This method is used to click the enter key
	 */
	public void clickEnterKey() {
		((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
		LogClass.info("Performed click on enter key");
	}

}
