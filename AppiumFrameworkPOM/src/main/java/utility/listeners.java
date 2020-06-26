package utility;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

/**
 * This class is used to generate Listeners methods
 * 
 * @author Dipanjan
 */

public class listeners extends baseFunctionalities implements ITestListener {
	public listeners() throws IOException {
		super();
	}

	public void onTestStart(ITestResult result) {
		log.info("Test case" + result.getName() + " started");
	}

	public void onTestSuccess(ITestResult result) {
		log.info("Test case " + result.getName() + " is passed");
		try {
			attachScreenshottoReport();
		} catch (IOException e) {
			log.info("Failed to capture screenshot");
			e.printStackTrace();
		}
	}

	public void onTestFailure(ITestResult result) {
		log.info("Test case " + result.getName() + " is failed");
		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				reporter.log(Status.FAIL, MarkupHelper
						.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
				reporter.fail(result.getThrowable());
				attachScreenshottoReport();
			} catch (IOException e) {
				log.info("Failed to capture screenshot");
				e.printStackTrace();
			}
		}
		report.flush();
	}

	public void onTestSkipped(ITestResult result) {
		log.info("Test case " + result.getName() + " is skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.info("Test case " + result.getName() + " is failed");
	}

	public void onStart(ITestContext context) {
		log.info("Execution started");
	}

	public void onFinish(ITestContext context) {
		log.info("Execution Finished");
		report.flush();
	}
}
