package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
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
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
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
import pageObjects.loginPage;
import pageObjects.productDetailPage;
import pageObjects.homePage;

/**
 * the baseFunctionalities class contains all the important functions for all
 * testcases
 * 
 * @author Dipanjan
 *
 */
public class basePage {

	public static AppiumDriverLocalService service;
	public static AndroidDriver<AndroidElement> driver;
	public static ExtentReports report;
	public static ExtentTest reporter;
	public static Properties prop;
	public static String base64String;

	/**
	 * This method launches the server and initializes the driver.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void initialization() throws IOException, InterruptedException {
		setupReporters();
		log.info("Starting server");
		startServer();
		log.startTestCase(this.getClass().getSimpleName());
		driver = capabilities();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	/**
	 * This method and quits the driver and ends the session.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void tearDown() throws InterruptedException, IOException {
		log.endTestCase(this.getClass().getSimpleName());
		driver.quit();
		log.info("Driver has been destroyed");
		stopServer();
	}

	/**
	 * The StartServer method is use for starting the server.
	 */
	public static void startServer() {
		String startServerFromCode = (String) prop.get("startServerFromCode");
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
	public void stopServer() throws InterruptedException, IOException {
		String startServerFromCode = (String) prop.get("startServerFromCode");
		if (startServerFromCode.equalsIgnoreCase("yes")) {
			log.info("Stopping server");
			service.stop();
			log.info("Server stopped");
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
	public static AndroidDriver<AndroidElement> capabilities() throws IOException, InterruptedException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String device = (String) prop.get("device");
		String UDID = (String) prop.get("UDID");
		String appPackage = (String) prop.get("AppPackage");
		String appActivity = (String) prop.get("AppActivity");
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
	public void setupReporters() throws IOException {
		prop = new Properties();
		FileInputStream fs = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/resources/global.properties");
		prop.load(fs);
		ExtentHtmlReporter extent = new ExtentHtmlReporter(
				new File(System.getProperty("user.dir") + "/Reports/addTocart" + getCurrentDateTime() + ".html"));
		report = new ExtentReports();
		report.attachReporter(extent);
		log.info("Extent Report initialized");
	}

	/**
	 * This method is used to capture screenshot and store it in Screenshots folder
	 * 
	 * @param driver object of the mobile driver
	 * @return
	 * @throws IOException
	 */
	public static String getScreenshot() throws IOException {
		String path = System.getProperty("user.dir") + "/Screenshots/" + getCurrentDateTime() + ".png";
		File scrfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrfile, new File(path));
		return path;

	}

	/**
	 * This method is used to get a base64 screenshot and attach it to the extent
	 * report
	 * 
	 * @param driver object of the mobile driver
	 * 
	 * @throws IOException
	 */
	public static void attachScreenshottoReport() throws IOException {
		base64String = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
		reporter.log(Status.INFO, "Snapshot below: ",
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64String).build());

	}

	/**
	 * This method gets the current time
	 * 
	 * @return String containing the current time
	 */
	public static String getCurrentDateTime() {
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
	 * 
	 * @param ele : will define WebElement value.
	 * @param elementDescription : the element description.
	 * @throws InterruptedException
	 */
	public void clickElement(WebElement ele, String elementDescription) throws InterruptedException {
		try {
			ele.click();
			log.info("Element " + elementDescription + "  clicked");
			reporter.pass("Element " + elementDescription + "  clicked");
		} catch (NoSuchElementException e) {
			log.info("Element NOT present - " + ele);
			Assert.fail("Elelemnt " + ele + " could not be clicked because - " + e);
		} catch (StaleElementReferenceException e1) {
			log.info("Element  " + ele + "   NOT present");
			Assert.fail("Elelemnt " + ele + " could not be clicked because - " + e1);
		}
	}

	/**
	 * Method to set the value in the text box based on locator
	 * 
	 * @param ele : WebElement element to pass the locator.
	 * @param val : value to type in Mobile element.
	 * @param elementDescription : the element description.
	 */
	public void setValueToField(WebElement ele, String val, String elementDescription) {
		try {
			ele.clear();
			ele.sendKeys(val);
			log.info("Entered the text" + val + "in the field " + elementDescription);
			reporter.pass("Entered the text" + val + "in the field " + elementDescription);
		} catch (NoSuchElementException e) {
			log.info("Element NOT present - " + elementDescription);
			Assert.fail("Value could not be set in element - " + ele + "   because - " + e);
		} catch (StaleElementReferenceException e1) {
			log.info("Element NOT present");
			Assert.fail("Value could not be set in element - " + ele + "  because - " + e1);
		} catch (Exception e2) {
			log.info("Value could not be set because of error:  " + e2);
			Assert.fail("Value could not be set in element - " + ele + "  because - " + e2);

		}
	}

	/**
	 * Method to check element displayed
	 * 
	 * @param ele - WebElement element to pass the locator
	 */
	public boolean isElementDisplayed(WebElement ele) {
		waitForElementPresence(ele);
		return ele.isDisplayed();
	}

	/**
	 * Method to check element presence using explicit wait condition
	 * 
	 * @param ele - WebElement element to pass the locator
	 */
	public void waitForElementPresence(WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	/**
	 * This method is used to change the screen orientation
	 * 
	 * @param orientation
	 */
	public void screenRotate(ScreenOrientation orientation) {
		((AppiumDriver) driver).rotate(orientation);
		log.info("Screen Orientation changed to " + orientation);
		reporter.pass("Screen Orientation changed to " + orientation);
	}

	/**
	 * The Method is will scroll the page until element will display
	 * 
	 * @param ele WebElement element to pass the locator
	 * @return : it will return the webelement
	 * @throws InterruptedException
	 */
	public AndroidElement scrollToElement(WebElement ele) throws InterruptedException {
		AndroidElement element = null;
		int numberOfTimes = 4;
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.width / 2);
		// Swipe up to scroll down
		int startPoint = (int) (size.height - 10);
		int endPoint = 10;

		for (int i = 0; i < numberOfTimes; i++) {
			try {
				if (ele.isDisplayed()) {
					i = numberOfTimes;
				} else
					log.info(String.format("Element not available. Scrolling times…", i + 1));
				new TouchAction(driver).longPress(PointOption.point(anchor, startPoint))
						.moveTo(PointOption.point(anchor, endPoint)).release().perform();

			} catch (Exception ex) {
				log.info(String.format("Element not available. Scrolling times…", i + 1));
			}
		}

		return (AndroidElement) ele;
	}

	/**
	 * Method to get the text based on the element
	 * 
	 * @param ele WebElement element to pass the locator
	 */
	public String getText(WebElement ele) {
		log.info("Getting text from the field " + ele);
		return ele.getText().trim();
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
	public boolean verifyingPage(WebElement ele, String text) {
		boolean result = false;
		result = ele.getText().equalsIgnoreCase(text) ? true : false;
		return result;
	}

	/**
	 * This method is used to click the enter key
	 */
	public void clickEnterKey() {
		((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
		log.info("Performed click on enter key");
	}

}
