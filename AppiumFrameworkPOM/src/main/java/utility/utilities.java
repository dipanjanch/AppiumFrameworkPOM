package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;

/**
 * the utilities class contains all the important functions like data access etc
 * 
 * @author Dipanjan
 *
 */
public class utilities extends basePage {
	AndroidDriver<AndroidElement> driver;

	/**
	 * This method is used to get the data from the data list(test data)
	 * 
	 * @param Role the user number
	 * @param Key  the key value to be fetched
	 * @return
	 */
	public String getDataFromDatalist(String Role, String Key) {

		try {

			Object object = new JSONParser().parse(new FileReader("src/test/java/testData/dataList.json"));
			JSONObject jsonObject = (JSONObject) object;
			JSONArray jsonArray = (JSONArray) jsonObject.get("UserData");
			JSONObject j = (JSONObject) jsonArray.get(0);
			JSONArray childArray = (JSONArray) j.get(Role);
			JSONObject childObject = (JSONObject) childArray.get(0);
			String value = (String) childObject.get(Key);

			return value;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

}
