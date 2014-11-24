package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 *This is the BigWordAnalyzer class that implements the interface: analyzer.
 *It builds a list of big word that are in the inputed file.
 */

public class BigWordAnalyzer implements Analyzer {

    private Properties properties;
    private Set<String> bigWords;
    private int minimumWordLength;
    String totalString = "";

    /**Class constructor*/
    public BigWordAnalyzer() {
        bigWords = new TreeSet();
    }

    /**Class constructor with one properties paramater
     *@param properties
     */
    public BigWordAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }

    /**
     *This method is passed an individual token and adds it to the TreeSet if it
     *is larger or equal to the minimumWordLength.
     *@param token
     */
    public void processToken(String token) {
        minimumWordLength = Integer.parseInt(this.properties.getProperty("bigwords.minimum.length"));
        if (token.length() >= minimumWordLength) {
            bigWords.add(token);
        }
    }

    /**
     *This method loops through the bigWords TreeSet
     *and outputs each word to a line.
     *@param out
     */
    public void bigWordsLoop(PrintWriter out) {

        for (Iterator iterator = bigWords.iterator(); iterator.hasNext();) {
            out.println(iterator.next());
        }
    }

    /**
     *This method writes output to the big_words text file.
     *@param inputFilePath
     *@exception FileNotFoundException
     *@exception Exception
     */
    public void writeOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.dir");
        PrintWriter out = null;

        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath + properties.getProperty("output.file.bigwords"))));
            bigWordsLoop(out);
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
     *This method writes output to the big words dialog box.
     *@param inputFilePath
     *@param totalString
     */
    public String outputDialogBoxes(String inputFilePath) {
    	    
    	totalString += "\n\nBIG WORDS ANALYZER:\n===================\nThis analyzer finds words over 14 characters long.\n";
    	
    	for (Iterator iterator = bigWords.iterator(); iterator.hasNext();) {
           totalString += "\n" + iterator.next();
        }
        return totalString;
    }

    /**
     *This is the get method for the bigWords set.
     *
     */
    public Set<String> getBigWords() {
        return bigWords;
    }
}