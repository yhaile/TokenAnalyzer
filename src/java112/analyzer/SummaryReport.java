package java112.analyzer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 *This is the SummaryReport class that implements the interface: analyzer.
 *It builds a reprot with summary information about the inputed file.
 */

public class SummaryReport implements Analyzer {

    private int totalTokensCount;
    private Properties properties;
    String totalString = "";

    /**Class constructor*/
    public SummaryReport() {

    }

    /**
     *Class constructor with one properties paramater
     *@param properties
     */
    public SummaryReport(Properties properties) {
        this.properties = properties;
    }
    /**
     *This method writes output to the summary_report text file.
     *@param out
     *@param inputFilePath
     */

    public void outputMethod(PrintWriter out, String inputFilePath) {

        out.println("Application: " + properties.getProperty("application.name"));
        out.println("Author: " + properties.getProperty("author"));
        out.println("email: " + properties.getProperty("author.email.address"));
        out.println("Input file: " + inputFilePath);
        out.println("Analyzed on: " + new Date());
        out.println("Total token count: " + getTotalTokensCount());

    }
    
     /**
     *This method writes output to the summary_report dialog box.
     *@param inputFilePath
     *@param totalString
     */

    public String outputDialogBoxes(String inputFilePath) {
    	    
    	totalString += "\nSUMMARY REPORT:\n===============\n";
        totalString += "\nApplication: " + properties.getProperty("application.name");
        totalString += "\nAuthor: " + properties.getProperty("author");
        totalString += "\nemail: " + properties.getProperty("author.email.address");
        totalString += "\nInput file: " + inputFilePath;
        totalString += "\nAnalyzed on: " + new Date();
        totalString += "\nTotal token count: " + getTotalTokensCount()+ "\n";

        return totalString;
    }
    
    /**
     *Get method for the totalTokensCount integer variable.
     */
    public int getTotalTokensCount() {
        return totalTokensCount;
    }

    /**
     *This method is passed an individual token and increments totalTokensCount
     *every time this is done.
     *@param token
     */

    public void processToken(String token) {
        totalTokensCount++;
    }

    /**
     *This method passes the out variable and the inputFilePath to the output
     *method.
     *@param inputFilePath
     *@exception FileNotFoundException
     *@exception Exception
     *@exception IOException
     */
    public void writeOutputFile(String inputFilePath) {
        PrintWriter out = null;
        String outputFilePath = properties.getProperty("output.dir");

        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath + properties.getProperty("output.file.summary"))));

            outputMethod(out, inputFilePath);

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
}
