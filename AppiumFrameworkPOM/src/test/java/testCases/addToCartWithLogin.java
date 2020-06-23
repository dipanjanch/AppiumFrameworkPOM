package testCases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import utility.baseFunctionalities;
import utility.log;


/**
 * the addToCartWithLogin class contains the test case which will login and test the add-to-cart functionality
 * @author Dipanjan
 *
 */
public class addToCartWithLogin extends baseFunctionalities {
	
	public addToCartWithLogin() throws IOException {
		super();
	}

	/**
	 * The method is for existing user login which having account already
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(priority = 0)
	public void TC01_loginTest() throws InterruptedException, IOException {
		logger = report.createTest(new Object(){}.getClass().getEnclosingMethod().getName());
	    logger.info("Strat Testcase "+new Object(){}.getClass().getEnclosingMethod().getName());
	    log.info("Welcome Page will display");
	    login.clicksignInButton();
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
	    logger = report.createTest(new Object(){}.getClass().getEnclosingMethod().getName());
	    logger.info("Strat Testcase "+new Object(){}.getClass().getEnclosingMethod().getName());
	        
	    home.enterSearchText(utils.getDataFromDatalist("FirstUser", "SearchText"));	    	    
	    home.getProductDetails();	    
	    home.clickProductFromProductList();	    
	    prodDetailPage.getProductDetailsProductDeatilPage();	    
	    prodDetailPage.verifyProductdeatilsOnProductListandProductDeatilPgae();      
	    prodDetailPage.clickAddtoCart(); 
	    prodDetailPage.clickviewCart();    
	    prodDetailPage.isProceedToBuyDisplayed();	    
	    
	    logger.pass("Passed testcase");
	    
	    
	}

}
