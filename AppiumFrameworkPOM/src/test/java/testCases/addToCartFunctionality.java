package testCases;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import pageObjects.loginPage;
import pageObjects.productDetailPage;
import utility.baseFunctionalities;
import utility.log;
import utility.utilities;
import pageObjects.homePage;

/**
 * the addToCartFunctionality class contains the test case which will skip login
 * and test the add-to-cart functionality
 * 
 * @author Dipanjan
 *
 */
public class addToCartFunctionality extends baseFunctionalities {

	public addToCartFunctionality() throws IOException {
		super();
	}
	public loginPage login;
	public homePage home;
	public productDetailPage prodDetailPage;
	public utilities utils;
	WebDriverWait wait;
	public static Properties prop;
	
	@BeforeTest
	public void setUp() throws InterruptedException, IOException {
		initialization();
		login = new loginPage(driver);
		home = new homePage(driver);
		prodDetailPage = new productDetailPage(driver);
		utils = new utilities();
		log.info("Driver has been created");
		wait = new WebDriverWait(driver, 30);
	}
	/**
	 * Test case will skip login and test the add-to-cart functionality
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void TC01_add_To_Cart() throws IOException, InterruptedException {
		reporter = report.createTest(this.getClass().getSimpleName());
		reporter.info("Strat Testcase " + this.getClass().getSimpleName());
		login.validateLoginPageElements();
		clickElement(login.skipLogin, "skipLogin");
		home.enterSearchText(utils.getDataFromDatalist("FirstUser", "SearchText"));
		home.getProductDetails();
		clickElement(home.ProductNameProductList, "Product from ProductList");
		prodDetailPage.verifyProductdeatilsOnProductListandProductDeatilPgae();
		screenRotate(ScreenOrientation.LANDSCAPE);
		prodDetailPage.clickAddtoCart();			
		attachScreenshottoReport();
		waitForElementPresence(prodDetailPage.cartIcon);
		clickElement(prodDetailPage.cartIcon, "view cartIcon");
		screenRotate(ScreenOrientation.PORTRAIT);
		Assert.assertTrue(isElementDisplayed(prodDetailPage.proceedToBuy),"Verify if Proceed to Buy button is displayed");
		reporter.pass("Verify if Proceed to Buy button is displayed");
		attachScreenshottoReport();
		reporter.pass("Passed testcase");

	}
	
	@AfterTest
	public void teardown() throws InterruptedException, IOException {
		tearDown();
		}

}
