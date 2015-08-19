package src.main.trees;

import src.main.utils.HashAlgorithms;
import src.main.utils.LargeInteger;
import src.main.nlg.PhraseFactory;
import java.util.Random;

public class RunMain {
	
	private RunMain() {}

	public static void main (String [] args) {
		LargeInteger p = LargeInteger.probablePrime(64, new Random());
		LargeInteger h = HashAlgorithms.getHash("SHA-1", p.toString(), 8);
		PhraseFactory factory = PhraseFactory.getInstance();

		if (args.length > 0) {
			ParseTree[] parse = ReadFile.processParse(args[0], p, h, factory);
			DependencyTree[] dep = ReadFile.processDependency(args[1], args[2], p, h);
			/*
			for (int i = 0; i < parse.length; i++) {
				System.out.println(parse[i].getSentence() + "\n" + parse[i] + "\n"
						+ dep[i] + "\n" + parse[i].getBinaryString() + "\n");
			}
			*/
			
			for (int i = 0; i < parse.length; i++) {
				if ( TreeTransforms.hasFeature(dep[i].root, "VBD subj")) {
					System.out.println(i + "\t" + parse[i].getSentence());
					System.out.println(dep[i]);
				}
			}
		}
	}
}
