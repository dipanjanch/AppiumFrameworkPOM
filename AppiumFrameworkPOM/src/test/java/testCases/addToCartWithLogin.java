package testCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.homePage;
import pageObjects.loginPage;
import pageObjects.productDetailPage;
import utility.basePage;
import utility.log;
import utility.utilities;


/**
 * the addToCartWithLogin class contains the test case which will login and test the add-to-cart functionality
 * @author Dipanjan
 *
 */
public class addToCartWithLogin extends basePage {
	
	public addToCartWithLogin() throws IOException {
		super();
	}
	public loginPage login;
	public homePage home;
	public productDetailPage prodDetailPage;
	public utilities utils;
	WebDriverWait wait;
	public static Properties prop;
	/**
	 * The method is for existing user login which having account already
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(priority = 0)
	public void TC01_loginTest() throws InterruptedException, IOException {
		reporter = report.createTest(new Object(){}.getClass().getEnclosingMethod().getName());
	    reporter.info("Strat Testcase "+new Object(){}.getClass().getEnclosingMethod().getName());
	    log.info("Welcome Page will display");
	    clickElement(login.signInButton, "signIn Button");
	    login.userlogin(utils.getDataFromDatalist("FirstUser", "username"), utils.getDataFromDatalist("FirstUser", "password"));	
	}
	
	/**
	 * Test the add-to-cart functionality
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(dependsOnMethods = {"TC01_loginTest"})
	public void TC02_add_To_Cart() throws IOException, InterruptedException
	{    
	    reporter = report.createTest(new Object(){}.getClass().getEnclosingMethod().getName());
	    reporter.info("Strat Testcase "+new Object(){}.getClass().getEnclosingMethod().getName());
	        
	    home.enterSearchText(utils.getDataFromDatalist("FirstUser", "SearchText"));	    	    
	    home.getProductDetails();	    
	    clickElement(home.ProductNameProductList, "Product from ProductList");    	    
	    prodDetailPage.verifyProductdeatilsOnProductListandProductDeatilPgae();      
	    prodDetailPage.clickAddtoCart(); 
	    screenRotate(ScreenOrientation.LANDSCAPE);
		attachScreenshottoReport();
		waitForElementPresence(prodDetailPage.cartIcon);
		clickElement(prodDetailPage.cartIcon, "view cartIcon");
		Assert.assertTrue(isElementDisplayed(prodDetailPage.proceedToBuy),"Verify if Proceed to Buy button is displayed");
		reporter.pass("Verify if Proceed to Buy button is displayed");
		attachScreenshottoReport();
		reporter.pass("Passed testcase");	    
	    
	    reporter.pass("Passed testcase");
	    
	    
	}

}
