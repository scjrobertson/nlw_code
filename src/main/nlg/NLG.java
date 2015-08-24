
/*************************************************************************
 *  Compilation:  javac src/main/nlg/NLG.java
 *  Execution:    java src.main.nlg.NLG
 *  Dependencies: none
 *
 *  Generates pseudo-random simple sentences.
 *************************************************************************/
package src.main.nlg;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * This is a library of static methods used to generate simple
 * sentences from the input dictionaries.
 *
 *@author SCJ Robertson, 16579852
 *@since 24/08/15
 */
public class NLG {

	private Lexicon lexicon = Lexicon.getDefaultLexicon();
	private NLGFactory nlgFactory = new NLGFactory(lexicon);
	private Realiser realiser = new Realiser(lexicon);
	private String [] nouns, verbs, adverbs, adjectives, prepositions;
	private int N;


	/**
	 * Initialises the NLG object.
	 *
	 * @param fileNames The names of the noun, verb, adverb
	 * @param N The maximum amount of adverbs or adjectives for 
	 * a phrase element.
	 * adjective and preposition libraries.
	 */
	private NLG (String [] fileNames, int N) {
		this.nouns = fileToString ( fileNames[0] );
		this.verbs = fileToString ( fileNames[1] );
		this.adverbs = fileToString ( fileNames[2] );
		this.adjectives = fileToString ( fileNames[3] );
		this.prepositions = fileToString ( fileNames[4] );
		this.N = N;
	}


	/**
	 * Returns a NLG class.
	 *
	 * @param fileNames The names of the noun, verb, adverb
	 * adjective and preposition libraries.
	 * @param N The maximum amount of adverbs or adjectives for 
	 * a phrase element.
	 * @return A NLG class.
	 */
	public static NLG getInstance( String [] fileNames, int N ) {
		return new NLG ( fileNames, N );
	}

	/**
	 * Opens and reads a file and the output as a single string.
	 *
	 * @param file The name of the file.
	 * @return The contents of a file as an array of strings.
	 */
	private static String[] fileToString( String file ) {
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
		return sb.toString().split("\n");
	}


	/**
	 * Creates a random noun phrase, possibly with a determiner and up to
	 * N adjectives.
	 *
	 * @return A noun phrase
	 */
	private NPPhraseSpec createNounPhrase () {
		int r = getRandom( this.nouns.length );
		NPPhraseSpec np = this.nlgFactory.createNounPhrase( this.nouns[r] );
		
		r  = getRandom( this.N );
		int j;
		for ( int i = 0; i < r; i++ ) {
			j = getRandom( this.adjectives.length );
			np.addPreModifier( this.adjectives[j]  );
		}

		if ( getRandom( 3 ) == 0 ) np.setDeterminer("the");
		else if ( getRandom( 3 ) == 1 ) np.setDeterminer("a");

		return np;
	}

	/**
	 * Creates a random noun phrase, with  up to N adjectives.
	 *
	 * @return A verb phrase
	 */
	private VPPhraseSpec createVerbPhrase () {
		int r = getRandom( this.verbs.length );
		VPPhraseSpec vp = this.nlgFactory.createVerbPhrase( this.verbs[r] );
		
		
		r  = getRandom( 3 );
		int j;
		for ( int i = 0; i < r; i++ ) {
			j =getRandom( this.adverbs.length );
			vp.addPreModifier( this.adverbs[j]  );
		}
		return vp;
	}

	/**
	 * Creates a random prepositional phrase, with  up to N adjectives.
	 *
	 * @return A verb phrase
	 */
	private PPPhraseSpec createPrepPhrase () {
		int r = getRandom( this.prepositions.length );
		
		PPPhraseSpec pp = this.nlgFactory.createPrepositionPhrase();
		pp.setPreposition( this.prepositions[r] );

		NPPhraseSpec np = createNounPhrase();
		if ( getRandom( 2 ) == 0 ) np.setDeterminer("the");
		else if ( getRandom( 2 ) == 1 ) np.setDeterminer("a");
		pp.addComplement(np);

		return pp;
	}


	/**
	 * Create a simple sentence from the available libraries, every N
	 * of which will be passive.
	 *
	 * @return A pseudo-random sentence.
	 */
	public String generateSentence () {
		NPPhraseSpec obj = createNounPhrase();
		VPPhraseSpec verb = createVerbPhrase();
		NPPhraseSpec subj = createNounPhrase();
		SPhraseSpec s = this.nlgFactory.createClause( subj, verb, obj );
		s.setFeature( Feature.TENSE, Tense.PAST );
		return this.realiser.realiseSentence( s );		
	}

	/**
	 * Get a random integer between 0 and N.
	 *
	 * @param N The upper bound
	 * @return A random intger between 0 and N.
	 */
	private int getRandom ( int N ) {
		int r = (int) ( Math.random()*N ) - 1;
		if ( r < 0 ) r++;
		return r;
	}

	/**
	 * Runs main.
	 *
	 * @param args Standard input
	 */
	public static void main (String [] args) {
		NLG nlg = NLG.getInstance( args, 4 );
		for (int i = 0; i < 1000; i++ ) {
			System.out.println( nlg.generateSentence()  );
		}
	}
	
}
