import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class PrefixTree implements IPrefixTree {
	/**
	 * the root node of the prefixTree
	 */
	PrefixTreeNode root;

	public PrefixTree() {
		root = new PrefixTreeNode();
	}

	@Override
	public ArrayList<String> prefixSearch(String word) {
		// first get the target node
		PrefixTreeNode target = prefixSearchNode(word);
		// if there is no target contains prefix, return null
		if (target == null)
			return null;
		return findKeywords(target, word);
	}

	@Override
	public boolean insert(String tag) {
		PrefixTreeNode temp = root;
		// see if we need to add new node;
		boolean addNew = false;
		for (int level = 0; level < tag.length(); level++) {
			String curChar = String.valueOf(tag.charAt(level));
			if (!temp.getChildren().containsKey(curChar)) {
				// see if it is the keyword node
				boolean isKeyWord = (level == tag.length() - 1) ? true : false;
				PrefixTreeNode newChild = new PrefixTreeNode(curChar, isKeyWord, temp);
				temp.addChildren(newChild);
				addNew = true;
			}
			temp = temp.getChildren().get(curChar);
			// if contain a tag in the path of other tags, we need to indicate it
			if (level == tag.length() - 1 && !temp.isKeyword()) {
				temp.makeKeyword();
				addNew = true;
			}
		}
		return addNew;
	}

	/**
	 * find the target node from which we want to search all possible keywords (tags)
	 * 
	 * @param word
	 * @return the target node where the word is represented
	 */
	private PrefixTreeNode prefixSearchNode(String word) {
		PrefixTreeNode temp = root;
		for (int level = 0; level < word.length(); level++) {
			String curChar = String.valueOf(word.charAt(level));
			// if there is no such possible keywords
			if (!temp.getChildren().containsKey(curChar))
				return null;
			temp = temp.getChildren().get(curChar);
		}
		return temp;
	}

	/**
	 * find all possible tags from the target node using dfs algorithm
	 * @param target
	 * @param word
	 * @return list of all possible tags
	 */
	private ArrayList<String> findKeywords(PrefixTreeNode target, String word) {
		// start at the target
		PrefixTreeNode temp = target;
		// stack to implement DFS algorithm
		Stack<PrefixTreeNode> dfsQ = new Stack<PrefixTreeNode>();
		// vist array to store nodes have been visited
		LinkedList<PrefixTreeNode> visited = new LinkedList<PrefixTreeNode>();
		ArrayList<String> keywords = new ArrayList<String>();
		dfsQ.push(temp);
		while (!dfsQ.isEmpty()) {
			// indicate we have not find a keyword yet
			boolean updateQ = false;
			temp = dfsQ.peek();
			// choose a unvisited child to continue dfs
			for (PrefixTreeNode child : temp.getChildren().values()) {
				if (!visited.contains(child)) {
					// push the child to stack
					dfsQ.push(child);
					visited.add(child);
					updateQ = true;
					break;
				}
			}
			// if we did not push anything to the dfsQ
			if (!updateQ) {
				temp = dfsQ.pop();
				// if pop node contain keyword, add it to list
				if (temp.isKeyword()) {
					String keyword = getKeyword(target, temp, word);
					keywords.add(keyword);
					//System.out.println(keyword);
				}
			}
		}
		return keywords;
	}

	/**
	 * find the tag represented in the keyword node
	 * @param target
	 * @param temp
	 * @param word
	 * @return the tag represented in current keyword node
	 */
	private String getKeyword(PrefixTreeNode target, PrefixTreeNode temp, String word) {
		String get = "";
		while (temp != target) {
			get = temp.getCurChar() + get;
			temp = temp.getParent();
		}
		return word + get;
	}

}