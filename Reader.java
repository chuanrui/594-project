import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Reader {
	
	/**
	 * total words in the dictionary
	 */
	private HashSet<String> dictionary;
	
	/**
	 * filename and valid words in it
	 */
	private HashMap<String,List<String>> fileMap;
	
	/**
	 * Lemmatizer
	 */
	private Lemmatizer lem = new Lemmatizer();
	
	/**
	 * stopwords
	 */
	private HashSet<String> stopwords;
	
	/**
	 * stopword file
	 */
	private static final String STOP_WORD = "stopwords.txt";
	
	/**
	 * Constructor
	 * create a stopword list; create the filename:word List map
	 * @param path
	 * @throws Exception
	 */
	public Reader(String path) throws Exception {
		this.stopwords = new HashSet<String>();
		this.dictionary = new HashSet<String>();
		File file = new File(STOP_WORD);
		Scanner stop = new Scanner(file);
		while(stop.hasNextLine()) {
			String s = stop.nextLine().trim();
			if(!s.isEmpty()) {
				this.stopwords.add(s);
			}
			
		}
		//System.out.println(stopwords);
		this.fileMap = read(path);
		
	}
	
	/**
	 * read the path and return a map of String and valid words
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private HashMap<String,List<String>> read(String path) throws Exception {
		HashMap<String,List<String>> map= new HashMap<String,List<String>>();
		File file = new File(path);
		File[] fileList = file.listFiles();
		for(int i = 0;i < fileList.length;i++) {
			String filename = fileList[i].toString();
			List<String> wordList = readLine(fileList[i]);
			map.put(filename, wordList);
		}
//		this.fileMap = map;
		return map;
	}
	
	/**
	 * Analysis each file and add word to dictionary
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private List<String> readLine(File f) throws Exception{
		
		Long filelength = f.length();
		byte[] filecontent = new byte[filelength.intValue()];
		InputStream in = new FileInputStream(f);
		in.read(filecontent);
		in.close();
		String documentText = new String(filecontent);
		List<String> lemma = lem.lemmatize(documentText);
		List<String> words = new ArrayList<String>();
		
		for(String word : lemma) {
			if(isValidWord(word)) {
				this.dictionary.add(word);
				words.add(word);
			}
		}
		return words;
	}
	
	/**
	 * 
	 * @return dictionary
	 */
	public HashSet<String> getDictionary(){
		return dictionary;
	}
	
	/**
	 * tell valid word which contains only char
	 * @param s
	 * @return
	 */
	private boolean isValidWord(String s) {
		if(s.equals("")) {
			return false;
		}
		if(stopwords.contains(s)) {
			return false;
		}
		for(int i = 0;i < s.length();i++) {
			if(!Character.isAlphabetic(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @return fileMap
	 */
	public HashMap<String,List<String>> getFileMap(){
		return fileMap;
	}
	
}
