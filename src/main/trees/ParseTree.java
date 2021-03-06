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
import src.main.utils.HashAlgorithm;
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

	protected HashSet<String> set = POSTags.getTags(); 
	protected PhraseFactory factory;

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
	 * @param K The sentence sequence index.
	 * @param hash The hash algorithm.
	 */
	private ParseTree (String sExp, LargeInteger p, LargeInteger h, int K, 
			HashAlgorithm hash) { 
		this.p = p;
		this.h = h;
		this.K = K;
		this.hash = hash;
		growTree(sExp); 
		assignRank();
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
	 * @param K The sentence sequence index.
	 * @param hash The hash algorithm.
	 * @return A ParseTree object.
	 */
	public static ParseTree getInstance(String sExp, LargeInteger p, LargeInteger h, 
			int K, HashAlgorithm hash) {
		return new ParseTree(sExp, p, h, K, hash);
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
	 * Recreate the original sentence described by the tree. This function is recursively 
	 * called by each node in the tree.
	 *
	 * <p> Complexity: O(N), where N is the number of nodes in the tree.
	 * 
	 * @return The sentence desribed by the parse tree.
	 */
	public String getSentence () {
		return getSentence(this.root, new StringBuilder()).toString();
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
			sb.append(word + " ");
		}
		for (Node n : node.children) sb = getSentence(n, sb);
		return sb;
	}

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
	static StringBuilder sketchTree (Node node, StringBuilder sb, int j) {
		if (node == null) return sb;
		for (int i = 0; i < j; i++) sb.append("  ");
		j++;
		if (node.word == null) sb.append(node.tag + "\n");
		else sb.append(node.tag + " => " + node.word + "\n");
		for (Node n : node.children) sb = sketchTree(n, sb, j);
		return sb;
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
