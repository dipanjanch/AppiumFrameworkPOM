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
import utility.baseFunctionalities;
import utility.log;

/**
 * the productDetailPage class contains all the productDetailPage elements and functions associated with them
 * @author Dipanjan
 *
 */
public class productDetailPage extends baseFunctionalities{
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
	private WebElement enterPinCode;
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='bylineInfo']/	android.widget.TextView")
	private WebElement compnayNameProductDetailPage;
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='title_feature_div']/android.view.View")
	private WebElement productNameProductDetailPage;
	
	@AndroidFindBy(xpath="//android.view.View[@resource-id='atfRedesign_priceblock_priceToPay']/	android.widget.EditText")
	private WebElement productPriceProductDetailPage;
	
	@AndroidFindBy(xpath="//android.widget.Button[@text='Add to Cart']")
	private WebElement addtoCart;
	
	@AndroidFindBy(xpath="//android.view.View[@content-desc='Cart']/android.view.View/android.widget.Button")
	private WebElement viewCart;
	
	@AndroidFindBy(xpath="(//android.view.View[@resource-id='sc-proceed-to-checkout-params-form']/	android.widget.TextView)[3]")
	private WebElement cartAmount; 
	
	
	@AndroidFindBy(id="com.amazon.mShop.android.shopping:id/loc_ux_gps_enter_pincode")
	private WebElement enterPincodeButton;
	
	@AndroidFindBy(id="com.amazon.mShop.android.shopping:id/action_bar_cart_image")
	private WebElement cartIcon;
	
	@AndroidFindBy(xpath="//android.widget.Button[@text='Proceed to Buy']")
	private List <WebElement> proceedToBuy;
	
	
	/**
	 * This method clicks in Pin code field
	 */
	public void clickenterPinCode()
	{
		enterPinCode.click();
	}
	
	
	/**
	 * Returns the product company name in product detail page
	 * @return the product company name in product detail page
	 */
	public String getcompnayNameProductDetailPage()
	{

		return(compnayNameProductDetailPage.getText());
	}
	
	
	/**
	 * Returns the product name in product detail page
	 * @return the product name in product detail page
	 */
	public String getProductNameProductDetailPage()
	{
		return(productNameProductDetailPage.getText());
	}
	
	
	/**
	 * Returns the product price in product detail page
	 * @return the product price in product detail page
	 */
	public String getproductPriceProductDetailPage()
	{
		
		String price = productPriceProductDetailPage.getText();
		price = price.replaceAll("[^0-9]", "");
		return price;
	}
	
	/**
	 * This method extracts the product name,company and price 
	 */
	public void getProductDetailsProductDeatilPage()
	{
		ignoreEnterPincodePopup();
		CompanyNameProductDeatilPage = getcompnayNameProductDetailPage();
		log.info("CompanyNameProductDeatilPage="+CompanyNameProductDeatilPage);
		ProductNameProductDeatilPage = getProductNameProductDetailPage();
		log.info("ProductNameProductDeatilPage="+ProductNameProductDeatilPage);
		ProductPriceProductDeatilPage = getproductPriceProductDetailPage();
		log.info("ProductPriceProductDeatilPage="+ProductPriceProductDeatilPage);
	}
	
	/**
	 * This method verifies the product name,company and price in the product detail page and product description page
	 */
	public void verifyProductdeatilsOnProductListandProductDeatilPgae()
	{
		Assert.assertTrue(CompanyNameProductDeatilPage.equalsIgnoreCase(homePage.CompanyNameProductList)
	    		,"Validating the Company Name in Product List and Product Detail Screen");
		logger.pass("Validating the Product Company name details");
	    Assert.assertTrue(ProductNameProductDeatilPage.equalsIgnoreCase(homePage.ProductNameProductlist)
	    		,"Validating the Product Name in Product List and Product Detail Screen");
	    logger.pass("Validating the Product name details");
	    Assert.assertTrue(ProductPriceProductDeatilPage.equalsIgnoreCase(homePage.ProductPriceProductlist)
	    		,"Validating the Product Price in Product List and Product Detail Screen");
	    logger.pass("Validating the Product price details");
	}
	
	/**
	 * This method ignores the enter pin code popup
	 */
	public void ignoreEnterPincodePopup()
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(enterPincodeButton));
		clickCentreOfScreen();
	}
	
	
	/**
	 * This method clicks in AddtoCart field
	 *  no argument as input
	 */
	public void clickAddtoCart()
	{
		scrollToText("Add to Cart");
		addtoCart.click();
		logger.pass("Clicking on Add to cart button");
	}
	
	
	/**
	 * This method clicks in viewCart field
	 *  no argument as input
	 */
	public void clickviewCart()
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(cartIcon));
		cartIcon.click();
		logger.pass("Clicking on view cart button");
	}
	
	
	/**
	 * This method returns the cart amount
	 * @return the total cart amount
	 */
	public String getcartAmount()
	{
		String price = cartAmount.getText();
		String [] priceArr = price.split(".", 2);
		price = priceArr[0].replaceAll("[^0-9]", "");
		return price;
	}
	
	
	/**
	 * This method verifies if ProceedToBuyDisplayed is displayed
	 * @throws InterruptedException
	 */
	public void isProceedToBuyDisplayed()throws InterruptedException
	{
		 Assert.assertTrue(proceedToBuy.size()>0,"Verify if Proceed to Buy button is displayed");
		 logger.pass("Verify if Proceed to Buy button is displayed");
	}
	
	

}
