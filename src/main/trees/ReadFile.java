/*************************************************************************
 *  Compilation:  javac src/main/trees/ReadFile.java
 *  Execution:    java src.main.trees.ReadFile
 *  Dependencies: none
 *
 *  Reads in files and recreates the pare of dependency trees.
 *************************************************************************/
package src.main.trees;
import src.main.utils.HashAlgorithms;
import src.main.utils.LargeInteger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import src.main.nlg.PhraseFactory;

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
	public static ParseTree[] processParse (String parse, LargeInteger p, LargeInteger h) {
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
	 * @param factory A phrase factory for sentence realisation.
	 * @return An array of DependencyTree objects.
	 */
	public static DependencyTree[] processDependency (String dep, String pos, LargeInteger p, LargeInteger h,
			PhraseFactory factory) {
		String[] deps = fileToString(dep).split("\n\n");
		String[] tags = fileToString(pos).split("\n\n");
		DependencyTree[] forest = new DependencyTree[tags.length];
		for (int i = 0; i < tags.length; i++) 
			forest[i] = DependencyTree.getInstance(deps[i], tags[i], p, h, i, factory);
		return forest;
	}

	/**
	 * Run as main of package.Flags -p : parse s-expression file
	 * 				-d: parse dependency and pos files 
	 * 				    (requires two input files)
	 * @param args Standard input - flags and file names.
	 */
	public static void main (String [] args) {
		LargeInteger p = LargeInteger.probablePrime(64, new Random());
		LargeInteger h = HashAlgorithms.getHash("SHA-1", p.toString(), 8);
		PhraseFactory factory = PhraseFactory.getInstance();
		System.out.println(p + "\t" + h + "\n");

		Tree[] forest = null;
		if (args.length > 0) {
			if (args[0].equals("-p")) forest = processParse(args[1], p, h);
			if (args[0].equals("-d")) forest = processDependency(args[1], args[2], p, h, factory);
			for (int i = 0; i < forest.length; i++) {  
				System.out.println(forest[i].getSentence() + "\n" + forest[i] + "\n" + forest[i].getBinaryString() + "\n");
  			}
		}
	}
}
