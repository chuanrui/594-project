import java.util.HashMap;

public class PrefixTreeNode {

	// The current character
	private String curChar;
	// the sub nodes
	private HashMap<String, PrefixTreeNode> children;
	// whether this is the last character of a word
	private boolean isKeyword;
	private PrefixTreeNode parent;
	private boolean visited;
	
	/**
	 * The constructor
	 * @param s
	 * @param children
	 */
	public PrefixTreeNode(String curChar, boolean isKeyWord, PrefixTreeNode parent) {
		this.curChar = curChar;
		children = new HashMap<String, PrefixTreeNode>(); 
		this.isKeyword = isKeyWord;
		this.parent = parent;
	}
	
	public PrefixTreeNode() {
		curChar = "";
		children = new HashMap<String, PrefixTreeNode>(); 
		isKeyword = false; 
		parent = null;
	}
	
	public String getCurChar() {
		return curChar;
	}
	 
	public void setCurChar(String curChar) {
		this.curChar = curChar;
	}
	
	public PrefixTreeNode getParent() {
		return parent;
	}
	
	public void setParent(PrefixTreeNode parent) {
		this.parent = parent;
	}

	public HashMap<String, PrefixTreeNode> getChildren() {
		return children;
	}
	
	public void addChildren(PrefixTreeNode newChild) {
		// put new child into hashmap
		children.put(newChild.curChar, newChild);
	}
	
	public boolean isKeyword() {
		return isKeyword;
	}
	
	public void makeKeyword() {
		this.isKeyword = true;
	}
	
}