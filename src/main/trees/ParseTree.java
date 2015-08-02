package src.main.trees;

import src.main.utils.StdIn;
import java.util.ArrayList;
import java.util.HashSet;

public class ParseTree {

	private Node root;
	private HashSet<String> set = POSTags.getTags(); 
	private String sentence;

	private class Node {
		private String tag;
		private String word;
		private ArrayList<Node> children = new ArrayList<Node> ();
		private int N;

		public Node(String tag, String word) {
			this.tag = tag; this.word = word;
		}
	}

	private ParseTree (String sExp) {
		parseSExp(sExp);
	}

	public static ParseTree getInstance(String sExp) {
		return new ParseTree(sExp);
	}

	private void parseSExp(String sExp) {
		ArrayList<Node> sapling = new ArrayList<Node>();
		String[] tokens = sExp.split("\\s+");
		int N = 0;

		for(int i = 0; i < tokens.length; i++) {
			if (tokens[i].equals(")")) {
				this.root = sapling.remove(--N);
				if (N == 0) break;
				sapling.get(N-1).children.add(this.root);
			}
			else if (!tokens[i].equals("(")) {
				if (this.set.contains(tokens[i])) sapling.add(new Node(tokens[i], tokens[++i]));
				else sapling.add(new Node(tokens[i], null));
				N++;
			}	
		}
	}

	public void preOrder () { preOrder(this.root, 0); }

	private int preOrder (Node node, int j) {
		if (node == null) return j;
		node.N = j++;
		if (node.word != null) System.out.println(node.word);
		for (int i = 0; i < node.children.size(); i++) j = preOrder(node.children.get(i), j);
		return j;
	}

	public void postOrder () { postOrder(this.root, 0); }

	private int postOrder (Node node, int j) {
		if (node == null) return j;
		for (int i = 0; i < node.children.size(); i++) j = postOrder(node.children.get(i), j);
		node.N = j++;
		return j;
	}

	public String getSentence () {
		if (sentence == null) sentence = buildSentence(this.root, new StringBuilder()).toString();
		return sentence;
	}

	public StringBuilder buildSentence (Node node, StringBuilder sb) {
		if (node == null) return sb;
		if (node.word != null) {
			String word = node.word;
			if (word.equals("-LRB-")) word = "(";
			else if (word.equals("-RRB-")) word = ")";
			if (this.set.contains(word)) sb.deleteCharAt(sb.length()-1);
			sb.append(word + " ");
		}
		for (int i = 0; i < node.children.size(); i++) sb = buildSentence(node.children.get(i), sb);
		return sb;
	}

	@Override public String toString() {
		return sketchTree(this.root, new StringBuilder(), 0).toString();
	}

	public StringBuilder sketchTree (Node node, StringBuilder sb, int j) {
		if (node == null) return sb;
		for (int i = 0; i < j; i++) sb.append("  ");
		j++;
		if (node.word == null) sb.append(node.tag + "\n");
		else sb.append(node.tag + " => " + node.word + "\n");
		for (int i = 0; i < node.children.size(); i++) sb = sketchTree(node.children.get(i), sb, j);
		return sb;
	}

	public static void main(String[] args) {
		String sexp = StdIn.readAllLines()[0].trim();
		sexp = sexp.replace("(", " ( ").replace(")", " ) ");
		System.out.println(sexp);
		ParseTree tree = ParseTree.getInstance(sexp);
		System.out.println(tree.toString());
	}

}
