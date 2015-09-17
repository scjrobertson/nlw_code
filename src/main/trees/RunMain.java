/*************************************************************************
 *  Compilation:  javac src/main/trees/RunMain.java
 *  Execution:    java src.main.trees.RunMain
 *  Dependencies: none
 *
 *  Runs the embedding and extraction process.
 *************************************************************************/
package src.main.trees;

import src.main.utils.HashAlgorithm;
import src.main.utils.LargeInteger;
import java.util.Random;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Runs the embedding and extraction process.
 *
 *@author SCJ Robertson, 16579852
 *@since 19/08/15
 */
public class RunMain {
	
	private static int MSG_LENGTH = 10;
	private static int PRIME_LENGTH = 64;
	private static int HASH_LENGTH = 8;
	private static String HASH = "SHA-1";

	/**
	 * Library of static methods.
	 */
	private RunMain() {}


	public static void embed ( ParseTree[] parse, DependencyTree[] dep, 
			SortedSet<Tree> s, int[] msg ) {
		HashSet <Tree> discarded = new HashSet <Tree> ();
		int j;
		for ( int i = 0; i < msg.length; i++ ) {
			j = s.first().getK() + 1;

			if ( j == parse.length || discarded.contains( parse[j] ) );
			else if ( TreeTransforms.isSimple( dep[j] ) && msg[i] == 1) {
				TreeTransforms.passiveVoice( parse[j].root );
				if ( s.contains( parse[j] ) ) {
					s.remove( parse[j] );
					parse[j].assignRank();
					s.add( parse[j] );
				}
			} 
			discarded.add( s.first() );
			s.remove( s.first() );
		}	
	}


	public static int[] extract (DependencyTree[] dep, SortedSet<Tree> s, int msgl ) {
		
		int j;
		int [] msg = new int[msgl];

		for ( int i = 0; i < msgl; i++ ) {
			j  = s.first().getK() + 1;
			
			if ( j == dep.length ) i--;
			else if ( TreeTransforms.isPassive(dep[j]) ) msg[i] = 1;
			else msg[i] = 0;
			
			s.remove( s.first() );
		}
		return msg;
	}

	public static int [] generateMessage (SortedSet <Tree> s) {
		int N = (int) ( s.size()*(1.0*MSG_LENGTH/100) );
		int [] msg = new int [N];
		for (int i = 0; i < N; i++) msg[i] = (int) ( Math.random()*2 );
		return msg;
	}

	public static void printOutput (ParseTree[] parse, LargeInteger p, LargeInteger h, int[] msg) {
		System.out.println(p + "\n" + h);
		for (int i = 0; i < msg.length; i++) System.out.print(msg[i] + "\t");
		System.out.println("\n");
		for (int i = 0; i < parse.length; i++) System.out.println(parse[i].getSentence());
	}

	/**
	 * Runs the embedding process.
	 *
	 * @param args The file names of the parse, dependency and lemma files
	 */
	public static void main (String [] args) {
		HashAlgorithm sha = HashAlgorithm.getInstance(HASH, HASH_LENGTH);
		//LargeInteger p = LargeInteger.probablePrime(PRIME_LENGTH, new Random()); 
		LargeInteger p = LargeInteger.getInstance("11055716757729592891");
		LargeInteger h = sha.hashString(p.toString());
		ParseTree[] parse; DependencyTree[] dep; 
		SortedSet <Tree> s = new TreeSet <Tree> ( new RankComparator() );
		int [] msg;

		if (args.length >= 4) {

			if ( args[3].equals("0") ) {
				parse = ReadFile.processParse( args[0], p, h, sha );
				dep = ReadFile.processDependency( args[1], args[2], p, h, sha );
				
				s.addAll(Arrays.asList(parse));
				msg = generateMessage(s);

				embed(parse, dep, s, msg);
				printOutput(parse, p, h, msg);

			} else if ( args[3].equals("1") ) {
				msg = ReadFile.getKey(args[4], p, h);
				parse = ReadFile.processParse( args[0], p, h, sha );
				dep = ReadFile.processDependency( args[1], args[2], p, h, sha );

				s.addAll(Arrays.asList(parse));
				int [] rec = extract(dep, s, msg.length);
				int ber = 0;

				for (int i = 0; i < msg.length; i++) ber += msg[i] ^ rec[i];
				System.out.println( (100.0*ber )/msg.length + ";" + p);
			}


		}
	}
}
