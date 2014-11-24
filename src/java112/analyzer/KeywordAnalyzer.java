package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * This is the KeywordAnalyzer class that implements the interface: analyzer.
 * Outputs keywords as keys to a Map and their occurences in a large text file
 * as their values.
 */
public class KeywordAnalyzer implements Analyzer {

	private Map<String, List<Integer>> keywordMap;
	private Properties properties;
	private int tokenOccurence;
	String totalString = "";

	public Map<String, List<Integer>> getKeywordMap() {
		return keywordMap;
	}

	/**
	 * Class constructor
	 */
	public KeywordAnalyzer() {
		keywordMap = new TreeMap<String, List<Integer>>();
	}

	/**
	 * Class constructor with one properties paramater
	 * 
	 * @param properties
	 */
	public KeywordAnalyzer(Properties properties) {
		this();
		this.properties = properties;
		readKeywordFile();
	}

	/**
	 * tokensLoop loops through the ArrayList tokens, checks for nulls then
	 * passes the token to the analyzers.
	 * 
	 * @param tokenArray
	 */
	public void tokensLoop(String[] tokenArray) {

		ArrayList<Integer> intList;

		for (String word : tokenArray) {
			if (word.length() > 0) {
				keywordMap.put(word, intList = new ArrayList());
			}
		}
	}

	/**
	 * This method holds a loop of inputed lines to be read and added to the
	 * array.
	 * 
	 * @param input
	 * @exception IOException
	 */
	public void inputLoop(BufferedReader input) throws IOException {

		String inputLine;
		String[] tokenArray;

		while (input.ready()) {
			inputLine = input.readLine();
			tokenArray = inputLine.split("\\W");
			tokensLoop(tokenArray);
		}
	}

	/**
	 * This method instantiates the BufferedReader and calls the inputLoop
	 * method.
	 * 
	 * @exception FileNotFoundException
	 * @exception Exception
	 * @exception IOException
	 */
	public void readKeywordFile() {

		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(
					properties.getProperty("file.path.keywords")));
			inputLoop(input);
		} catch (java.io.FileNotFoundException fnfe) {
			System.out.println("Failed to read input file");
			fnfe.printStackTrace();
		} catch (Exception exception) {
			System.out.println("General Error");
			exception.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (java.io.IOException ioe) {
				System.out.println("Failed to close input or outputfile");
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * This method is passed an individual token and adds it to a TreeMap every
	 * time this is done.
	 * 
	 * @param token
	 */
	public void processToken(String token) {

		tokenOccurence++;
		List<Integer> arrList = keywordMap.get(token.toLowerCase());
		if (arrList != null) {
			arrList.add(tokenOccurence);
		}
	}

	/**
	 * This method loops through the keyword HashMap's value of nums List
	 * building the output string.
	 * 
	 * @param sb
	 * @param nums
	 */
	public void valueLoop(StringBuilder sb, List<Integer> nums) {

		int index = 0;
		for (Integer x : nums) {
			if (index == 0) {
				// Do nothing
			} else if (index % 8 == 0) {
				sb.append("\n");
			} else {
				sb.append(" ");
			}
			sb.append(nums.get(index));
			if (index < nums.size() - 1) {
				sb.append(",");
			}
			index++;
		}
	}

	/**
	 * This method loops through the keyword HashMap building the output string.
	 * 
	 * @param out
	 */
	public void keywordMapLoop(PrintWriter out) {

		Iterator it = keywordMap.entrySet().iterator();
		List<Integer> nums;
		StringBuilder sb = new StringBuilder(2048);

		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			nums = (List<Integer>) pairs.getValue();
			sb.setLength(0);
			sb.append(pairs.getKey()).append(" =\n[");
			valueLoop(sb, nums);
			out.println(sb.toString() + "]\n");
		}
	}

	/**
	 * This method writes output to the token_count text file.
	 * 
	 * @param inputFilePath
	 * @exception FileNotFoundException
	 * @exception Exception
	 */
	public void writeOutputFile(String inputFilePath) {

		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					properties.getProperty("output.dir")
							+ properties.getProperty("output.file.keyword"))));
			keywordMapLoop(out);
		} catch (java.io.IOException fnfe) {
			System.out.println("Failed to read input file");
			fnfe.printStackTrace();
		} catch (Exception exception) {
			System.out.println("General Error");
			exception.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * This method writes output to the big words dialog box.
	 * 
	 * @param inputFilePath
	 * @param totalString
	 */
	public String outputDialogBoxes(String inputFilePath) {

		totalString += "\n\nKEYWORD ANALYZER:\n=================\nThis analyzer selects "
				+ "the ten most commons words used in the English language from the document and lists "
				+ "thier position.\n";
		Iterator it2 = keywordMap.entrySet().iterator();
		List<Integer> nums;
		StringBuilder sb = new StringBuilder(2048);

		while (it2.hasNext()) {
			Map.Entry pairs = (Map.Entry) it2.next();
			nums = (List<Integer>) pairs.getValue();

			sb.setLength(0);
			sb.append(pairs.getKey()).append(" =\n[");
			valueLoop(sb, nums);
			totalString += sb.toString() + "]\n";
		}
		return totalString;
	}
}