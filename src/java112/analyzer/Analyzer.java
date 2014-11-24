package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * This is the Analyzer interface that is implemented by SummaryReport
 *uniqueTokenAnalyzer, bigWordsAnalyzer and the tokenCountAnalyzer.
 */
public interface Analyzer {

    /**
     *This method processes each individual token when being called by all four
     *analyzers and passing the token.
     *@param token
     */
    void processToken(String token);

    /**
     *This method will build the output text documents for each analyzer
     *by passing the input file path variable.
     *@param inputFilePath
     */
    void writeOutputFile(String inputFilePath);
    
     /**
     *This method will build a dialog box for each analyzer
     *by passing the input file path variable.
     *@param inputFilePath
     */
    String outputDialogBoxes(String inputFilePath);

}
