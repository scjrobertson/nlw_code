package src.main.trees;
import src.main.utils.LargeInteger;
import java.util.Comparator;

class RankComparator implements Comparator <Tree> {
	
	public int compare (Tree one, Tree two) {
		return one.getRank().compareTo(two.getRank());
	}
}
