import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ParserTest {

	Parser parser;
	HashSet<String> dictionary;
	HashMap<String,List<String>> map;
	List<String> list1;
	List<String> list2;
	List<String> list3;
	
	@Before
	public void setUp() throws Exception {
//		parser = new Parser("text");
		parser  = new Parser();
		dictionary = new HashSet();
		dictionary.add("one");
		dictionary.add("two");
		dictionary.add("three");
		dictionary.add("four");

		
		map = new HashMap<String,List<String>>();
		list1 =  new ArrayList<String>();
		list2 =  new ArrayList<String>();
		list3 =  new ArrayList<String>();
		list1.add("one");
		list1.add("two");
		list1.add("one");
		list2.add("one");
		list2.add("two");
		list2.add("two");
		list2.add("three");
		list3.add("one");
		list3.add("two");
		list3.add("three");
		list3.add("four");
		list3.add("four");
		map.put("list1", list1);
		map.put("list2", list2);
		map.put("list3", list3);
	}

	
	@Test
	public void tfTest() {
		Double epsilon = 0.000001;
		HashMap<String,HashMap<String,Double>> tf = parser.tf(map);
		assertEquals(1, tf.get("list1").get("one"),epsilon);
		assertEquals(0.5, tf.get("list2").get("one"),epsilon);
		
	}
	
	@Test
	public void idfTest() {
		Double epsilon = 0.00001;
		HashMap<String,Double> idf = parser.idf(map, dictionary);
		System.out.println(idf);
		assertEquals(0.0, idf.get("one"),epsilon);
	}
	
	
	
	@Test
	public void tfidfTest() {
		HashMap<String, HashSet<String>> tfidf = parser.tfIdf(map, dictionary);
//		System.out.println(tfidf);
		assertTrue(tfidf.get("four").contains("list3"));
	}

}
