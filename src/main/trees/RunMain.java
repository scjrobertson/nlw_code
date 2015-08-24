package src.main.trees;

import src.main.utils.HashAlgorithm;
import src.main.utils.LargeInteger;
import java.util.Random;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Arrays;

public class RunMain {
	
	private RunMain() {}

	public static void main (String [] args) {
		HashAlgorithm sha = HashAlgorithm.getInstance("SHA-1", 8);
		LargeInteger p =  LargeInteger.probablePrime(64, new Random());
		LargeInteger h = sha.hashString(p.toString()); 

		int N = 12;
		int[] msg = new int[N];
		for (int i = 0; i < N; i++) {
			msg[i] = i%2;
		}


		if (args.length > 0) {
			ParseTree[] parse = ReadFile.processParse( args[0], p, h, sha );
			DependencyTree[] dep = ReadFile.processDependency( args[1], args[2], p, h, sha );

			SortedSet <Tree> s = new TreeSet <Tree> ( new RankComparator() );
			s.addAll(Arrays.asList(parse));

			System.out.println(s.size());

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
					System.out.println( parse[j].getSentence() );
				} else if ( msg[i] == 1 ) {
					System.out.println(parse[j].getSentence() + "\n" + dep[j] + "\n");
				}
				s.remove( s.first() );
			}
		}
	}
}
