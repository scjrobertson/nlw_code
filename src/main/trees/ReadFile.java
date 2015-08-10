/*************************************************************************
 *  Compilation:  javac src/main/trees/ReadFile.java
 *  Execution:    java src.main.trees.ReadFile
 *  Dependencies: none
 *
 *  Reads in files and recreates the pare of dependency trees.
 *************************************************************************/
package src.main.trees;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;
import java.security.MessageDigest;

/**
 * This is a library of static methods used to easily process large inputs.
 *
 *@author SCJ Robertson, 16579852
 *@since 06/08/15
 */
public class ReadFile {

	/**
	 * This is a library of static methods, no need to instantiate.
	 */
	private ReadFile() {}

	/**
	 * Opens and reads a file and the output is a single string.
	 *
	 * @param file The name of the file.
	 * @return The contents of a file as a single string.
	 */
	public static String fileToString(String file) {
		StringBuilder sb = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
		    	String line = br.readLine();
		    	while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
		    	}
		} catch (IOException e)	{
			throw new RuntimeException("File not found", e);
		}
		return sb.toString();
	}

	/**
	 * Creates an array of parse trees for the given s-expressions.
	 *
	 * @param parse The name of the file containing s-expressions.
	 * @param p BigInteger representation of a 20 digit prime.
	 * @param h The hash of the prime p
	 * @return An array of ParseTree objects.
	 */
	public static ParseTree[] processParse (String parse, BigInteger p, BigInteger h) {
		String[] sexp = fileToString(parse).split("\n\n");
		ParseTree[] forest = new ParseTree[sexp.length];
		for (int i = 0; i < sexp.length; i++) {
			sexp[i] = sexp[i].replace("(", " ( ").replace(")", " ) ");	
			forest[i] = ParseTree.getInstance(sexp[i], p, h, i);
		}
		return forest;
	}


	/**
	 * Creates an array of dependency trees for the given dependencies and postags of a 
	 * list of sentences.
	 *
	 * @param dep The dependencies describing the text, see format.
	 * @param pos The pos tags of the text, see format. 
	 * @param p BigInteger representation of a 20 digit prime.
	 * @param h The hash of the prime p
	 * @return An array of DependencyTree objects.
	 */
	public static DependencyTree[] processDependency (String dep, String pos, 
			BigInteger p, long h) {
		String[] deps = fileToString(dep).split("\n\n");
		String[] tags = fileToString(pos).split("\n\n");
		DependencyTree[] forest = new DependencyTree[tags.length];
		for (int i = 0; i < tags.length; i++) 
			forest[i] = DependencyTree.getInstance(deps[i], tags[i], i);
		return forest;
	}

	/**
	 * This function acts as a wrapper type for the MessageDigest's factory method, handling 
	 * the exceptions.
	 *
	 * @param alg The choice of algorithm. Possible choices include md5, SHA-1 and SHA-256
	 * @return A MessageDigest object initiliased for a particular hashing algorithm.
	 */
	public static MessageDigest getHashAlgorithm (String alg) {
		try { return MessageDigest.getInstance(alg); }
		catch (Exception e) { throw new RuntimeException("Algorithm not found", e); }
	}

	/**
	 * Returns a segment of a string's hash, itself a hash. 
	 *
	 * @param md A MessageDigest object intialised to a particular algorithm
	 * @param s The string to be hashed
	 * @param n The number bytes to be chosen from the total hash. SHA-1 algorithm is 20-byte
	 * 	    md5 is 16-byte, SHA-2 is 20-byte
	 * @return A BigInteger representing the hash.
	 */
	public static BigInteger getHash(MessageDigest md, String s, int n) {
		try {
			if (md.getDigestLength() < n) throw new AssertionError("n too large");
			md.update(s.getBytes("UTF-8"));
			byte[] b = Arrays.copyOfRange(md.digest(), 0, n);
			return new BigInteger(1, b);
		} catch (UnsupportedEncodingException e) { 
			throw new RuntimeException("Unsupported encoding expression", e);
		}
	}

	/** 
	 * Return whether a node label x is a quadratic residue of q
	 * modulo n.
	 *
	 * @param x The node label according to a pre-order traversal
	 * @param q The truncated hash of the p
	 * @param p A BigInteger representation of a 20 digit prime
	 * @return Whether x is a quadratic residue of q modulo p.
	 */
	public static boolean isQuadraticResidue (int x, BigInteger q, BigInteger p) {
		BigInteger xb = new BigInteger(x*(x-1) + "");
		BigInteger mod = xb.subtract(q).mod(p);
		if (mod.equals(BigInteger.ZERO)) return true;
		return false;
	}

	/**
	 * Run as main of package.Flags -p : parse s-expression file
	 * 				-d: parse dependency and pos files 
	 * 				    (requires two input files)
	 * @param args Standard input - flags and file names.
	 */
	public static void main (String [] args) {
		BigInteger p = BigInteger.probablePrime(64, new Random());
		MessageDigest md = getHashAlgorithm("SHA-1");
		BigInteger h = getHash(md, p.toString(), 8);
		System.out.println(p + "\t" + h);

		ParseTree[] forest = null;
		if (args.length > 0) {
			if (args[0].equals("-p")) 
				forest = processParse(args[1], p, h);
			//else if (args[0].equals("-d")) 
				//forest = processDependency(args[1], args[2], p, h.longValue());
			for (int i = 0; i < forest.length; i++)
				System.out.println(forest[i].getBinaryString());
		}
	}
}
