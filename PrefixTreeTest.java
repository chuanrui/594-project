import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PrefixTreeTest {

	@Test
	public void testPrefixSearch() {
		PrefixTree tree = new PrefixTree();
		tree.insert("student");
		tree.insert("study");
		tree.insert("studio");
		tree.insert("stand");
		tree.insert("standard");
		tree.insert("stanford");
		tree.insert("star");
		tree.insert("start");
		tree.insert("stare");
		tree.insert("sheet");
		tree.insert("she");
		tree.insert("sherlock");
		
		ArrayList<String> list1 = tree.prefixSearch("stud");
		assertTrue(list1.contains("student"));
		assertTrue(list1.contains("study"));
		assertTrue(list1.contains("studio"));
		

		ArrayList<String> list2 = tree.prefixSearch("sta");
		assertTrue(list2.contains("stand"));
		assertTrue(list2.contains("standard"));
		assertTrue(list2.contains("stanford"));
		assertTrue(list2.contains("start"));
		assertTrue(list2.contains("stare"));

		ArrayList<String> list3 = tree.prefixSearch("she");
		assertTrue(list3.contains("she"));
		assertTrue(list3.contains("sheet"));
		assertTrue(list3.contains("sherlock"));
		
		assertTrue(tree.prefixSearch("zoo") == null);	
	}

	@Test
	public void testInsert() {
		PrefixTree tree = new PrefixTree();
		tree.insert("student");
		tree.insert("study");
		tree.insert("studio");
		tree.insert("stand");
		tree.insert("standard");
		tree.insert("stanford");
		tree.insert("star");
		tree.insert("start");
		tree.insert("stare");
		tree.insert("sheet");
		assertEquals(false, tree.insert("sheet"));
		assertEquals(true, tree.insert("sherlock"));	
	}

}
