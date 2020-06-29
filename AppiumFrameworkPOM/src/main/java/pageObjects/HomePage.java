package pageObjects;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;
import utility.LogClass;
import utility.Utilities;

/**
 * the homePage class contains all the homepage elements and functions
 * associated with them
 * 
 * @author Dipanjan
 *
 */
public class HomePage extends BasePage {
	AndroidDriver<AndroidElement> driver;
	public static String CompanyName;
	public static String ProductName;
	public static String ProductPrice;

	/**
	 * Constructor to initialize the web elements and the driver
	 * 
	 * @param driver
	 */
	public HomePage(AndroidDriver<AndroidElement> driver) throws IOException {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		this.driver = driver;

	}

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	public WebElement searchBox;
	@AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc=\"Visa\"]")
	public WebElement sugesstionBar;
	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/item_title")
	public List<WebElement> productList;
	@AndroidFindBy(xpath = "(//*[@resource-id='com.amazon.mShop.android.shopping:id/rs_item_styled_byline'])[3]/android.widget.TextView")
	public WebElement companyNameProductList;
	@AndroidFindBy(xpath = "(//*[@resource-id='com.amazon.mShop.android.shopping:id/item_title'])[3]")
	public WebElement ProductNameProductList;
	@AndroidFindBy(xpath = "(//*[@resource-id='com.amazon.mShop.android.shopping:id/rs_results_styled_price_v2'])[3]/android.widget.TextView")
	public WebElement ProductPriceProductList;

	public List<WebElement> getProductList() {
		return productList;
	}

	/**
	 * This method enters the search text
	 * 
	 * @param text: String argument as input
	 * @throws InterruptedException
	 */
	public void enterSearchText(String text) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		clickElement(wait.until(ExpectedConditions.visibilityOf(searchBox)), "searchBox");
		if (isElementDisplayed(sugesstionBar))
			clickCentreOfScreen();
		setValueToField(searchBox, text, "searchBox");
		reporter.pass("Entering SearchText" + text);
		clickEnterKey();
	}

	/**
	 * This method returns the name of the company of the product from the
	 * product list no argument as input
	 */
	public String getCompanyNameProductList() {
		String compName = getText(companyNameProductList);
		compName = compName.replace("by ", "");
		LogClass.info("getCompanyNameProductList " + compName);
		return compName;
	}

	/**
	 * This method returns the name of the product of the product from the
	 * product list no argument as input
	 */
	public String getProductNameProductList() {
		String prodName = getText(ProductNameProductList);
		System.out.println(prodName);
		LogClass.info("getProductNameProductList " + prodName);
		return prodName;
	}

	/**
	 * This method returns the price of the product of the product from the
	 * product list no argument as input
	 */
	public String getProductPriceProductList() {
		String prodPrice = getText(ProductPriceProductList);
		String[] discountedAndOriginalPrice = prodPrice.split(" ", 2);
		discountedAndOriginalPrice[0] = discountedAndOriginalPrice[0].replaceAll("[^0-9]", "");
		discountedAndOriginalPrice[1] = discountedAndOriginalPrice[1].replaceAll("[^0-9]", "");
		LogClass.info("getProductPriceProductList " + discountedAndOriginalPrice[0]);
		return discountedAndOriginalPrice[0];
	}

	/**
	 * This method is used to extract the product details from product list screen
	 */
	public void getProductDetails() {
		CompanyName = getCompanyNameProductList();
		LogClass.info("CompanyNameProductList=" + CompanyName);
		ProductName = getProductNameProductList();
		LogClass.info("ProductNameProductlist=" + ProductName);
		ProductPrice = getProductPriceProductList();
		LogClass.info("ProductPriceProductlist=" + ProductPrice);
		reporter.pass("Getting ProductDetails from product list");
	}

}
