package src.test.utils;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import src.main.trees.ReadFile;
import src.main.utils.LargeInteger;
import src.main.utils.TonelliShanks;
import java.security.MessageDigest;
import java.util.Random;

public class TonelliShanksTest {

	protected LargeInteger p, h;

	@BeforeClass public static void beforeClass () {
		System.out.println("TonelliShanksTest");
	}

	@Before public void setUp() {
		p = LargeInteger.getInstance("14081674184982926243");
		h = LargeInteger.getInstance("4820946344706800409");
	}

	@Test public void testQuadraticResidue() {
		assertEquals(TonelliShanks.isQuadraticResidue(p, h), false);
	}

	@After public void tearDown() {}

	@AfterClass public static void afterClass () {}

}
