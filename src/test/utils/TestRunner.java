/***********************************************************************************************************************************
 * Compilation: javac -cp .:src/test/resources/hamcrest-core-1.3.jar:src/test/resources/junit-4.12.jar src.test.trees.TestRunner.java
 * Execution: java -cp .:src/test/resources/hamcrest-core-1.3.jar:src/test/resources/junit-4.12.jar src.test.trees.TestRunner
 * Dependencies: src/test/resources/hamcrest-core-1.3.jar src/test/resources/junit-4.12.jar 
 *
 * Runs the trees test suite.
 *******************************************************/

package src.test.utils;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Runs the trees test suite.
 *
 * @author SCJ Robertson, 16579852
 * @since 10/08/15
 */
public class TestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(src.test.utils.UtilsTestSuite.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}
