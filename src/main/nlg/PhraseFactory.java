package src.main.nlg;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class PhraseFactory {

	private Lexicon lexicon = Lexicon.getDefaultLexicon();
	private NLGFactory nlgFactory = new NLGFactory(lexicon);

	private PhraseFactory () {}

	public static PhraseFactory getInstance() {
		return new PhraseFactory();
	}

	public  NLGElement createPhrase () {
		return nlgFactory.createClause();
	}

	public static void main (String[] args) {
		Lexicon lexicon = Lexicon.getDefaultLexicon();
		NLGFactory nlgFactory = new NLGFactory(lexicon);
		Realiser realiser = new Realiser(lexicon);
		PhraseElement verb = nlgFactory.createVerbPhrase("chase");
		PhraseElement subj = nlgFactory.createNounPhrase("girl");
		PhraseElement obj = nlgFactory.createNounPhrase("rabbit");
		obj.setDeterminer("a");
		subj.setDeterminer("the");
		PhraseElement p = nlgFactory.createClause();
		verb.setParent(p);
		obj.setParent(p);
		subj.setParent(p);
		System.out.println(verb.getRealisation());

	}
}
