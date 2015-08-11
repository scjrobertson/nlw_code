package src.test.utils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import src.main.utils.LargeInteger;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class LargeIntegerTest {

	protected LargeInteger x, y;

	@BeforeClass public static void beforeClass () {
		System.out.println("LargeIntegerTest");
	}

	@Before public void setUp() {
		x = LargeInteger.getInstance(5);
		y = LargeInteger.getInstance("13");
	}

	@Test public void testIntType() {
		assertEquals(x.toString(), "5");
	}

	@Test public void testStringType() {
		assertEquals(y.toString(), "13");
	}

	@Test public void testAddition() {
		assertEquals(x.add(2).toString(), "7");
	}

	@Test public void testMulitplication() {
		assertEquals(x.multiply(2).toString(), "10");
	}

	@Test public void testDivision() {
		assertEquals(x.divide(2).toString(), "2");
	}

	@Test public void testSubtraction() {
		assertEquals(x.subtract(2).toString(), "3");
	}

	@Test public void testModulus() {
		assertEquals(x.mod(2).toString(), "1");
	}

	@Test public void testLargeModulus() {
		assertEquals(x.mod(y).toString(), "5");
	}

	@Test public void testIntPow() {
		assertEquals(x.pow(2).toString(), "25");
	}

	@Test public void testLargeIntPow() {
		assertEquals(y.pow(x).toString(), "371293");
	}

	@Test public void textXOR() {
		assertEquals(x.xor(y).toString(), "8");
	}

	@Test public void textEquals() {
		LargeInteger z = LargeInteger.getInstance("5");
		assertEquals(x.equals(z), true);
	}

	@After public void tearDown() {}

}
