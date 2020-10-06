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
import pageObjects.ShoppingListPage;
import utility.LogClass;
import utility.Utilities;

public class TC01_Verify_user_can_create_the_List extends BasePage {
	public LoginPage loginPage;
	public HomePage homePage;
	public ShoppingListPage shoppingListPage;
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
		shoppingListPage = new ShoppingListPage(driver);
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
		//clickElement(loginPage.signinButton, "signIn Button");
		//loginPage.verifyUserLogin(utilities.getDataFromDatalist("FirstUser", "username"),
		//		utilities.getDataFromDatalist("FirstUser", "password"));
		
		homePage.clickYourWishList();
		shoppingListPage.createAList(utilities.getDataFromDatalist("FirstUser", "listname"));
		shoppingListPage.validateCreatedList(utilities.getDataFromDatalist("FirstUser", "listname"));
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
