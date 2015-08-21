/*************************************************************************
 *  Compilation:  javac src/main/utils/HashAlgorithms.java
 *  Execution:    java src.main.trees.HashAlgorithms
 *  Dependencies: none
 *
 *  A convenient method of hashing strings.
 *************************************************************************/
package src.main.utils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;

/**
 * This library contains several wrapper methods for conveniently hasing strings
 * without a large code overhead.
 *
 * @author SCJ Robertson, 16579852
 * @since 15/08/15
 */
public class HashAlgorithm {

	 private MessageDigest md;
	 private int N;

	 /**
	  * Initiliase the HashAlgorithms object.
	  *
	  * @param alg The hash algorithm.
	  * @param N The nuber of bytes in the truncated the hash.
	  */
	 private HashAlgorithm (String alg, int N) { 
		 this.md = getHashAlgorithm(alg);
		 this.N = N;
	 }

	 /**
	  * Initiliase the HashAlgorithms object.
	  *
	  * @param alg The hash algorithm.
	  * @param N The nuber of bytes in the truncated the hash.
	  * @return A Hash algorithm.
	  */
	 public static HashAlgorithm getInstance (String alg, int N) {
		 return new HashAlgorithm(alg, N);
	 }

	/**
	 * This function acts as a wrapper type for the MessageDigest's factory method, handling 
	 * the exceptions.
	 *
	 * @param alg The choice of algorithm. Possible choices include md5, SHA-1 and SHA-256
	 * @return A MessageDigest object initiliased for a particular hashing algorithm.
	 */
	private MessageDigest getHashAlgorithm (String alg) {
		try { return MessageDigest.getInstance(alg); }
		catch (Exception e) { throw new RuntimeException("Algorithm not found", e); }
	}

	/**
	 * Returns a n-byte segment of a string's hash. 
	 *
	 * @param md A MessageDigest object intialised to a particular algorithm
	 * @param s The string to be hashed
	 * @param n The number bytes to be chosen from the total hash. SHA-1 algorithm is 20-byte
	 * 	    md5 is 16-byte, SHA-2 is 20-byte
	 * @return A LargeInteger representing the hash.
	 */
	public LargeInteger hashString(String s) {
		try {
			if (md.getDigestLength() < this.N) throw new AssertionError("n too large");
			this.md.update(s.getBytes("UTF-8"));
			byte[] b = Arrays.copyOfRange(this.md.digest(), 0, this.N);
			return LargeInteger.getInstance(1, b);
		} catch (UnsupportedEncodingException e) { 
			throw new RuntimeException("Unsupported encoding expression", e);
		}
	}

	/**
	 * A small example of output.
	 *
	 * @param args Standard input - flags and file names.
	 */
	public static void main (String[] args) {
	}

}
