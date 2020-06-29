package testCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.BasePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductDetailPage;
import utility.LogClass;
import utility.Utilities;

/**
 * the addToCartWithLogin class contains the test case which will login and test
 * the add-to-cart functionality
 * 
 * @author Dipanjan
 *
 */
public class AddToCartWithLogin extends BasePage {

	public AddToCartWithLogin() throws IOException {
		super();
	}

	public LoginPage login;
	public HomePage home;
	public ProductDetailPage prodDetailPage;
	public Utilities utils;
	WebDriverWait wait;
	public static Properties prop;

	@BeforeTest
	public void setUp() throws InterruptedException, IOException {
		initialization();
		login = new LoginPage(driver);
		home = new HomePage(driver);
		prodDetailPage = new ProductDetailPage(driver);
		utils = new Utilities();
		LogClass.info("Driver has been created");
	}

	/**
	 * The method is for existing user login which having account already
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(priority = 0)
	public void TC01_loginTest() throws InterruptedException, IOException {
		reporter = report.createTest(new Object() {
		}.getClass().getEnclosingMethod().getName());
		reporter.info("Strat Testcase " + new Object() {
		}.getClass().getEnclosingMethod().getName());
		LogClass.info("Welcome Page will display");
		clickElement(login.signInButton, "signIn Button");
		login.userlogin(utils.getDataFromDatalist("FirstUser", "username"),
				utils.getDataFromDatalist("FirstUser", "password"));
	}

	/**
	 * Test the add-to-cart functionality
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dependsOnMethods = { "TC01_loginTest" })
	public void TC02_add_To_Cart() throws IOException, InterruptedException {
		reporter = report.createTest(new Object() {
		}.getClass().getEnclosingMethod().getName());
		reporter.info("Strat Testcase " + new Object() {
		}.getClass().getEnclosingMethod().getName());

		home.enterSearchText(utils.getDataFromDatalist("FirstUser", "SearchText"));
		home.getProductDetails();
		clickElement(home.ProductNameProductList, "Product from ProductList");
		prodDetailPage.verifyProductdetails();
		screenRotate(ScreenOrientation.LANDSCAPE);
		prodDetailPage.clickAddtoCart();		
		attachScreenshottoReport();
		screenRotate(ScreenOrientation.PORTRAIT);
		waitForElementPresence(prodDetailPage.cartIcon);
		clickElement(prodDetailPage.cartIcon, "view cartIcon");
		Assert.assertTrue(isElementDisplayed(prodDetailPage.proceedToBuy),
				"Verify if Proceed to Buy button is displayed");
		reporter.pass("Verify if Proceed to Buy button is displayed");
		attachScreenshottoReport();
	}

	@AfterTest
	public void teardown() throws InterruptedException, IOException {
		tearDown();
	}

}
