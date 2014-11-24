package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 *This is the TokenCountAnalyzer class that implements the interface: analyzer.
 *It builds a list of unique tokens and their number of occurences from an inputed
 *text file.
 */
public class TokenCountAnalyzer implements Analyzer {

    private Properties properties;
    private Map<String, Integer> tokenCounts;
    String totalString = "";

    public Map getTokenCounts() {
        return tokenCounts;
    }

    /**Class constructor*/
    public TokenCountAnalyzer() {
        tokenCounts = new TreeMap<String, Integer>();
    }

    /**
     *Class constructor with one properties paramater
     *@param properties
     */
    public TokenCountAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }
    
    /**
     *This method writes output to the token_count text file.
     *@param inputFilePath
     *@exception FileNotFoundException
     *@exception Exception
     */
    public void writeOutputFile(String inputFilePath) {

        PrintWriter out = null;

        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(properties.getProperty("output.dir") + properties.getProperty("output.file.token.count"))));
            tokenCountsLoop(out);
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
     *This method is passed an individual token and adds it to a TreeMap
     *every time this is done.
     *@param token
     */
    public void processToken(String token) {

        Integer count = tokenCounts.get(token);
        if (count == null) {
            tokenCounts.put(token, 1);
        } else {
            tokenCounts.put(token, count + 1);
        }
    }
    
 /**
     *This method writes output to the token count dialog box.
     *@param inputFilePath
     */
    public String outputDialogBoxes(String inputFilePath) {
    	    
    	totalString += "\n\nTOKEN COUNT ANALYZER:\n=====================\nThis analyzer prepares a occurance count of each individual token\n";
     	Iterator it = tokenCounts.entrySet().iterator();
 
     	while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            totalString += "\n" + pairs.getKey() + "\t" + pairs.getValue();
         }
        return totalString;
    }
    
    /**
     *This method loops through the tokenCounts TreeMap
     *added through process token.
     *@param out
     */
    public void tokenCountsLoop(PrintWriter out) {

        Iterator it2 = tokenCounts.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pairs = (Map.Entry) it2.next();
            out.println(pairs.getKey() + "\t" + pairs.getValue());
        }
    }
}