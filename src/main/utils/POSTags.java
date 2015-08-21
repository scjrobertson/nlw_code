/*************************************************************************
 *  Compilation:  javac src/main/utils/POSTags.java
 *  Execution:    java src.main.utils.POSTags
 *  Dependencies: none
 *
 *  Enum type for the Penn Treebank II tags.
 *************************************************************************/
package src.main.utils;
import java.util.HashSet;

/**
 * This enum type merely lists all the Penn Treebank II tags.
 *
 *@author SCJ Robertson
 *@since 01/08/15
 */
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
	VBZ ("VBZ"),
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
	RRB ("-RRB-"),
	QTM("\"");

	private String tag;

	/**
	 * Initialises the enum type.
	 *
	 * @param tag The tag type.
	 */
	POSTags (String tag) {
		this.tag = tag;
	}

	/**
	 * Returns a hashset of tags for convenience.
	 *
	 *@return A Hashset of the tags.
	 */
	public static HashSet <String> getTags() {
		HashSet <String> set = new HashSet<String>();
		for (POSTags p : POSTags.values()) set.add(p.tag);
		return set;
	}

	/**
	 * A small usage example.
	 *
	 * @param args Standard input.
	 */
	public static void main (String[] args) {
		HashSet<String> set = POSTags.getTags();
		System.out.println(set.contains("CC"));
		System.out.println(POSTags.CC);
	}

}
