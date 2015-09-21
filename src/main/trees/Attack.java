/*************************************************************************
 *  Compilation:  javac src/main/trees/Attack.java
 *  Execution:    java src.main.trees.Attack
 *  Dependencies: none
 *
 * This is a library of static methods used to attack the aready embedded watermark.
 * Attacks include random and precise deletion, shuffling and conjunction.
 *************************************************************************/
package src.main.trees;
import src.main.nlg.NLG;
import src.main.utils.HashAlgorithm;
import src.main.utils.LargeInteger;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.HashMap;

/**
 * This is a library of static methods used to attack the aready embedded watermark.
 * Attacks include random and precise deletion, shuffling and conjunction.
 *
 *@author SCJ Robertson, 16579852
 *@since 17/09/15
 */
public class Attack {

	private static final int PERC_DEL = 10;
	private static final int PRECISION = 10;
	private static final int SHUFFLE = 10;
	private static final int PRIME_LENGTH = 64;
	private static final int HASH_LENGTH = 8;
	private static String HASH = "SHA-1";
	public static String ADR = "src/main/nlg/";
	public static String [] FILES = { ADR + "nouns.txt", ADR + "verbs.txt", ADR + "adverbs.txt",
					ADR + "adjectives.txt", ADR + "prepositions.txt"};

	/**
	 * Class of static methods.
	 */
	private Attack() {}


	/**
	 * Opens a text file containing an embedded message and reads the
	 * sentences.
	 *
	 * @param fileName The location and name of the file
	 * @return An array of plain text sentences.
	 */
	public static String[] plainText (String fileName) {
		return ReadFile.fileToString(fileName).trim().split("\n");
	}

	/**
	 * Deletes a preset amount of randomly selected sentences from the
	 * plain text.
	 *
	 * @param text An array of plain text sentences, containing a watermark.
	 * @return An Array of plain text sentences. The input array with 
	 * deleted entries.
	 */
	public static String[] randomDelete(String[] text) {
		ArrayList<String> t = new ArrayList<String> ();
		HashSet<Integer> keys = new HashSet<Integer> ();
		int L = text.length;
		int N = (int) ( L*(1.0*PERC_DEL/100) );

		while (keys.size() < N) keys.add( (int) (Math.random()*L) );
		for (int i = 0; i < L; i++) {
			if (!keys.contains(i)) t.add( text[i] );
		}

		return t.toArray( new String [t.size()] );

	}

	/**
	 * Deletes a sentences with a high probability of being watermark
	 * sentences.Uses a preset probability.
	 *
	 * @param text An array of plain text sentences, containing a watermark.
	 * @param dep The plain text's sentences corresponding dependency trees.
	 * @return An Array of plain text sentences. The input array with 
	 * deleted entries.
	 */
	public static String[] preciseDelete(String [] text, DependencyTree[] dep) {
		ArrayList<String> t = new ArrayList<String>();
		
		for (int i = 0; i < dep.length; i++) {
			int r = (int) (Math.random()*PRECISION);
			if ( !( TreeTransforms.isPassive(dep[i]) && ( r == 0 )  ) ) {
				t.add(text[i]);		
			} else System.out.println(text[i]);
		}

		return t.toArray( new String [t.size()] );
	}

	/**
	 * Randomly shuffles the plain text sentences.
	 *
	 * @param text An array of plain text sentences, containing a watermark.
	 * @return An Array of plain text sentences. The a shuffled variant of the
	 * input sentences.
	 */
	public static String [] shuffle (String [] text) {
		String [] t = Arrays.copyOfRange(text, 0, text.length);
		HashMap<Integer, Integer> swaps = new HashMap<Integer, Integer>();
		int L = text.length;
		int N = (int) ( L*(1.0*PERC_DEL/100) );

		while ( swaps.size() < N) {
	
			int r1 = (int) (Math.random()*L);
			int r2 = (int) (Math.random()*L);

			if ( !( r1 == r2 && swaps.containsKey(r1) &&  swaps.containsValue(r2) 
						&& swaps.containsKey(r2) && swaps.containsValue(r1) ) ) {
				String s = t[r1];
				t[r1] = t[r2];
				t[r2] = s;
				swaps.put(r1, r2);
			}
		}
		
		return t;
	}

	/**
	 * Randomly selects a sentence and joins it with it successor with the 
	 * conjunction "and".
	 *
	 * @param text An array of plain text sentences, containing a watermark.
	 * @return An Array of plain text sentences. The input array with deleted
	 * entries.
	 */
	public static String [] conjunction(String [] text) {
		ArrayList<String> t = new ArrayList<String>();
		HashSet<Integer> keys = new HashSet<Integer> ();
		int L = text.length;
		int N = (int) ( L*(1.0*PERC_DEL/100) );

		while (keys.size() < N) keys.add( (int) (Math.random()*(L-1) ) );
		for (int i : keys) System.out.println(i);
		for (int i = 0; i < text.length; i++) {
			if (keys.contains(i)) {
				text[i] = text[i].replace(".", "");
				t.add(text[i] + "and " + text[++i]);
			} else t.add(text[i]);
		}

		return t.toArray( new String [t.size()] );
	}

	/**
	 * Randomly inserts a sentence in the plain text.
	 *
	 * @param text An array of plain text sentences, containing a watermark.
	 * @return An Array of plain text sentences. The input array with inserted
	 * entries.
	 */
	public static String[] insertion(String [] text) {
		ArrayList<String> t = new ArrayList(Arrays.asList(text));
		HashSet<Integer> keys = new HashSet<Integer>();
		NLG nlg = NLG.getInstance(FILES, 6);
		int L = t.size();
		int N = (int) ( L*(1.0*PERC_DEL/100) );

		while (keys.size() < N) keys.add( (int) (Math.random()*(L-1) ) );
		for (int i : keys) t.add(i, nlg.generateSentence());
		return  t.toArray( new String [t.size()] );
	}


	/**
	 * Coordinates the attacks.Input is from standard input, with the following format:
	 * <p> Path to plain text, path to dependencies, path to lemma, attack type 
	 *
	 * @param args An array of input paths and attack types.
	 */
	public static void main (String args[]) {
		HashAlgorithm sha = HashAlgorithm.getInstance(HASH, HASH_LENGTH);
		LargeInteger p = LargeInteger.probablePrime(PRIME_LENGTH, new Random()); 
		LargeInteger h = sha.hashString(p.toString());
		String [] text;
		String [] attacked = null;
		DependencyTree[] dep;

		if (args.length >= 0)
		{
			text = plainText(args[0]);

			switch (args[3]) {
				case "0":
					attacked = randomDelete (text);
					break;
				case "1":
					dep = ReadFile.processDependency(args[1], args[2], p, h, sha );
					attacked = preciseDelete(text, dep);
					break;
				case "2":
					attacked = shuffle(text);
					break;
				case "3":
					attacked = conjunction(text);
					break;
				case "4":
					attacked = insertion(text);
					break;
				default:
					break;
			}
			for (int i = 0; i < attacked.length; i++) System.out.println(attacked[i]);
		}
	}


}
