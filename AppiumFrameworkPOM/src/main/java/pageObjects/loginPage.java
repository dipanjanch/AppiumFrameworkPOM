package pageObjects;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utility.baseFunctionalities;
import utility.log;

/**
 * the loginPage class contains all the loginPage elements and functions
 * associated with them
 * 
 * @author Dipanjan
 *
 */
public class loginPage extends baseFunctionalities {
	AndroidDriver<AndroidElement> driver;

	/**
	 * Constructor to initialize the webElements and the driver
	 * 
	 * @param driver
	 */
	public loginPage(AndroidDriver<AndroidElement> driver) throws IOException {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		this.driver = driver;
	}

	/**
	 * The elements of the login page
	 */

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/skip_sign_in_button")
	public WebElement skipLogin;
	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/sign_in_button")
	public WebElement signInButton;
	@AndroidFindBy(id = "	com.amazon.mShop.android.shopping:id/new_user")
	public WebElement createAccount;
	@AndroidFindBy(xpath = "//android.widget.EditText[@id='ap_email_login']")
	public WebElement mobileEmailtextbox;
	@AndroidFindBy(xpath = "//android.widget.Button[@id='continue']")
	public WebElement continueButton;
	@AndroidFindBy(xpath = "//android.widget.EditText[@id='ap_password']")
	public WebElement passwordBox;
	@AndroidFindBy(id = "signInSubmit")
	public WebElement signInSubmit;
	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='English - EN']")
	public WebElement englishLanguageRadioButton;
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Save Changes']")
	public WebElement saveChanges;

	/**
	 * 
	 */
	public void validateLoginPageElements()
	{
		Assert.assertTrue(isElementDisplayed(signInButton), "Validating signInButton");
		Assert.assertTrue(isElementDisplayed(createAccount), "Validating createAccount");
		Assert.assertTrue(isElementDisplayed(skipLogin), "Validating skipLogin");
		reporter.pass("Validating the login page elements");
	}
	/**
	 * The method is for existing user login which having account already
	 * 
	 * @param email contains the user name
	 * @param pass  contains the password
	 * @throws InterruptedException
	 */
	public void userlogin(String email, String pass) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(mobileEmailtextbox));
		boolean eleSelected = mobileEmailtextbox.isDisplayed();
		assertEquals(eleSelected, true);
		setValueToField(mobileEmailtextbox, email, pass);
		clickElement(continueButton, "continueButton");
		wait.until(ExpectedConditions.visibilityOf(passwordBox));
		setValueToField(passwordBox, pass, "passwordBox");
		clickElement(signInSubmit, "signInSubmit button");
		reporter.pass("Username and password entered sucessfully");
	}

	/**
	 * This method clicks in RadioButton no argument as input
	 * 
	 * @throws InterruptedException
	 */
	public void clickEnglishRadioButtonandSaveChanges() throws InterruptedException {
		clickElement(englishLanguageRadioButton, "englishLanguageRadioButton");
		clickElement(saveChanges, "saveChanges");

	}

}
