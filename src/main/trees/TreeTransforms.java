package src.main.trees;

public class TreeTransforms {

	private TreeTransforms() {}

	protected void performTransform (Tree tree) {
		passiveVoice(tree.getRoot().children.get(0));
	}


	/**
	 * Changes the appropriate sentence from its active voice to its passive voice.
	 */
	 void passiveVoice (Node root) {
		Node subj, obj, verb, pntr, pp;
		
		//root = this.root.children.get(0);
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

}
