package testCases;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
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

public class addToCartFunctionality  extends baseFunctionalities {
	
	public addToCartFunctionality() throws IOException {
		super();
	}
	
	/**
	 * Test case will skip login and test the add-to-cart functionality
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Test
	public void TC01_add_To_Cart() throws IOException, InterruptedException
	{	    
	    logger = report.createTest(this.getClass().getSimpleName());
	    logger.info("Strat Testcase "+this.getClass().getSimpleName());
	    
	    login.clickskipLogin();    
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
