package java112.analyzer;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 *This is the TokenSizeAnalyzer class that implements the interface: analyzer.
 *It builds a list of unique tokens and their number of occurences from an inputed
 *text file.
 */
public class TokenSizeAnalyzer implements Analyzer {

    private Properties properties;
    private Map<Integer, Integer> tokenSizes;
    String totalString = "";

    public Map<Integer, Integer> getTokenSizes() {
        return tokenSizes;
    }

    /**Class constructor*/
    public TokenSizeAnalyzer() {
        tokenSizes = new TreeMap<Integer, Integer>();
    }

    /**Class constructor with one properties paramater
     *@param properties
     */
    public TokenSizeAnalyzer(Properties properties) {

        this();
        this.properties = properties;

    }

    /**
     *This method writes output to the token_size text file.
     *@param inputFilePath
     *@exception FileNotFoundException
     *@exception Exception
     */
    public void writeOutputFile(String inputFilePath) {

        PrintWriter out = null;

        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(properties.getProperty("output.dir") + properties.getProperty("output.file.token.size"))));
            tokenSizesLoop(out);
            tokenSizesHistogramLoop(out);
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
     *This method is passed an individual token and adds it's size
     *and the count to a TreeMap
     *@param token
     */
    public void processToken(String token) {

        Integer counter = tokenSizes.get(token.length());
        if (counter == null) {
            tokenSizes.put(token.length(), 1);
        } else {
            tokenSizes.put(token.length(), counter + 1);
        }
    }

    /**
     *This method loops through the tokenSize TreeMap
     *added through process token.
     *@param out
     * */
    public void tokenSizesLoop(PrintWriter out) {

        Iterator it = tokenSizes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            out.println(pairs.getKey() + "\t" + pairs.getValue());
        }
    }

    /**
     *This method builds a histogram to represent the counts for each
     *token size.
     *@param out
     */
    public void tokenSizesHistogramLoop(PrintWriter out) {
        // Declare an iterator just to get the max count
        Iterator countIt = tokenSizes.entrySet().iterator();
        int maxCount = 1, count, maximumSize =80;
        // Create an 80-char string to use as the histogram look-up
        String line = new String(new char[maximumSize]).replace('\0', '*');

        // do the counting: if new count is larger, update maxCount
        while (countIt.hasNext()) {
            count = (Integer) ((Map.Entry) countIt.next()).getValue();
            if (count > maxCount) {
                maxCount = count;
            }
        }

        outputLoop(out, maxCount, line, maximumSize);
    }

    /**
     *This method loops throught the map to output
     *the keys and values.
     *@param out
     *@param maxCount
     *@param maximumSize
     */
    public void outputLoop(PrintWriter out, int maxCount, String line, int maximumSize) {
        // calculate the scale: count per a single "*"""
        double scale = (double)maxCount / maximumSize;
        int count;
        int index;
        Iterator it = tokenSizes.entrySet().iterator();
        // Iterate through map and output the * representation for each count
        while (it.hasNext()) {
            @SuppressWarnings("rawtypes")
			Map.Entry pairs = (Map.Entry) it.next();
            count = (Integer) pairs.getValue();

            if (scale < 1) {
                scale = 1; }
            index = (int)(count / scale);
            if (index <= 0) {
                index = 1;
            }

            out.println(pairs.getKey() + " " + line.substring(0, index));
        }
    }
    /**
     *This method loops throught the map to output
     *the keys and values.
     *@param out
     *@param maxCount
     *@param maximumSize
     */
    public void outputLoopForDialogBox(int maxCount, String line, int maximumSize) {
        // calculate the scale: count per a single "*"""
        double scale = (double)maxCount/maximumSize;
        int count;
        int index;
        if (scale < 1) {
        	totalString += "\nHistogram star ratio: 1 : 1";
        } else {
        	totalString += "\nHistogram star ratio: 1 : " + scale;
        }
        Iterator it = tokenSizes.entrySet().iterator();
        // Iterate through map and output the * representation for each count
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            count = (Integer) pairs.getValue();
            index = (int)(count / (double)scale);
            if (index <= 0) {
                index = 1;
            }
            totalString += "\n" + pairs.getKey() + " " + line.substring(0, index);  
            
        }
    }
    
     /**
     *This method writes output to the token count dialog box.
     *@param inputFilePath
     *@param totalString
     */
    public String outputDialogBoxes(String inputFilePath) {
    	    
    	totalString += "\n\nTOKEN SIZE ANALYZER:\n====================\nThis analyzer displays token sizes and their occurance count along with a histogram of the occurance count with a maximun of 50 stars.\n";
    	
    	Iterator<Entry<Integer, Integer>> it = tokenSizes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            totalString += "\n" + pairs.getKey() + "\t" + pairs.getValue();
        }
    	
    	// Declare an iterator just to get the max count
        Iterator countIt = tokenSizes.entrySet().iterator();
        int maxCount = 1, count, maximumSize =80;
        // Create an 80-char string to use as the histogram look-up
        String line = new String(new char[maximumSize]).replace('\0', '*');

        // do the counting: if new count is larger, update maxCount
        while (countIt.hasNext()) {
            count = (Integer) ((Map.Entry) countIt.next()).getValue();
            if (count > maxCount) {
                maxCount = count;
            }
        }	
        outputLoopForDialogBox(maxCount, line, maximumSize);
        return totalString;
    }
}