package src.main.trees;
import java.util.HashSet;

public enum POSTags {
	CC ("CC"),
	CD ("CD"),
	DT ("DT"),
	EX ("EX"),
	FW ("FW"),
	IN ("IN"),
	JJ ("JJ"),
	JJR ("JJR"),
	LS ("LS"),
	MD ("MD"),
	NN ("NN"),
	NNS ("NNS"),
	NNP ("NNP"),
	NNPS ("NNPS"),
	PDT ("PDT"),
	POS ("POS"),
	PRP ("PRP"),
	PRPS ("PRP$"),
	RB ("RB"),
	RBR ("RBR"),
	RBS ("RBS"),
	RP ("RP"),
	SYM ("SYM"),
	TO ("TO"),
	UH ("UH"),
	VB ("VB"),
	VBD ("VBD"),
	VBG ("VBG"),
	VBN ("VBN"),
	VBP ("VBP"),
	VBZ ("WBZ"),
	WPS ("WP$"),
	WRB ("WRB"),
	CMM (","),
	FSTP ("."),
	QM ("?"),
	HYP ("-"),
	EXC ("!"),
	CLN (":"),
	SCLN (";"),
	LRB ("-LRB-"),
	RRB ("-RRB-");

	private String tag;

	POSTags (String tag) {
		this.tag = tag;
	}

	public static HashSet <String> getTags() {
		HashSet <String> set = new HashSet<String>();
		for (POSTags p : POSTags.values()) set.add(p.tag);
		return set;
	}

	public static void main (String[] args) {
		HashSet<String> set = POSTags.getTags();
		System.out.println(set.contains("CC"));
	}

}
