package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utility.LogClass;

/**
 * This class contains all the homepage elements and functions associated with
 * them
 * 
 * @author Dipanjan
 *
 */
public class HomePage extends BasePage {
	AndroidDriver<AndroidElement> driver;
	public static String companyName;
	public static String productName;
	public static String productPrice;

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
	@AndroidFindBy(xpath = "(//*[@resource-id='com.amazon.mShop.android.shopping:id/rs_item_styled_byline'])[3]/android.widget.TextView")
	public WebElement companyNameField;
	@AndroidFindBy(xpath = "(//*[@resource-id='com.amazon.mShop.android.shopping:id/item_title'])[3]")
	public WebElement productNameField;
	@AndroidFindBy(xpath = "(//*[@resource-id='com.amazon.mShop.android.shopping:id/rs_results_styled_price_v2'])[3]/android.widget.TextView")
	public WebElement productPriceField;

	/**
	 * This method enters the search text
	 * 
	 * @param text: String argument as input
	 * @throws InterruptedException
	 */
	public void searchText(String text) throws InterruptedException {
		waitForElementPresent(searchBox);
		clickElement(searchBox, "Search Box");
		if (isElementDisplayed(sugesstionBar)) {
			clickCentreOfScreen();
		}
		setValueToField(searchBox, text, "Entering Search Text");
		reporter.pass("Entering Search Text" + text);
		clickEnterKey();
	}

	/**
	 * This method returns the name of the company of the product from the product
	 * list no argument as input
	 */
	public String getCompanyName() {
		String compName = getText(companyNameField);
		compName = compName.replace("by ", "");
		return compName;
	}

	/**
	 * This method returns the price of the product
	 */
	public String getProductPrice() {
		String prodPrice = getText(productPriceField);
		String[] discountedAndOriginalPrice = prodPrice.split(" ", 2);
		discountedAndOriginalPrice[0] = discountedAndOriginalPrice[0].replaceAll("[^0-9]", "");
		discountedAndOriginalPrice[1] = discountedAndOriginalPrice[1].replaceAll("[^0-9]", "");
		return discountedAndOriginalPrice[0];
	}

	/**
	 * This method is used to extract the product details
	 */
	public void getProductDetails() {
		companyName = getCompanyName();
		LogClass.info("Company Name =" + companyName);
		productName = getText(productNameField);
		LogClass.info("Product Name =" + productName);
		productPrice = getProductPrice();
		LogClass.info("Product Price =" + productPrice);
		reporter.pass("Getting ProductDetails from product list");
	}

}
