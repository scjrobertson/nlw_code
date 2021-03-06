/*************************************************************************
 *  Compilation:  javac src/main/trees/DependencyTree.java
 *  Execution:    java src.main.trees.DependencyTree
 *  Dependencies: none
 *
 *  Recreates a dependecy tree from the simplified dependencies and POS
 *  tagging desribed by dep.xsl and lemma.xsl
 *************************************************************************/
package src.main.trees;
import src.main.utils.StdIn;
import java.util.Arrays;
import java.util.ArrayList;
import src.main.utils.TonelliShanks;
import src.main.utils.LargeInteger;
import src.main.utils.HashAlgorithm;
import java.util.Random;

/**
 * This object is given both a simplified POS and dependency expressions and recreates
 * the dependency tree.This is a basic collapsed Stanford dependency.Dependency trees 
 * are easier to modify in comparison to syntactic parse trees and are easier to integrate 
 * with simpleNLG.Subtrees can be added an the sentence expanded.
 *
 *@author SCJ Robertson, 16579852
 *@since 05/08/15
 */
public class DependencyTree extends Tree {

	protected ArrayList<Node> sentence;

	/**
	 * Private constructor for ParseTree rather use 
	 * static factory method {@link getInstance()}.
	 *
	 * @param dep The simplified Stanford dependencies for the sentence.
	 * @param words The POS tags and lemmas of the sentence.
	 * @param K The setentence index.
	 */
	private DependencyTree(String dep, String words) { 
		growTree(dep, plantTree(words)); 
	}

	/**
	 * Private constructor for ParseTree rather use 
	 * static factory method {@link getInstance()}.
	 *
	 * @param dep The simplified Stanford dependencies for the sentence.
	 * @param words The POS tags and lemmas of the sentence.
	 * @param p BigInteger representation of a 20 digit prime.
	 * @param h the truncated hash of p
	 * @param K The setentence index.
	 * @param hash The hash algorithm.
	 */
	private DependencyTree(String dep, String words, LargeInteger p, 
			LargeInteger h, int K, HashAlgorithm hash) { 
		this.K = K;
		this.p = p;
		this.h = h;
		this.hash = hash;
		growTree(dep, plantTree(words)); 
	}

	/**
	 * Preferable method of instantiation. Used to initialise the DependencyTree object.
	 *
	 * @param dep The simplified Stanford dependencies for the sentence.
	 * @param words The POS tags and lemmas of the sentence.
	 * @param K The sentence index.
	 * @return A DependencyTree object.
	 */
	public static DependencyTree getInstance(String dep, String lemma) {
		return new DependencyTree(dep, lemma);
	}

	/**
	 * Preferable method of instantiation. Used to initialise the DependencyTree object.
	 *
	 * @param dep The simplified Stanford dependencies for the sentence.
	 * @param words The POS tags and lemmas of the sentence.
	 * @param p BigInteger representation of a 20 digit prime.
	 * @param h The truncated hash of p.
	 * @param K The sentence index.
	 * @param hash The hash algorithm.
	 * @return A DependencyTree object.
	 */
	public static DependencyTree getInstance(String dep, String lemma, LargeInteger p, 
			LargeInteger h, int K, HashAlgorithm hash) {
		return new DependencyTree(dep, lemma, p, h, K, hash);
	}

	/**
	 * Creates an array of nodes which will be used to form the 
	 * dependencies.
	 *
	 *<p> Complexity: O(N), where N is the number of words in the sentence.
	 *
	 * @param pos A table of the words, tags and lemmas
	 * @return An array of Node objects.
	 */
	private Node[] plantTree(String pos) {
		String[] words = pos.split("\\n");
		Node[] seed = new Node[words.length + 1];
		String[] tk;
		seed[0] = new Node("ROOT", null);

		for (int i = 0; i < words.length; i++) {
			tk = words[i].split("\u00A0");
			seed[i+1] = new Node(tk[3], tk[1]);
		}
		this.sentence = new ArrayList<Node>(Arrays.asList(seed));
		return seed;
	}

	/**
	 * Creates the dependency tree from the given dependencies.
	 *
	 *<p> Complexity: O(N), where N is the number of dependecies.
	 *
	 * @param dep The dependencies describing the sentence.
	 * @param node The words of the sentence as compilied in {@link plantTree}
	 */
	private void growTree(String dep, Node[] node) {
		String[] dp = dep.split("\\n");
		String[] tk;
		int i,j;
		
		for (int k = 0; k < dp.length; k++) {
			tk = dp[k].split("\u00A0");
			i = Integer.parseInt(tk[0]);
			j = Integer.parseInt(tk[1]);
			node[i].children.add(node[j]);
			node[j].gov_rel = tk[2];
		}
		this.root = node[0];
	}

	/** 
	 * Recreate the original sentence described by the tree.
	 *
	 * <p> Complexity: O(N), where N is the number of nodes in the tree.
	 * 
	 * @return The sentence desribed by the parse tree.
	 */
	public String getSentence () {
		StringBuilder sb = new StringBuilder();
		for (Node n : this.sentence) if (n.word != null) sb.append(n.word + " ");
		return sb.toString();
	}

	/**
	 * Create an easily readable representation of the tree. This a relatively expensive operation,
	 * don't call lightly.
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
	StringBuilder sketchTree(Node node, StringBuilder sb, int j) {
		if(node == null) return sb;
		for(int i = 0; i < j; i++) sb.append("    ");
		j++;
		if (node.word == null) sb.append(node.tag + "\n");
		else sb.append(node.gov_rel + " => " + node.word + " (" + node.tag + ")\n");
		for(Node n : node.children) sb = sketchTree(n, sb, j);
		return sb;
	}

	/**
	 * A small example of the output. 
	 *
	 * @param args Standard input.
	 */
	public static void main(String [] args) {
		String dep = "0 3 root\n"
			+ "2 1 det\n"
			+ "3 2 nsubj\n"
			+ "5 4 det\n"
			+ "3 5 dobj";
		String lemma = "1 The the DT\n"
				+ "2 girl girl NN\n"
				+ "3 chased chase VBD\n"
				+ "4 the the DT\n"
				+ "5 rabbit rabbit NN";
		DependencyTree tree = DependencyTree.getInstance(dep, lemma);
		System.out.println(tree.toString());
	}
}
