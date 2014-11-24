package java112.analyzer;

import java.io.*;
import java.util.*;
import java.text.*;

/**
 * This is the UniqueTokenAnalyzer class that implements the interface:
 * analyzer. It builds a list of unique tokens from an inputed text file.
 */
public class UniqueTokenAnalyzer implements Analyzer {

	private Set uniqueTokensList;
	private Properties properties;
	String totalString = "";

	/*
	 * Class constructor
	 */
	public UniqueTokenAnalyzer() {
		uniqueTokensList = new TreeSet();
	}

	/**
	 * Class constructor with one proerties paramater
	 * 
	 * @param properties
	 */
	public UniqueTokenAnalyzer(Properties properties) {
		this();
		this.properties = properties;
	}

	/**
	 * This method writes output to the unique_tokens text file.
	 * 
	 * @param inputFilePath
	 * @exception FileNotFoundException
	 * @exception Exception
	 * @exception IOException
	 */
	public void writeOutputFile(String inputFilePath) {
		String outputFilePath = properties.getProperty("output.dir");
		PrintWriter out = null;

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					outputFilePath
							+ properties.getProperty("output.file.unique"))));
			uniqueTokensListLoop(out);
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
	 * Get method for the uniqueTokensList variables
	 */
	public Set getUniqueTokensList() {
		return uniqueTokensList;
	}

	/**
	 * This method is passed an individual token and adds it to a TreeSet every
	 * time this is done.
	 * 
	 * @param token
	 */
	public void processToken(String token) {
		uniqueTokensList.add(token);
	}

	/**
	 * This method loops through the uniqueTokensList TreeSet added through
	 * process token.
	 * 
	 * @param out
	 */
	public void uniqueTokensListLoop(PrintWriter out) {

		if (uniqueTokensList != null) {
			for (Iterator iterator = uniqueTokensList.iterator(); iterator
					.hasNext();) {
				out.println(iterator.next());
			}
		}
	}

	/**
	 * This method writes output to the unique tokens dialog box.
	 * 
	 * @param inputFilePath
	 * @param totalString
	 */
	public String outputDialogBoxes(String inputFilePath) {

		totalString += "\n\nUNIQUE TOKENS ANALYZER:\n========================\nThis analyzer prepares a list of every unique token\n";
		if (uniqueTokensList != null) {
			for (Iterator iterator = uniqueTokensList.iterator(); iterator
					.hasNext();) {
				totalString += "\n" + iterator.next();
			}
		}
		return totalString;
	}
}