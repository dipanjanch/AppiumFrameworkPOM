package pageObjects;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utility.LogClass;

/**
 * This class contains all the loginPage elements and functions associated with
 * them
 * 
 * @author Dipanjan
 *
 */
public class LoginPage extends BasePage {
	AndroidDriver<AndroidElement> driver;

	/**
	 * Constructor to initialize the webElements and the driver
	 * 
	 * @param driver
	 */
	public LoginPage(AndroidDriver<AndroidElement> driver) throws IOException {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		this.driver = driver;
	}

	/**
	 * The elements of the login page
	 */

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/skip_sign_in_button")
	public WebElement skipLoginButton;
	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/sign_in_button")
	public WebElement signinButton;
	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/new_user")
	public WebElement createAccountButton;
	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='ap_email_login']")
	public WebElement userNameTextbox;
	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='continue']")
	public WebElement continueButton;
	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='ap_password']")
	public WebElement passwordBox;
	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='signInSubmit']")
	public WebElement signinSubmitButton;
	@AndroidFindBy(xpath = "//android.widget.RadioButton[@text='English - EN']")
	public WebElement languageRadioButton;
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Save Changes']")
	public WebElement saveChangesButton;

	/**
	 * This method is used for validating login page elements
	 */
	public void validateLoginPageElements() {
		Assert.assertTrue(isElementDisplayed(signinButton), "Validating SignIn Button");
		Assert.assertTrue(isElementDisplayed(createAccountButton), "Validating Create Account Button");
		Assert.assertTrue(isElementDisplayed(skipLoginButton), "Validating SkipLogin button");
		LogClass.info("Validating the login page elements");
		reporter.pass("Validating the login page elements");
	}

	/**
	 * The method is for existing user login which having account already
	 * 
	 * @param email contains the user name
	 * @param pass  contains the password
	 * @throws InterruptedException
	 */
	public void verifyUserLogin(String email, String pass) throws InterruptedException {
		assertEquals(isElementDisplayed(userNameTextbox), true);
		setValueToField(userNameTextbox, email, "Username Field");
		clickElement(continueButton, "Continue Button");
		isElementDisplayed(passwordBox);
		setValueToField(passwordBox, pass, "Password Field");
		clickElement(signinSubmitButton, "SignIn Submit button");
		reporter.pass("Username and password entered sucessfully");
		selectLanguage();
	}

	/**
	 * This method clicks in RadioButton no argument as input
	 * 
	 * @throws InterruptedException
	 */
	public void selectLanguage() throws InterruptedException {
		clickElement(languageRadioButton, "English Language RadioButton");
		clickElement(saveChangesButton, "Save Changes");

	}

}
