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

	private Node root;
	private final int K;
	private LargeInteger p;
	private LargeInteger h;
	private ArrayList<Node> sentence;

	/**
	 * This nested class describes the node of a tree. Each node can have an arbitrary
	 * number of children. The class is private to hide the implementation of the tree
	 * from the client.
	*/
	private class Node {
		private String gov_rel;
		private String word;
		private String lemma;
		private String tag;
		private ArrayList<Node> dependents = new ArrayList<Node>();
		private int N;
		private int M;

		/* Instantiates the Node object.
		 *
		 * @param word The sentence level appearing in the word. If 
		 * 		the tag is phrase level the word is null.
		 * @param lemma The lemma of the word.
		 * @param tag The Penn Treebank II Tags which describes
		 * 	      the word or phrase
		*/
		public Node(String word, String lemma, String tag) {
			this.word = word; 
			this.lemma = lemma;
			this.tag = tag;
		}
	}

	/**
	 * Private constructor for ParseTree rather use 
	 * static factory method {@link getInstance()}.
	 *
	 * @param dep The simplified Stanford dependencies for the sentence.
	 * @param words The POS tags and lemmas of the sentence.
	 * @param K The setentence index.
	 */
	private DependencyTree(String dep, String words, int K) { 
		this.K = K;
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
	 */
	private DependencyTree(String dep, String words, LargeInteger p, LargeInteger h, int K) { 
		this.K = K;
		this.p = p;
		this.h = h;
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
	public static DependencyTree getInstance(String dep, String lemma, int K) {
		return new DependencyTree(dep, lemma, K);
	}


	/**
	 * Preferable method of instantiation. Used to initialise the DependencyTree object.
	 *
	 * @param dep The simplified Stanford dependencies for the sentence.
	 * @param words The POS tags and lemmas of the sentence.
	 * @param p BigInteger representation of a 20 digit prime.
	 * @param h The truncated hash of p.
	 * @param K The sentence index.
	 * @return A DependencyTree object.
	 */
	public static DependencyTree getInstance(String dep, String lemma, LargeInteger p, LargeInteger h, int K) {
		return new DependencyTree(dep, lemma, p, h, K);
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
		seed[0] = new Node(null, null, "ROOT");

		for (int i = 0; i < words.length; i++) {
			tk = words[i].split("\u00A0");
			seed[i+1] = new Node(tk[1], tk[2], tk[3]);
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
			node[i].dependents.add(node[j]);
			node[j].gov_rel = tk[2];
		}
		this.root = node[0];
	}

	/** 
	 * This function performs a pre-order traversal of the tree assigning each node
	 * a consecutive number based on order of inspection. This function is recursively 
	 * called by each node in the tree.
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
		for (Node n : node.dependents) j = preOrder(n, j);
		return j;
	}


	/** 
	 * Generate the binary string of the parse tree as described by Atallah et. al (2001).
	 *
	 * <p> Complexity: O(NM), where N is the number of node is the tree and M is the number 
	 * of bits in (p-1)'s binary representation, typically 64.
	 *
	 *@return The binary string.
	 */
	public String getBinaryString () {
		this.preOrder();
		return getBinaryString(this.root, new StringBuilder()).toString();
	}

	/**
	 * Generate the binary string of the parse tree.This function is called recursively
	 * by every node in the tree.This is a post-order travesal of the tree assigning 
	 * each node a bit based on whether it is a quadratic residue or not.
	 *
	 * <p> Complexity: O(NM), where N is the number of node is the tree and M is the number 
	 * of bits in (p-1)'s binary representation, typically 64.
	 *
	 * @param node The current node during traversal
	 * @param sb StringBuilder constructing the binary string.
	 * @return A StringBuilder of the binaryString.
	 */
	private StringBuilder getBinaryString (Node node, StringBuilder sb) {
		if (node == null) return sb;
		for (Node n : node.dependents) sb = getBinaryString(n, sb);
		if (TonelliShanks.isQuadraticResidue(this.h.add(node.N), this.p)) sb.append("1");
		else sb.append("0");
		return sb;
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
		for (Node n : node.dependents) j = postOrder(n, j);
		node.N = j++;
		return j;
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
		StringBuilder sb = new StringBuilder();
		for (Node n : sentence) {
			if (n.word != null) sb.append(n.word + " ");
		}
		return sb.toString();
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
	private void getSentence (Node nod) {
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
	private StringBuilder sketchTree(Node node, StringBuilder sb, int j) {
		if(node == null) return sb;
		for(int i = 0; i < j; i++) sb.append("    ");
		j++;
		if (node.word == null) sb.append(node.tag + "\n");
		else sb.append(node.gov_rel + " => " + node.word + " (" + node.tag + ")\n");
		for(Node n : node.dependents) sb = sketchTree(n, sb, j);
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
		DependencyTree tree = DependencyTree.getInstance(dep, lemma, 0);
		System.out.println(tree.toString());
	}
}
