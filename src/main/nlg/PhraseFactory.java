package src.main.nlg;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class PhraseFactory {


	private Lexicon lexicon = Lexicon.getDefaultLexicon();
	private	NLGFactory nlgFactory = new NLGFactory(lexicon);
	private	Realiser realiser = new Realiser(lexicon); 

	private PhraseFactory () {}

	public static PhraseFactory getInstance() {
		return new PhraseFactory();
	}

	public SPhraseSpec getClause () {
		return this.nlgFactory.createClause();
	}

	public void applyDependency (SPhraseSpec p, String lemma, String pos, String dep) {
	}

	public String realiseSentence(NLGElement e) {
		return this.realiser.realiseSentence(e);
	}

	public static void main (String[] args) {
	}
}
