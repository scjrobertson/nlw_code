/*************************************************************************
 *  Compilation:  javac src/main/trees/ParseTree.java
 *  Execution:    java src.main.trees.ParseTree
 *  Dependencies: none
 *
 *  Recreates a parse tree from a Stanford s-expression.
 *************************************************************************/
package src.main.trees;
import src.main.utils.POSTags;
import src.main.utils.LargeInteger;
import src.main.utils.TonelliShanks;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.Random;
import java.security.MessageDigest;

/**
 * This object is given a Stanford syntactic parse tree s-expression and recreates the it.
 * Functionality is limited to basic traversals and binary string generations. Parse trees 
 * are difficult to modify as they do not embody the dependency relationships between words 
 * and subtrees cannot be added to perform transformations. This is merely a basic example of
 * tree creation. This is not a binary tree as it is also capable of handling punctuation 
 * which add to phrase Tags and destroy the binary decomposition of the sentence.
 *
 *@author SCJ Robertson, 16579852
 *@since 01/08/15
 */
public class ParseTree extends Tree {

	private Node root;
	private HashSet<String> set = POSTags.getTags(); 
	private String sentence, binaryString;
	private LargeInteger p, h;
	private int M;

	/**
	 * This nested class describes the node of a tree. Each node can have an arbitrary
	 * number of children. The class is private to hide the implementation of the tree
	 * from the client.
	*/
	private class Node {
		private String tag, word;
		private ArrayList<Node> children = new ArrayList<Node> ();
		private int N;

		/* Instantiates the Node object.
		 *
		 * @param tag The Penn Treebank II Tags which describes
		 * 	      the word or phrase
		 * @param word The sentence level appearing in the word. If 
		 * 		the tag is phrase level the word is null.
		*/
		public Node(String tag, String word) {
			this.tag = tag; this.word = word;
		}
	}
	
	/**
	 * Private constructor for ParseTree rather use 
	 * static factory method {@link getInstance()}.
	 *
	 * @param sExp The s-expression describing the parse tree.
	 */
	private ParseTree (String sExp) { 
		growTree(sExp); 
	}

	/**
	 * Private constructor for ParseTree rather use 
	 * static factory method {@link getInstance()}.
	 *
	 * @param sExp The s-expression describing the parse tree.
	 * @param p A BigInteger representation of a 20 digit prime.
	 * @param h The truncated hash of p.
	 * @param M The sentence sequence index.
	 */
	private ParseTree (String sExp, LargeInteger p, LargeInteger h, int M) { 
		this.p = p;
		this.h = h;
		this.M = M;
		growTree(sExp); 
	}

	/**
	 * Preferable method of instantiation. Used to initialise the ParseTree object.
	 *
	 * @param sExp The s-expression describing the parse tree.
	 * @return A ParseTree object.
	 */
	public static ParseTree getInstance(String sExp) {
		return new ParseTree(sExp);
	}
	
	/**
	 * Preferable method of instantiation. Used to initialise the ParseTree object.
	 *
	 * @param sExp The s-expression describing the parse tree.
	 * @param p A BigInteger representation of a 20 digit prime.
	 * @param h The truncated hash of p.
	 * @param M The sentence sequence index.
	 * @return A ParseTree object.
	 */
	public static ParseTree getInstance(String sExp, LargeInteger p, LargeInteger h, int M) {
		return new ParseTree(sExp, p, h, M);
	}

