package src.main.pos;

import src.main.utils.POSTags;
import src.main.trees.ReadFile;
import java.util.HashMap;

public class ProcessPOS {

	private HashMap<String, Integer> map = POSTags.getMap();
	private String [] sentences;

	private ProcessPOS (String file) {
		posToInteger(ReadFile.fileToString( file ));
	}

	private void posToInteger (String file) {
		String[] s = file.split("\n\n");
		this.sentences = new String[s.length];
		String[] w, t;
		for (int i = 0; i < s.length; i++) {
			w = s[i].split("\n");
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < w.length; j++) {
				t = w[j].split("\u00A0");
				//sb.append(this.map.get(t[3]) + " ; ");
				sb.append(t[3] + " ");
			}
			sentences[i] = sb.toString();
		}
	}

	private void printTags () {
		for (int i = 0; i < sentences.length; i++) {
			System.out.println(sentences[i]);
		}
	}

	public static void main (String [] args) {
		ProcessPOS pos = new ProcessPOS(args[0]);
		pos.printTags();
	}

}
