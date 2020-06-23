package utility;

import java.io.File;
import java.io.FileInputStream;
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
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
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
 * the baseFunctionalities class contains all the important functions  for all testcases 
 * @author Dipanjan
 *
 */
public class baseFunctionalities {

	 public static AppiumDriverLocalService service;
	 public static AndroidDriver<AndroidElement>  driver;
	 public static ExtentReports report;
	 public static ExtentTest logger;
	 public loginPage login;
	 public homePage home;
	 public productDetailPage prodDetailPage;
	 public utilities utils;
	 WebDriverWait wait;
	 public static Properties prop;

	 public baseFunctionalities() throws IOException {
			prop = new Properties();
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/resources/global.properties");
			prop.load(fs);
		}
	 /**
	  * This is the Before Suite method which runs first and sets up the Extent report
	  */
	 @BeforeSuite
	 public void setupSuite()
	 {
		 ExtentHtmlReporter extent = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"/Reports/addTocart"+getCurrentDateTime()+".html"));
		 report = new ExtentReports();
		 report.attachReporter(extent);
		 log.info("Extent Report initialized");
		 log.info("Starting server");
		 startServer();
		
		 
	 }
	 
	 /**
	  * This is the After Suite method which runs at the end of the suite and deletes the session
	  * @throws InterruptedException
	  * @throws IOException
	  */
	 @AfterSuite
	 public void endSuite() throws InterruptedException, IOException 
	 {
		 endSession();
	 }
	 
	 
	 /**
	  * This is the Before class method and it launches the driver and initializes all the page objects
	  * @throws IOException
	  * @throws InterruptedException
	  */
	 @BeforeClass
	 public void setUpDriver() throws IOException, InterruptedException
	 {
		 log.startTestCase(this.getClass().getSimpleName());
		 driver=capabilities();		
		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 login = new loginPage(driver);
		 home = new homePage(driver);
		 prodDetailPage = new productDetailPage(driver);
		 utils = new utilities(driver);
		 log.info("Driver has been created");
		 wait = new WebDriverWait(driver, 30);
	 }
	 
	 /**
	  * This is the After class method and quits the driver after the test case is over
	  */
	 @AfterClass
	 public void tearDown()
	 {
		 log.endTestCase(this.getClass().getSimpleName());
		 driver.quit();
		 log.info("Driver has been destroyed");
	 }
	 
	 /**
	  * This is the After method function which runs after each test case is executed and attaches the screenshot if test case fails
	  * @param result the current testcase status
	  * @throws IOException
	  */
	 @AfterMethod
	 public void tearDown(ITestResult result) throws IOException
	 {
		 if(result.getStatus()==ITestResult.FAILURE) {
			 logger.fail("Test Failed ", MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot(driver)).build());
		 }
		 report.flush();
	 }
	 
	 
	 /**
	  * The StartServer method is use for starting the server.
	  */
	public static void startServer() {
		String startServerFromCode = (String) prop.get("startServerFromCode");
		if(startServerFromCode.equalsIgnoreCase("yes")) {
		service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.withLogFile(new File(System.getProperty("user.dir") + "/src/test/resources/logs/logResult.txt"))
				.withArgument(GeneralServerFlag.LOCAL_TIMEZONE));
		service.start();
		}
		}
	
	/**
	 * The method is use for stopping the server and closing the driver.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void endSession() throws InterruptedException, IOException {
		String startServerFromCode = (String) prop.get("startServerFromCode");
		if(startServerFromCode.equalsIgnoreCase("yes"))
		{
		log.info("Stopping server");
		service.stop();
		log.info("Server stopped");
		}
	}
	 
	/**
	 * This method returns the the driver after creating the driver using the derired capabilities
 	 * @return driver object after creating the session
 	 * @throws IOException
 	 * @throws InterruptedException
 	 */
	public static AndroidDriver<AndroidElement> capabilities() throws IOException, InterruptedException
	{
			
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String device=(String) prop.get("device");
		String UDID = (String) prop.get("UDID");
		String appPackage = (String) prop.get("AppPackage");
		String appActivity = (String) prop.get("AppActivity");
		 
	     capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device); 
	     capabilities.setCapability("platformName","Android");
	     capabilities.setCapability("udid", UDID);
	     capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
	     capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,14);
	     
	     capabilities.setCapability("appPackage", appPackage );
	     capabilities.setCapability("appActivity", appActivity);
	    driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    
		    return driver;
		}
		
		/**
		 * 
		 * @param driver object of the mobile driver
		 * @return
		 * @throws IOException
		 */
		public static String getScreenshot(AndroidDriver<AndroidElement>  driver) throws IOException
		{
		String path = System.getProperty("user.dir")+"/Screenshots/"+getCurrentDateTime()+".png";
		File scrfile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrfile,new File(path));
		return path;
		
		}
		
		/**
		 * This method gets the current time
		 * @return String containing the current time
		 */
		public static String getCurrentDateTime()
		{
			DateFormat customFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
			Date currentDate = new Date();
			return customFormat.format(currentDate);
		}
		
		/**
		 * This method is used to scroll to the given text
		 * @param text text to be scrolled up
		 */
		public void scrollToText(String text)
		{
		     driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));");
		}
		
		
		/**
		 * This method is used to click the center of the screen
		 */
		public void clickCentreOfScreen()
		{
			Dimension dimension = driver.manage().window().getSize();
			int centreX = (int) (dimension.getWidth() * 0.5);
		    int centreY = (int) (dimension.getHeight() * 0.5);
		    TouchAction touchAction=new TouchAction(driver);
		    touchAction.tap(PointOption.point(centreX, centreY)).perform();
		}
		
		/**
		 * This method is used to click the enter key
		 */
		public void clickEnterKey()
		{
			((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
		}
		
	    /**
	     * method to get pageSource of a particular screen
	     */
	    public void getPageSource() {
	       String pageSource =  driver.getPageSource();
	       log.info("****Page Source****"+pageSource);
	     		
	    }
	    
	    /**
	     * method to get all the context handles at particular screen
	     */
	    public void getAllContexts() {
	        Set<String> set = (((AppiumDriver) driver).getContextHandles());
	     // 1. Returns an iterator over the elements in this set
	     		Iterator<String> it = set.iterator();

	     		while (it.hasNext()) {
	     			log.info(it.next());
	     		}
	    }

	    /**
	     * method to set the context to required view.
	     *
	     * @param context view to be set
	     */
	    public void setContext(String context) {

	        Set<String> contextNames = ((AppiumDriver) driver).getContextHandles();

	        if (contextNames.contains(context)) {
	            ((AppiumDriver) driver).context(context);
	            log.info("Context changed successfully");
	        } else {
	        	System.out.println(context + "not found on this page");
	        }

	        log.info("Current context" + ((AppiumDriver) driver).getContext());
	    }
		
}
