package pageObjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import jdk.internal.org.jline.utils.Log;
import utility.basePage;
import utility.log;

/**
 * the productDetailPage class contains all the productDetailPage elements and functions associated with them
 * @author Dipanjan
 *
 */
public class productDetailPage extends basePage{
	AndroidDriver<AndroidElement> driver;
	public static String CompanyNameProductDeatilPage;
	public static String ProductNameProductDeatilPage;
	public static String ProductPriceProductDeatilPage;
	
	public productDetailPage(AndroidDriver<AndroidElement> driver)throws IOException
	{
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		this.driver=driver;
	}
	
	
	/**
	 * The elements of the product detail page 
	 */
	@AndroidFindBy(id="	com.amazon.mShop.android.shopping:id/loc_ux_gps_enter_pincode")
	public WebElement enterPinCode;	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='bylineInfo']/	android.widget.TextView")
	public WebElement compnayNameProductDetailPage;	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='title_feature_div']/android.view.View")
	public WebElement productNameProductDetailPage;
	@AndroidFindBy(xpath="//android.view.View[@resource-id='atfRedesign_priceblock_priceToPay']/	android.widget.EditText")
	public WebElement productPriceProductDetailPage;
	@AndroidFindBy(xpath="//android.widget.Button[@text='Add to Cart']")
	public WebElement addtoCart;
	@AndroidFindBy(xpath="//android.view.View[@content-desc='Cart']/android.view.View/android.widget.Button")
	public WebElement viewCart;
	@AndroidFindBy(xpath="(//android.view.View[@resource-id='sc-proceed-to-checkout-params-form']/	android.widget.TextView)[3]")
	public WebElement cartAmount; 
	@AndroidFindBy(id="com.amazon.mShop.android.shopping:id/loc_ux_gps_enter_pincode")
	public WebElement enterPincodeButton;
	@AndroidFindBy(id="com.amazon.mShop.android.shopping:id/action_bar_cart_image")
	public WebElement cartIcon;
	@AndroidFindBy(xpath="//android.widget.Button[@text='Proceed to Buy']")
	public WebElement proceedToBuy;
	
	
	/**
	 * This method verifies the product name,company and price in the product detail page and product description page
	 */
	public void verifyProductdeatilsOnProductListandProductDeatilPgae()
	{
		ignoreEnterPincodePopup();		
		Assert.assertTrue(verifyingPage(compnayNameProductDetailPage, (homePage.CompanyNameProductList))
	    		,"Validating the Company Name in Product List and Product Detail Screen");
		reporter.pass("Validating the Product Company name details");
	    Assert.assertTrue(verifyingPage(productNameProductDetailPage, (homePage.ProductNameProductlist))
	    		,"Validating the Product Name in Product List and Product Detail Screen");
	    reporter.pass("Validating the Product name details");
	    ProductPriceProductDeatilPage = productPriceProductDetailPage.getText().replaceAll("[^0-9]", "");
	    Assert.assertTrue(ProductPriceProductDeatilPage.equalsIgnoreCase(homePage.ProductPriceProductlist)
	    		,"Validating the Product Price in Product List and Product Detail Screen");
	    reporter.pass("Validating the Product price details");
	}
	
	/**
	 * This method ignores the enter pin code popup
	 */
	public void ignoreEnterPincodePopup()
	{
		waitForElementPresence(enterPincodeButton);
		clickCentreOfScreen();
	}
	
	
	/**
	 * This method clicks in AddtoCart field
	 *  no argument as input
	 * @throws InterruptedException 
	 */
	public void clickAddtoCart() throws InterruptedException
	{
		scrollToText("Add to Cart");
		clickElement(addtoCart, "addtoCart");
	}
	
	

}
