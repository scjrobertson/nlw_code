package src.test.trees;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import src.main.trees.ReadFile;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class ReadFileTest {

	protected BigInteger p, h;

	@Before public void setUp() {
		p = new BigInteger("14081674184982926243");
		h = new BigInteger("4820946344706800409");
	}

	@Test public void testQuadraticResidue() {
	}

	@After public void tearDown() {}

}
