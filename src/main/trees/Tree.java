/*************************************************************************
 *  Compilation:  javac src/main/trees/Tree.java
 *  Execution:    java src.main.trees.Tree
 *  Dependencies: none
 *
 *  Abstract tree structure.This superclass of all other tree classes
 *  and contains all functions common to both.
 *************************************************************************/
package src.main.trees;
import java.util.ArrayList;
import src.main.utils.LargeInteger;
import src.main.utils.TonelliShanks;
import src.main.utils.HashAlgorithm;

/**
 *  Abstract tree structure.This superclass of all other tree classes
 *  and contains all functions common to both.
 *
 * @author SCJ Robertson, 16579852
 * @since 13/08/15
 */
abstract class Tree {

	protected Node root;
	protected LargeInteger p, h;
	protected int K;
	protected HashAlgorithm hash;
	protected LargeInteger rank;

	/**
	 * Procted method, returns the tree.
	 *
	 * @return The root of the tree.
	 */
	protected Node getRoot() { return this.root;  };


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
	 * Determine the rank of a parse tree, R = H(B) XOR H(p). Here B is the binary string of
	 * a parse tree.
	 *
	 * @return The LargeInteger representation of R.
	 */
	void assignRank () {
		LargeInteger s = LargeInteger.getInstance(this.getBinaryString(), 2);
		LargeInteger b = this.hash.hashString(s.toString());
		this.rank =  b.xor(this.h);
	}


	/**
	 * Get the rank of a parse tree, R = H(B) XOR H(p). Here B is the binary string of
	 * a parse tree.
	 *
	 * @return The LargeInteger representation of R.
	 */
	public LargeInteger getRank () {
		return this.rank;
	}


	/**
	 * Returns the position of a sentence within the body of text.
	 *
	 * @return The sentence index.
	 */
	public int getK () {
		return this.K;
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
		for (Node n : node.children) sb = getBinaryString(n, sb);
		if (TonelliShanks.isQuadraticResidue(this.h.add(node.N), this.p)) sb.append("1");
		else sb.append("0");
		return sb;
	}

	/** 
	 * Recreate the original sentence described by the tree. 
	 *
	 * @return The sentence described by the parse tree.
	 */
	abstract String getSentence();
	
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
}
