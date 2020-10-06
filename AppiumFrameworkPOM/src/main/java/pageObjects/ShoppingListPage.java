package pageObjects;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ShoppingListPage extends BasePage {
	AndroidDriver<AndroidElement> driver;

	public ShoppingListPage(AndroidDriver<AndroidElement> driver) throws IOException {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		this.driver = driver;
	}
	
	/**
	 * The elements of the product detail page
	 */
	@AndroidFindBy(xpath = "")
	public WebElement viewLists;
	@AndroidFindBy(xpath = "")
	public WebElement createAList;
	@AndroidFindBy(xpath = "")
	public WebElement createANewListHeading;
	@AndroidFindBy(xpath = "")
	public WebElement listNameTextBox;
	@AndroidFindBy(xpath = "")
	public WebElement createListButton;
	@AndroidFindBy(xpath = "")
	public WebElement shoppingListNameFiled;
	@AndroidFindBy(xpath = "")
	public WebElement shoppingListThreeDots;
	@AndroidFindBy(id = "")
	public WebElement addIdeaToList;
	@AndroidFindBy(xpath = "")
	public WebElement addIdeaToListTextBox;
	@AndroidFindBy(xpath = "")
	public WebElement addIdeaToListAddButton;
	@AndroidFindBy(xpath = "")
	public WebElement addIdeaToListDoneButton;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaTopSearchResultsField;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaThreeDots;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaAddCommentQunatityAndPriorty;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaAddCommentsField;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaAddNeedsField;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaAddHasField;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaAddPriorityField;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaAddPriorityValue;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaAddCommentQunatityAndPriortySaveButton;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaCommentsField;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaNeedsField;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaHasField;
	
	@AndroidFindBy(xpath = "")
	public WebElement ideaPriorityField;
	
	
	/**
	 * This method enters the search text
	 * 
	 * @param text: String argument as input
	 * @throws InterruptedException
	 */
	public void createAList(String listName) throws InterruptedException {
		waitForElementPresent(createAList);
		clickElement(createAList, "create a List");
		Assert.assertTrue(isElementDisplayed(createANewListHeading));
		setValueToField(listNameTextBox, listName, "Entering List Name");
		reporter.pass("Entering Search Text" + listName);
		clickElement(createListButton, "create List Button");
	}
	
	/**
	 * This method enters the search text
	 * 
	 * @param text: String argument as input
	 * @throws InterruptedException
	 */
	public void validateCreatedList(String listName) throws InterruptedException {
		waitForElementPresent(viewLists);
		Assert.assertTrue(listName.equalsIgnoreCase(getText(shoppingListNameFiled)));		
		reporter.pass("Entering Search Text" + listName);
	}
	
	/**
	 * This method enters the search text
	 * 
	 * @param text: String argument as input
	 * @throws InterruptedException
	 */
	public void navigateToCreatedList(String listName) throws InterruptedException {
		waitForElementPresent(viewLists);
		Assert.assertTrue(listName.equalsIgnoreCase(getText(shoppingListNameFiled)));		
		reporter.pass("Entering Search Text" + listName);
	}

}
