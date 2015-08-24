
/*************************************************************************
 *  Compilation:  javac src/main/trees/TreeTransforms.java
 *  Execution:    java src.main.trees.TreeTransforms
 *  Dependencies: none
 *
 *  Performs syntactic transformations on ParseTrees.
 *************************************************************************/
package src.main.trees;
import src.main.utils.POSTags;
import java.util.ArrayList;
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
	 * Determine the number of children for a given node.
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by POS and dependency tags.
	 * @return The number of children of a node.
	 */
	static int breadth (Node node, String tags) {
		return breadth(node, tags.split(" "), 0);
	}



	/**
	 * Determine the number of children for a given node.
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by POS and dependency tags.
	 * @param i The current tag index.
	 * @return The number of children of a node.
	 */
	private static int breadth (Node node, String [] tags, int i) {	
		for (Node n : node.children) {
			if (n.tag.equals(tags[i]) || n.gov_rel.equals(tags[i])) {
				if (i == tags.length -1) return n.children.size();
				return breadth(n, tags, ++i);
			}
		}
		return -1;
	}

	/**
	 * Determine whether a dependency tree has a particular feature.
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by POS and dependency tags.
	 * @return Whether the tree contains the desired feature.
	 */
	static boolean hasFeature (Node node, String tags) {
		return hasFeature(node, tags.split(" "), 0);
	}

	/**
	 * Determine whether a dependency tree has a particular feature.
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by depedency tags.
	 * @param i Current tag index.
	 * @return Whether the tree contains the desired feature.
	 */
	private static boolean hasFeature(Node node, String [] tags, int i) {
		for (Node n : node.children) {
			if (n.tag.equals(tags[i]) || n.gov_rel.equals(tags[i])) {
				if (i == (tags.length-1)) return true;
				return hasFeature(n, tags, ++i);
			}
		}
		return false;
	}

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
		return findSubtree(node, tags.split(" "), 0);
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
			if (n.tag.equals(tags[i]) || n.gov_rel.equals(tags[i])) {
				if (i == tags.length-1) return n;
				return findSubtree(n, tags, ++i);
			}
		}
		return null;
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
	 * Add a node's children to another node. 
	 *
	 * <p> Complexity: O(lgN) where N is the number of nodes in the tree.
	 *
	 * @param node The root of this tree.
	 * @param tags The path to be followed. Described by POS tags.
	 */
	static void addSubtree (Node root, ArrayList<Node> children, String tags) {
		Node parent = findSubtree(root, tags);
		for (Node n : children) parent.children.add(n); 
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
		addSubtree( v, pp.children, "VP VP");
		addSubtree( root, v.children, "S");
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
		removeFullStop(root);
		Node pp = removeSubtree(root, "S VP VP PP");
		Node verb = removeSubtree( removeSubtree(root, "S VP"), "VP VP");
		
		addSubtree(verb, removeSubtree(root, "S NP"), "VP");
		addSubtree(root, removeSubtree(pp, "NP"), "S");
		addSubtree(root, verb, "S");
	}


	/**
	 * Adds the abverbial phrase often beginning of a simple subj-vrb-obj sentence.
	 *
	 * <p> Complexity: Constant
	 *
	 * @param node The root of the tree.
	 */
	static void preAdjunct (Node root) {
		Node adj = ParseTree.getInstance("( ADVP ( RB Often )  )").root;
		addSubtree(root, adj, "S", 0);
	}


	/**
	 * Adds the abverbial phrase often end of a simple subj-vrb-obj sentence.
	 *
	 * <p> Complexity: Constant
	 *
	 * @param node The root of the tree.
	 */
	static void postAdjunct (Node root) {
		removeFullStop(root);
		Node adj = ParseTree.getInstance("ADVP ( RB Often )  )").root;
		addSubtree(root, adj, "S");
	}


	/**
	 * Removes the meaning-modifying transform adjunct transform.
	 *
	 * <p> Complexity: Constant
	 *
	 * @param node The root of the tree.
	 */
	static void removeAdjunct (Node root) {
		removeSubtree(root, "S ADVP");
	}


	/**
	 * Past tense it-cleft, changes the subject of a simple sentence to its
	 * focus.
	 *
	 * <p> Complexity: O(2M + 10lgN) Where N is the number of nodes in the tree.
	 * M <= N.
	 *
	 * @param node The root of the tree.
	 */
	static void cleft (Node root) {
		removeFullStop(root);
		Node sbar = ParseTree.getInstance("( ROOT ( SBAR ( IN that ) ( S  ) ) )").root;
		Node cleft = ParseTree.getInstance("( ROOT ( NP ( PRP It ) ) ( VP ( VBP was ) ) )").root;
		
		addSubtree(cleft, removeSubtree(root, "S VP NP"), "VP");
		addSubtree(sbar, removeSubtree(root, "S NP"), "SBAR S");
		addSubtree(sbar, removeSubtree(root, "S VP"), "SBAR S");
		addSubtree(cleft, sbar.children, "VP");
		addSubtree(root, cleft.children, "S");
	}


	/**
	 * Removes past tense it cleft, complex sentence is reverted to a simple sentence.
	 *
	 * <p> Complexity: O(2M + 10lgN) Where N is the number of nodes in the tree.
	 * M <= N.
	 *
	 * @param node The root of the tree.
	 */
	static void removeCleft (Node root) {
		removeFullStop(root);
		Node sapling = ParseTree.getInstance("( ROOT ( S ) )").root;
		
		addSubtree(sapling, removeSubtree(root, "S VP SBAR S NP"), "S");
		addSubtree(sapling, removeSubtree(root, "S VP SBAR S VP"), "S");
		addSubtree(sapling, removeSubtree(root, "S VP NP"), "S VP", 1);
	}


	/**
	 * Determines whether a sentence is a simple subj-verb-obj sentence.
	 *
	 * @param t A Dependency tree
	 * @return Whether a sentence is simple or not.
	 */
	static boolean isSimple (Tree t) {
		if (t instanceof DependencyTree) {
			return (breadth(t.root, "root") >= 2 && hasFeature(t.root, "root nsubj") 
					&& hasFeature(t.root, "root dobj"));
			
		} else return false;
	}

	/**
	 * Determines whether a simple sentence is the passive voice.
	 *
	 * @param t A Dependency tree
	 * @return Whether a sentence is passive or not.
	 */
	static boolean isPassive (Tree t) {
		if (t instanceof DependencyTree) {
			return (breadth(t.root, "root") == 3 && hasFeature(t.root, "root nsubjpass") 
					&& hasFeature(t.root, "root auxpass"));
			
		} else return false;
	}
}
