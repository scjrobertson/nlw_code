/*************************************************************************
 *  Compilation:  javac src/main/trees/Tree.java
 *  Execution:    java src.main.trees.Tree
 *  Dependencies: none
 *
 *  Abstract tree structure.
 *************************************************************************/
package src.main.trees;

/**
 * An abstract tree sturcture.
 *
 * @author SCJ Robertson, 16579852
 * @since 13/08/15
 */
public abstract class Tree {

	/**
	 * This nested class describes the node of a tree. Each node can have an arbitrary
	 * number of children. The class is private to hide the implementation of the tree
	 * from the client.
	*/
	abstract class Node {};


	/** 
	 * Generate the binary string of the parse tree as described by Atallah et. al (2001).
	 *
	 *@return The binary string.
	 */
	abstract String getBinaryString();


	/** 
	 * Recreate the original sentence described by the tree. 
	 *
	 * @return The sentence desribed by the parse tree.
	 */
	abstract String getSentence();
	 

	/** 
	 * This function performs a pre-order traversal of the tree assigning each node
	 * a consecutive number based on order of inspection. 	
	 */
	abstract void preOrder();

	/** 
	 * This function performs a post-order traversal of the tree assigning each node
	 * a consecutive number based on order of inspection. 	
	 */
	abstract void postOrder();
}
