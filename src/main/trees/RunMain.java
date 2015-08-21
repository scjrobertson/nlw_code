package src.main.trees;
import src.main.utils.HashAlgorithm;
import src.main.utils.LargeInteger;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Arrays;

public class RunMain {
	
	private RunMain() {}

	public static void main (String [] args) {
		HashAlgorithm sha = HashAlgorithm.getInstance("SHA-1", 8);
		LargeInteger p = LargeInteger.probablePrime(64, new Random());
		LargeInteger h = sha.hashString(p.toString());

		if (args.length > 0) {
			ParseTree[] parse = ReadFile.processParse(args[0], p, h, sha);
			DependencyTree[] dep = ReadFile.processDependency(args[1], args[2], p, h, sha);

			SortedSet<Tree> trees = new TreeSet<Tree>(new RankComparator());
			trees.addAll(Arrays.asList(parse));
	
			int i = 0;
			for (Tree t : trees) {
				i++;
				System.out.println(t.K + "\n" + t.getRank() + "\n\t" + t.getSentence());
			}
			System.out.println("# Sentence: " + "\t" + i);
		}
	}
}
