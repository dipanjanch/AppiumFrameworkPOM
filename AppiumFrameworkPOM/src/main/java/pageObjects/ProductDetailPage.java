package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * This class contains all the productDetailPage elements and functions
 * associated with them
 * 
 * @author Dipanjan
 *
 */
public class ProductDetailPage extends BasePage {
	AndroidDriver<AndroidElement> driver;

	public ProductDetailPage(AndroidDriver<AndroidElement> driver) throws IOException {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		this.driver = driver;
	}

	/**
	 * The elements of the product detail page
	 */
	@AndroidFindBy(xpath = "//android.view.View[@resource-id='bylineInfo']/	android.widget.TextView")
	public WebElement compnayNameField;
	@AndroidFindBy(xpath = "//android.view.View[@resource-id='title_feature_div']/android.view.View")
	public WebElement productNameField;
	@AndroidFindBy(xpath = "//android.view.View[@resource-id='atfRedesign_priceblock_priceToPay']/android.widget.EditText")
	public WebElement productPriceField;
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Add to Cart']")
	public WebElement addtoCartButton;
	@AndroidFindBy(xpath = "//android.view.View[@content-desc='Cart']/android.view.View/android.widget.Button")
	public WebElement viewCartButton;
	@AndroidFindBy(xpath = "//*[@text='Enter a pincode']")
	public WebElement enterPincodeButton;
	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/action_bar_cart_image")
	public WebElement cartIcon;
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Proceed to Buy']")
	public WebElement proceedToBuyButton;

	/**
	 * This method verifies the product name,company and price in the product detail
	 * page and product description page
	 */
	public void verifyProductDetails() {
		ignorePincodePopup();
		Assert.assertTrue(verifyValueInField(compnayNameField, (HomePage.companyName)),
				"Validating the Company Name in Product List and Product Detail Screen");
		reporter.pass("Validating the Product Company name details");
		Assert.assertTrue(verifyValueInField(productNameField, (HomePage.productName)),
				"Validating the Product Name in Product List and Product Detail Screen");
		reporter.pass("Validating the Product name details");
		String productPrice = getText(productPriceField).replaceAll("[^0-9]", "");
		Assert.assertTrue(productPrice.equalsIgnoreCase(HomePage.productPrice),
				"Validating the Product Price in Product List and Product Detail Screen");
		reporter.pass("Validating the Product price details");
	}

	/**
	 * This method ignores the enter pin code popup
	 */
	public void ignorePincodePopup() {
		waitForElementPresent(enterPincodeButton);
		clickCentreOfScreen();
	}

	/**
	 * This method clicks in AddtoCart field no argument as input
	 * 
	 * @throws InterruptedException
	 */
	public void verifyAddtoCart() throws InterruptedException {
		scrollToText("Add to Cart");
		clickElement(addtoCartButton, "add to Cart Button");
	}

}
