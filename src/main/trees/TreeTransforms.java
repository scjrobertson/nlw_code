
/*************************************************************************
 *  Compilation:  javac src/main/trees/TreeTransforms.java
 *  Execution:    java src.main.trees.TreeTransforms
 *  Dependencies: none
 *
 *  Performs syntactic transformations on ParseTrees.
 *************************************************************************/
package src.main.trees;
import src.main.utils.POSTags;
import java.util.Arrays;
import java.util.HashSet;

/**
 * This is a library of functions that perform syntactic transformations on parse trees.
 *
 * @author SCj Robertson
 * @since 15/08/15
 */
public class TreeTransforms {


	static HashSet<String> set = POSTags.getTags(); 
	//System.out.println(ParseTree.sketchTree(s, new StringBuilder(), 0));

	/**
	 * A static library of functions.
	 */
	private TreeTransforms() {}

	/**
	 * Find a particular node in a tree by specifying its path.
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by POS tags.
	 * @return The root of the subtree.
	 */
	static Node findSubtree (Node node, String tags) {
		Node n =  findSubtree(node, tags.split(" "), 0);
		return n;
	}


	/**
	 * Find a particular node in a tree by specifying its path. Recursively called
	 * by each node in the specified path.
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by POS tags.
	 * @return The root of the subtree.
	 */
	private static Node findSubtree (Node node, String [] tags, int i) {
		for (Node n : node.children) {
			if (i == tags.length) break;
			else if (n.tag.equals(tags[i])) {
				return findSubtree(n, tags, ++i);
			} 
		}
		return node;
	}


	/**
	 * Remove a particular node specified by its path.
	 *
	 * <p> Complexity: O(2lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by POS tags.
	 * @return The root of the subtree to be removed.
	 */
	static Node removeSubtree (Node node, String tags) {
		String[] tg = tags.split(" ");
		Node parent = findSubtree(node, Arrays.copyOfRange(tg, 0, tg.length-1), 0);
		Node child = findSubtree(node, tg, 0);
		parent.children.remove(child);
		return child;
	}


	/**
	 * Add a node to another by specifying by its path. 
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param subtree The root of the subtree to be added.
	 * @param tags The path to be followed. Described by POS tags.
	 * @param i The position in which the subtree will be added.
	 */
	static void addSubtree (Node root, Node subtree, String tags, int i) {
		Node parent = findSubtree(root, tags);
		parent.children.add(i, subtree);
	}

	/**
	 * Add a node to another by specifying by its path. 
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by POS tags.
	 */
	static void addSubtree (Node root, Node subtree, String tags) {
		Node parent = findSubtree(root, tags);
		parent.children.add(subtree);
	}


	/**
	 * Removes the full stop from a parse tree.
	 *
	 * <p> Complexity: Constant
	 *
	 * @param node The root of the tree.
	 */
	static void removeFullStop (Node root) {
		Node s = root.children.get(0);
		s.children.remove(s.children.size() -1);
	}
	

	/**
	 * Changes the applicable sentence to its passive voice.The sentence must
	 * have a transitive verb with a direct object for passivisation.
	 *
	 * <p> Complexity: O(2M + 6lgN) Here M is the length of PP and N is the 
	 * number of nodes is the tree.
	 *
	 * @param node The root of the tree.
	 */
	static void passiveVoice (Node root) {
		removeFullStop(root);
		Node pp = ParseTree.getInstance("( ROOT ( PP ( IN by ) ) )").root;
		Node v = ParseTree.getInstance("( ROOT ( VP ) )").root;

		addSubtree( pp, removeSubtree(root, "S NP"), "PP");
		addSubtree( root, removeSubtree(root, "S VP NP"), "S", 0);	
		addSubtree( v, new Node("VBP", "was"), "VP");
		addSubtree( v, removeSubtree(root, "S VP"), "VP");
		addSubtree( v, pp.children.get(0), "VP VP");
		addSubtree( root, v.children.get(0), "S");
	}


	/**
	 * Changes the passive sentence to its active voice.The sentence must have the
	 * past participle of a transitive verb and an indirect object.
	 * The agent cannot be missing.
	 *
	 * <p> Complexity: O(2M + 6lgN) Here M is the length of PP and N is the 
	 * number of nodes is the tree.
	 *
	 * @param node The root of the tree.
	 */
	static void activeVoice (Node root) {
		Node pp = removeSubtree(root, "S VP VP PP");
		Node verb = removeSubtree( removeSubtree(root, "S VP"), "VP VP");
		
		addSubtree(verb, removeSubtree(root, "S NP"), "VP");
		addSubtree(root, removeSubtree(pp, "NP"), "S");
		addSubtree(root, verb, "S");
	}

	public void preAdjunct (Node noot) {
		
	}

	public void postAdjunct (Node root) {

	}

}
