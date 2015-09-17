package src.main.trees;
import src.main.utils.HashAlgorithm;
import src.main.utils.LargeInteger;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.HashMap;

public class Attack {

	private static final int PERC_DEL = 10;
	private static final int PRECISION = 10;
	private static final int SHUFFLE = 10;
	private static final int PRIME_LENGTH = 64;
	private static final int HASH_LENGTH = 8;
	private static String HASH = "SHA-1";

	private Attack() {}

	public static String[] plainText (String fileName) {
		return ReadFile.fileToString(fileName).trim().split("\n");
	}

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
				default:
					break;
			}
			for (int i = 0; i < attacked.length; i++) System.out.println(attacked[i]);
		}
	}


}
