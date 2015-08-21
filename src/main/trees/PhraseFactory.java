package src.main.trees;
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


	public String realiseSentence(String s) {
		return this.realiser.realiseSentence(nlgFactory.createSentence(s));
	}

	public static void main (String[] args) {
		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon); 
		SPhraseSpec s = nlgFactory.createClause();
		NPPhraseSpec n = nlgFactory.createNounPhrase();
		n.setDeterminer("The");
		n.setNoun("Romans");
		VPPhraseSpec v = nlgFactory.createVerbPhrase();
		v.setVerb("attacked");
		PPPhraseSpec p = nlgFactory.createPrepositionPhrase();
		p.setPreposition("by");
		NPPhraseSpec np = nlgFactory.createNounPhrase();
		np.setNoun("villagers");
		np.setDeterminer("the");
		p.addComplement(np);
		v.setObject(p);
		s.setObject(n);
		s.setVerb(v);
		String out = realiser.realiseSentence(s);
		System.out.println(out);
	}
}
