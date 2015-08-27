/*************************************************************************
 *  Compilation:  javac src/main/utils/POSTags.java
 *  Execution:    java src.main.utils.POSTags
 *  Dependencies: none
 *
 *  Enum type for the Penn Treebank II tags.
 *************************************************************************/
package src.main.utils;
import java.util.HashSet;
import java.util.HashMap;

/**
 * This enum type merely lists all the Penn Treebank II tags.
 *
 *@author SCJ Robertson
 *@since 01/08/15
 */
public enum POSTags {
	CC ("CC", 0),
	CD ("CD", 1),
	DT ("DT", 2),
	EX ("EX", 3),
	FW ("FW", 4),
	IN ("IN", 5),
	JJ ("JJ", 6),
	JJR ("JJR", 7),
	LS ("LS", 8),
	MD ("MD", 9),
	NN ("NN", 10),
	NNS ("NNS", 11),
	NNP ("NNP", 12),
	NNPS ("NNPS", 13),
	PDT ("PDT", 14),
	POS ("POS", 15),
	PRP ("PRP", 16),
	PRPS ("PRP$", 17),
	RB ("RB", 18),
	RBR ("RBR", 19),
	RBS ("RBS", 20),
	RP ("RP", 21),
	SYM ("SYM", 22),
	TO ("TO", 23),
	UH ("UH", 24),
	VB ("VB", 25),
	VBD ("VBD", 26),
	VBG ("VBG", 27),
	VBN ("VBN", 28),
	VBP ("VBP", 29),
	VBZ ("VBZ", 30),
	WPS ("WP$", 31),
	WRB ("WRB", 32),
	CMM (",", 33),
	FSTP (".", 34),
	QM ("?", 35),
	HYP ("-", 36),
	EXC ("!", 37),
	CLN (":", 38),
	SCLN (";", 39),
	LRB ("-LRB-", 40),
	RRB ("-RRB-", 41),
	QTM("\"", 42);

	private String tag;
	private int i;

	/**
	 * Initialises the enum type.
	 *
	 * @param tag The tag type.
	 */
	POSTags (String tag, int i) {
		this.tag = tag;
		this.i = i;
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
	 * Returns a hashmap of tags and indexes for convenience.
	 *
	 * @return A HashMap of the tags and their indexes.
	 */
	public static HashMap <String, Integer> getMap() {
		HashMap <String, Integer> map = new HashMap<String, Integer> ();
		for (POSTags p : POSTags.values()) map.put(p.tag, p.i);
		return map;
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
