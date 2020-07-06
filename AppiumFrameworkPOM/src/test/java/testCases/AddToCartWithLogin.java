package testCases;

import java.io.IOException;

import org.openqa.selenium.ScreenOrientation;
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

	public LoginPage loginPage;
	public HomePage homePage;
	public ProductDetailPage prodDetailPage;
	public Utilities utilities;

	/**
	 * The method is used for starting the server, driver and initializing the
	 * object for all required classes
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@BeforeTest
	public void setUp() throws InterruptedException, IOException {
		initialization();
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		prodDetailPage = new ProductDetailPage(driver);
		utilities = new Utilities();
		LogClass.info("Driver has been created");
	}

	/**
	 * The method is for existing user login which having account already
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(priority = 0)
	public void loginTest() throws InterruptedException, IOException {
		startTestCase(new Object() {
		}.getClass().getEnclosingMethod().getName());
		
		LogClass.info("Welcome Page will display");
		clickElement(loginPage.signinButton, "signIn Button");
		loginPage.verifyUserLogin(utilities.getDataFromDatalist("FirstUser", "username"),
				utilities.getDataFromDatalist("FirstUser", "password"));
	}

	/**
	 * Test the add-to-cart functionality
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dependsOnMethods = { "loginTest" })
	public void addToCartFunctionality() throws IOException, InterruptedException {
		startTestCase(new Object() {
		}.getClass().getEnclosingMethod().getName());

		homePage.searchText(utilities.getDataFromDatalist("FirstUser", "SearchText"));
		homePage.getProductDetails();
		clickElement(homePage.productNameField, "Product from ProductList");
		prodDetailPage.verifyProductDetails();
		verifyScreenRotation(ScreenOrientation.LANDSCAPE);
		prodDetailPage.verifyAddtoCart();
		attachScreenshotToReport();
		verifyScreenRotation(ScreenOrientation.PORTRAIT);
		waitForElementPresent(prodDetailPage.cartIcon);
		clickElement(prodDetailPage.cartIcon, "view cartIcon");
		Assert.assertTrue(isElementDisplayed(prodDetailPage.proceedToBuyButton),
				"Verify if Proceed to Buy button is displayed");
		reporter.pass("Verify if Proceed to Buy button is displayed");
		attachScreenshotToReport();
	}

	/**
	 * The method is use for stopping the server and closing the driver.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@AfterTest
	public void teardown() throws InterruptedException, IOException {
		tearDownMethod();
	}

}
