import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ReaderTest {

	Reader reader;
	@Before
	public void setUp() throws Exception {
		reader = new Reader("text");
	}

//	@Test
//	public void testReader() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetDictionary() {
		Set<String> dictionary = reader.getDictionary();
		assertEquals(21599, dictionary.size());
	}

	@Test
	public void testGetFileMap() {
		HashMap<String,List<String>> fileMap = reader.getFileMap();
		assertEquals(96, fileMap.size());
	}

}
