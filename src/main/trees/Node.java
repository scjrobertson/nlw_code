
/*************************************************************************
 *  Compilation:  javac src/main/trees/Tree.java
 *  Execution:    java src.main.trees.Tree
 *  Dependencies: none
 *
 *  Leaf for the Tree class.
 *************************************************************************/

package src.main.trees;
import java.util.ArrayList;

/**
 * Leaf for the Tree class. Each node can have an arbitrary
 * number of children. The class is package private to hide the implementation of the tree
 * from the client. Not nested in Tree as this creates an ungodly mess when it comes to 
 * instantiating new Node objects.
 *
 * @author SCJ Robertson, 16579852
 * @since 15/08/15
*/
class Node {

	ArrayList<Node> children = new ArrayList<Node> ();
	int N;
	String tag;
	String word;
	String gov_rel = "";

	/* Instantiates the Node object.
	 *
	 * @param tag The Penn Treebank II Tags which describes
	 * 	      the word or phrase
	 * @param word The sentence level appearing in the word. If 
	 * 		the tag is phrase level the word is null.
	*/
	Node (String tag, String word) {
		this.tag = tag;
		this.word = word;
	}

	@Override public String toString () {
		return tag + ":\t" + word;
	}
}
