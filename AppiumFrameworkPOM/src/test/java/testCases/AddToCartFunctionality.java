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
 * the addToCartFunctionality class contains the test case which will skip login
 * and test the add-to-cart functionality
 * 
 * @author Dipanjan
 *
 */
public class AddToCartFunctionality extends BasePage {

	public AddToCartFunctionality() throws IOException {
		super();
	}

	public LoginPage loginPage;
	public HomePage homePage;
	public ProductDetailPage productDetailPage;
	public Utilities utilities;

	/**
	 * The method is use for starting the server, driver and initializing the object
	 * for all classes
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@BeforeTest
	public void setUp() throws InterruptedException, IOException {
		initialization();
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		productDetailPage = new ProductDetailPage(driver);
		utilities = new Utilities();
		LogClass.info("Driver has been created");
	}

	/**
	 * Test case will skip login and test the add-to-cart functionality
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void addToCartFunctionaity() throws IOException, InterruptedException {
		startTestCase(new Object() {
		}.getClass().getEnclosingMethod().getName());
		
		loginPage.validateLoginPageElements();
		clickElement(loginPage.skipLoginButton, "Skip Login Button");
		homePage.searchText(utilities.getDataFromDatalist("FirstUser", "SearchText"));
		homePage.getProductDetails();
		clickElement(homePage.productNameField, "Product from ProductList");
		productDetailPage.verifyProductDetails();
		verifyScreenRotation(ScreenOrientation.LANDSCAPE);
		productDetailPage.verifyAddtoCart();
		attachScreenshotToReport();
		waitForElementPresent(productDetailPage.cartIcon);
		clickElement(productDetailPage.cartIcon, "View Cart Icon");
		verifyScreenRotation(ScreenOrientation.PORTRAIT);
		Assert.assertTrue(isElementDisplayed(productDetailPage.proceedToBuyButton),
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
