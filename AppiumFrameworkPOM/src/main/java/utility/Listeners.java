package utility;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import pageObjects.BasePage;

/**
 * This class is used to generate Listeners methods
 * 
 * @author Dipanjan
 */

public class Listeners extends BasePage implements ITestListener {
	public Listeners() throws IOException {
		super();
	}

	/**
	 * Invoked each time before a test will be invoked.
	 */
	public void onTestStart(ITestResult result) {
		LogClass.info("Test case" + result.getName() + " started");
	}

	/**
	 * Invoked each time a test succeeds.
	 */
	public void onTestSuccess(ITestResult result) {
		LogClass.info("Test case " + result.getName() + " is passed");
		reporter.pass("Passed testcase");
		try {
			attachScreenshotToReport();
		} catch (IOException e) {
			LogClass.warn("Failed to capture screenshot");
			e.printStackTrace();
		}
	}

	/**
	 * Invoked each time a test fails.
	 */
	public void onTestFailure(ITestResult result) {
		LogClass.error("Test case " + result.getName() + " is failed");
		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				reporter.log(Status.FAIL, MarkupHelper
						.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
				reporter.fail(result.getThrowable());
				attachScreenshotToReport();
			} catch (IOException e) {
				LogClass.error("Failed to capture screenshot");
				e.printStackTrace();
			}
		}
		report.flush();
	}

	/**
	 * Invoked each time a test is skipped.
	 */
	public void onTestSkipped(ITestResult result) {
		LogClass.info("Test case " + result.getName() + " is skipped");
	}

	/**
	 * Invoked each time a method fails but has been annotated with
	 * successPercentage and this failure still keeps it within the success
	 * percentage requested.
	 */
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		LogClass.info("Test case " + result.getName() + " is failed");
	}

	/**
	 * Invoked before running all the test methods belonging to the classes inside
	 * the test tag and calling all their Configuration methods.
	 */
	public void onStart(ITestContext context) {
		LogClass.info("Execution started");
	}

	/**
	 * Invoked after all the test methods belonging to the classes inside the test
	 * tag have run and all their Configuration methods have been called.
	 */
	public void onFinish(ITestContext context) {
		LogClass.info("Execution Finished");
		report.flush();
	}
}