	/**
	 * This method parses the s-expression reconstructing the parse tree. This is 
	 * a simplified version of Dijkstra's two stack algorithm. 
	 *
	 *<p> Complexity: O(N), where N is the number of tokens in the s-expression.
	 *
	 * @param sExp The s-expression describing the parse tree.
	 */
	private void growTree(String sExp) {
		ArrayList<Node> sapling = new ArrayList<Node>();
		String[] tokens = sExp.split("\\s+");
		int N = 0;

		for(int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals(")")) {
				this.root = sapling.remove(--N);
				if (sapling.isEmpty()) break;
				sapling.get(N-1).children.add(this.root);
			} else if (!tokens[i].equals("(")) {
				if (this.set.contains(tokens[i])) sapling.add(new Node(tokens[i], tokens[++i]));
				else sapling.add(new Node(tokens[i], null));
				N++;
			}	
		}
	}

	/** 
	 * This function performs a pre-order traversal of the tree assigning each node
	 * a consecutive number based on order of inspection. 	
	 *
	 * <p> Complexity: O(N), where N is the number of nodes in the tree.
	 */
	public void preOrder () { preOrder(this.root, 0); }


	/** 
	 * This function performs a pre-order traversal of the tree assigning each node
	 * a consecutive number based on order of inspection. This function is recursively 
	 * called by each node in the tree.
	 *
	 * <p> Complexity: O(N), where N is the number of nodes in the tree.
	 *
	 * @param node The current node during traversal.
	 * @param j The number assigned to the previous node.
	 * @return The current node number, based onorder of inspection.
	 */
	private int preOrder (Node node, int j) {
		if (node == null) return j;
		node.N = j++;
		for (Node n : node.children) j = preOrder(n, j);
		return j;
	}


	/** 
	 * This function performs a post-order traversal of the tree assigning each node
	 * a consecutive number based on order of inspection. This function is recursively 
	 * called by each node in the tree.
	 *
	 * Complexity: O(N), where N is the number of nodes in the tree.
	 */
	public void postOrder () { postOrder(this.root, 0); }


	/** 
	 * This function performs a post-order traversal of the tree assigning each node
	 * a consecutive number based on order of inspection. This function is recursively 
	 * called by each node in the tree.
	 *
	 * <p> Complexity: O(N), where N is the number of nodes in the tree.
	 *
	 * @param node The current node during traversal.
	 * @param j The number assigned to the previous node.
	 * @return The current node number based on order of inspection.
	 */
	private int postOrder (Node node, int j) {
		if (node == null) return j;
		for (Node n : node.children) j = postOrder(n, j);
		node.N = j++;
		return j;
	}

	/** 
	 * Generate the binary string of the parse tree as described by Atallah et. al (2001).
	 *
	 * <p> Complexity: O(NM), where N is the number of node is the tree and M is 
	 * the number of bits in p-1 binary representation, typically 64.
	 *
	 *@return The binary string.
	 */
	public String getBinaryString () {
		preOrder();
		return getBinaryString(this.root, new StringBuilder()).toString();
	}

	/**
	 * Generate the binary string of the parse tree.This function is called recursively
	 * by every node in the tree.This is a post-order travesal of the tree assigning 
	 * each node a bit based on whether it is a quadratic residue or not.
	 *
	 * <p> Complexity: O(NM), where N is the number of node is the tree and M is 
	 * the number of bits in p-1 binary representation, typically 64.
	 *
	 * @param node The current node during traversal
	 * @param sb StringBuilder constructing the binary string.
	 * @return A StringBuilder of the binaryString.
	 */
	private StringBuilder getBinaryString (Node node, StringBuilder sb) {
		if (node == null) return sb;
		for (Node n : node.children) sb = getBinaryString(n, sb);
		if (TonelliShanks.isQuadraticResidue(this.h.add(node.N), this.p)) sb.append("1");
		else sb.append("0");
		return sb;
	}


	/** 
	 * Recreate the original sentence described by the tree. This function is recursively 
	 * called by each node in the tree.
	 *
	 * <p> Complexity: O(N), where N is the number of nodes in the tree.
	 * 
	 * @return The sentence desribed by the parse tree.
	 */
	public String getSentence () {
		if (sentence == null) sentence = getSentence(this.root, new StringBuilder()).toString();
		return sentence;
	}

	/** 
	 * Recreate the original sentence described by the tree. This function is recursively 
	 * called by each node in the tree.
	 *
	 * <p> Complexity: O(N), where N is the number of nodes in the tree.
	 *
	 * @param node The current node during traversal.
	 * @param sb The sentence under construction. Stringbuilder passed by reference to
	 * 		avoid memory overhed of immutable string class.
	 * @return The current sentence fragment under construction.
	 */
	private StringBuilder getSentence (Node node, StringBuilder sb) {
		if (node == null) return sb;
		if (node.word != null) {
			String word = node.word;
			if (word.equals("-LRB-")) word = "(";
			else if (word.equals("-RRB-")) word = ")";
			if (this.set.contains(word)) sb.deleteCharAt(sb.length()-1);
			sb.append(word + " ");
		}
		for (Node n : node.children) sb = getSentence(n, sb);
		return sb;
	}

	/**
	 * Generate the sentence described by the parse tree.
	 *
	 * @return The sentence described by the parse tree.
	 */
	public String generateSentence () { return null;  }

	/**
	 * Create an easily readable representation of the tree. This a relatively expensive 
	 * operation,don't call lightly.
	 *
	 * <p> Complexity: O(NlgN), where N is the number of nodes in the tree. 
	 * Lazy implementation, there are probably methods to reduce to linear 
	 * order of complexity.
	 *
	 * @return The text representation of the parse tree.
	 */
	@Override public String toString() {
		return sketchTree(this.root, new StringBuilder(), 0).toString();
	}


	/**
	 * Create an easily readable representation of the tree. This function is recursively 
	 * called by each node in the tree. This a relatively expensive operation,
	 * don't call lightly.
	 *
	 * <p> Complexity: O(NlgN), where N is the number of nodes in the tree. 
	 * Lazy implementation, there are probably methods to reduce to linear 
	 * order of complexity.
	 *
	 * @param node The current node during traversal.
	 * @param sb The sentence under construction. Stringbuilder passed by reference to
	 * 		avoid memory overhed of immutable string class.
	 * @param j The depth of the node. Used for spacing.
	 * @return The parse tree string under construction
	 */
	private StringBuilder sketchTree (Node node, StringBuilder sb, int j) {
		if (node == null) return sb;
		for (int i = 0; i < j; i++) sb.append("  ");
		j++;
		if (node.word == null) sb.append(node.tag + "\n");
		else sb.append(node.tag + " => " + node.word + "\n");
		for (Node n : node.children) sb = sketchTree(n, sb, j);
		return sb;
	}

	/**
	 * Changes the appropriate sentence from its active voice to its passive voice.
	 */
	 void passiveVoice () {
		Node root, subj, obj, verb, pntr, pp;
		
		root = this.root.children.get(0);
		subj = root.children.get(0);
		pntr  = root.children.get(1);
		obj = pntr.children.get(1);

		pntr.children.add(0, new Node ("VBP", "was"));
		verb = pntr.children.get(1);
		verb.tag = "VBN";

		pp = new Node ("PP", null);
		pp.children.add(new Node("IN", "by"));
		pp.children.add(subj);

		pntr.children.get(1).children.add(pp);
		
		root.children.remove(subj);
		root.children.add(0, obj);
		pntr.children.remove(obj);

	}


	/**
	 * A small example of the output. 
	 *
	 * @param args Standard input.
	 */
	public static void main(String[] args) {
		String sexp = "( ROOT ( S ( NP ( DT The ) ( NN girl ) ) "
			+" ( VP ( VBD chased ) ( NP ( DT the ) ( NN rabbit ) ) ) ) )" ;
		ParseTree tree = ParseTree.getInstance(sexp);
		System.out.println(tree.toString());
		System.out.println(tree.getSentence());
	}

}
