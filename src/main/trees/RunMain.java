package src.main.trees;
import src.main.utils.HashAlgorithms;
import src.main.utils.LargeInteger;
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
			
			System.out.println("# Sentences:\t" + parse.length);

			int j = 0;
			for (int i = 0; i < parse.length; i++) {
				if ( TreeTransforms.breadth(dep[i].root,"root") <= 3 && TreeTransforms.hasFeature(dep[i].root, "VBD dobj") 
						&& TreeTransforms.hasFeature(dep[i].root, "VBD nsubj" )) {
					System.out.println(parse[i].getSentence() + "\n" + parse[i].getBinaryString());
					TreeTransforms.passiveVoice(parse[i].root);
					System.out.println(parse[i].getSentence() + "\n" + parse[i].getBinaryString());
					j++;
				}
				if ( TreeTransforms.breadth(dep[i].root,"root") >= 2 && TreeTransforms.hasFeature(dep[i].root, "VBD nsubpass") 
						&& TreeTransforms.hasFeature(dep[i].root, "VBD auxpass")) {
					//System.out.println(parse[i].getSentence() + "\n" + parse[i] + "\n" + dep[i] +"\n");
					System.out.println(parse[i].getSentence() + "\n" + parse[i] + "\n" + dep[i] +"\n");
					TreeTransforms.activeVoice(parse[i].root);
					System.out.println(parse[i].getSentence() + "\n" + parse[i] + "\n" + dep[i] +"\n");
					j++;
				}

			}
			System.out.println(j);
		}
	}
}
