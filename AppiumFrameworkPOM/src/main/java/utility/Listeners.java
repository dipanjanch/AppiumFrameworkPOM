package utility;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.MediaEntityBuilder;
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

	public void onTestStart(ITestResult result) {
		LogClass.info("Test case" + result.getName() + " started");
	}

	public void onTestSuccess(ITestResult result) {
		LogClass.info("Test case " + result.getName() + " is passed");
		reporter.pass("Passed testcase");
		try {
			attachScreenshottoReport();
		} catch (IOException e) {
			LogClass.info("Failed to capture screenshot");
			e.printStackTrace();
		}
	}

	public void onTestFailure(ITestResult result) {
		LogClass.info("Test case " + result.getName() + " is failed");
		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				reporter.log(Status.FAIL, MarkupHelper
						.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
				reporter.fail(result.getThrowable());
				attachScreenshottoReport();
			} catch (IOException e) {
				LogClass.info("Failed to capture screenshot");
				e.printStackTrace();
			}
		}
		report.flush();
	}

	public void onTestSkipped(ITestResult result) {
		LogClass.info("Test case " + result.getName() + " is skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		LogClass.info("Test case " + result.getName() + " is failed");
	}

	public void onStart(ITestContext context) {
		LogClass.info("Execution started");
	}

	public void onFinish(ITestContext context) {
		LogClass.info("Execution Finished");
		report.flush();
	}
}
