package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * the log class contains all the important functions  for logging 
 * @author Dipanjan
 *
 */
public class log {

//Initialize Log4j logs

private static Logger Log = LogManager.getLogger(log.class.getName());

/**
 * This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
 * @param sTestCaseName the name of the test case
 */

public static void startTestCase(String sTestCaseName){
Log.info("****************************************************************************************");
Log.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");
Log.info("****************************************************************************************");
}

/**
 * This is to print log for the ending of the test case
 * @param sTestCaseName the name of the test case
 */

public static void endTestCase(String sTestCaseName){
Log.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
}

/**
 * Method for info logs 
 * @param message message to be displayed
 */

public static void info(String message) {

Log.info(message);

}

/**
 * Method for warn logs
 * @param message message to be displayed
 */
public static void warn(String message) {

   Log.warn(message);

}

/**
 * Method for error logs
 * @param message message to be displayed
 */
public static void error(String message) {

   Log.error(message);

}

/**
 * Method for fatal logs
 * @param message message to be displayed
 */
public static void fatal(String message) {

   Log.fatal(message);

}

/**
 * Method for debug logs
 * @param message message to be displayed
 */
public static void debug(String message) {

   Log.debug(message);

}

}
