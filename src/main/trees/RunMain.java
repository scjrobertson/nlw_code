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
import java.util.Arrays;

/**
 * Runs the embedding and extraction process.
 *
 *@author SCJ Robertson, 16579852
 *@since 19/08/15
 */
public class RunMain {
	
	/**
	 * Library of static methods.
	 */
	private RunMain() {}


	public static void embed ( ParseTree[] parse, DependencyTree[] dep, 
			SortedSet<Tree> s, int[] msg ) {
		int j;
		for ( int i = 0; i < msg.length; i++ ) {
			j = s.first().getK() + 1;

			if ( j == parse.length ) i--;
			else if ( TreeTransforms.isSimple( dep[j] ) && msg[i] == 1) {
				TreeTransforms.passiveVoice( parse[j].root );
				if ( s.contains( parse[j] ) ) {
					s.remove( parse[j] );
					parse[j].assignRank();
					s.add( parse[j] );
				}
			} 
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
			System.out.println(msg[i] + "\t" + dep[j].getSentence() );
		}
		return msg;
	}

	/**
	 * Runs the embedding process.
	 *
	 * @param args The file names of the parse, dependency and lemma files
	 */
	public static void main (String [] args) {
		HashAlgorithm sha = HashAlgorithm.getInstance("SHA-1", 8);
		LargeInteger p = LargeInteger.probablePrime(64, new Random());
		LargeInteger h = sha.hashString(p.toString()); 

		p = LargeInteger.getInstance("12172044259271309873");
		h = LargeInteger.getInstance("17988218695207327599");

		int N = 48;
		int[] msg = new int[N];
		for (int i = 0; i < N; i++) {
			msg[i] = i%2;
		}


		if (args.length >= 4) {
			ParseTree[] parse = ReadFile.processParse( args[0], p, h, sha );
			DependencyTree[] dep = ReadFile.processDependency( args[1], args[2], p, h, sha );

			SortedSet <Tree> s = new TreeSet <Tree> ( new RankComparator() );
			s.addAll(Arrays.asList(parse));

			if ( args[3].equals("0") ) {			
				embed(parse, dep, s, msg);
				for (int i = 0; i < parse.length; i++) System.out.println(parse[i].getSentence());
			} else if ( args[3].equals("1") ) {	
				int [] rec = extract(dep, s, msg.length);
				int ber = 0;
				for (int i = 0; i < msg.length; i++) ber += msg[i] ^ rec[i];
				System.out.println("Bit error rate: " + ( 100.0*ber )/N );
			}


		}
	}
}
