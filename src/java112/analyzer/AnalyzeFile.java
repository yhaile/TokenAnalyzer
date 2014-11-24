package java112.analyzer;

import java.io.*;
import java.util.*;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.extractor.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.util.List;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
/**
 *This class is the main controlling class for the project.
 *
 */
public class AnalyzeFile {
    private static final int VALID_ARGUMENTS_COUNT = 2;
    private String inputFilePath;

    private Properties properties;

    private List<Analyzer> analyzers;

    /**
     *This method loads values from a properties file.
     *@param  propertiesFilePath
     *@exception Exception
     *@exception IOException
     */
    public void loadProperties(String propertiesFilePath) {
        properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (IOException ioe) {
            System.out.println("Can't load the properties file");
            ioe.printStackTrace();
        } catch (Exception e) {
            System.out.println("Problem: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *This method creates analyzers by instantiating analyzers from a list of
     *analyzers.
     *
     */
    public void createAnalyzers() {

        analyzers = new ArrayList<Analyzer>();

        analyzers.add(new SummaryReport(properties));
        analyzers.add(new UniqueTokenAnalyzer(properties));
        analyzers.add(new BigWordAnalyzer(properties));
        analyzers.add(new TokenCountAnalyzer(properties));
        analyzers.add(new TokenSizeAnalyzer(properties));
        analyzers.add(new KeywordAnalyzer(properties));
    }

    /**
     *This method loops through the analyzer list to call the writeOutputFile
     *for each one.
     */
    public void outputFileLoop() {
        for (Analyzer analyzerRef : analyzers) {
            analyzerRef.writeOutputFile(inputFilePath);
            
        }

    }
    
     /**
     *This method passes the input file path to the anlyzers to write thier
     * respective output files.
     */
    public void writeAllDialogBoxes() {
        AllDialogBoxes allDialogBoxes = new AllDialogBoxes();
        allDialogBoxes.fileDialogBox(analyzers, inputFilePath);
    }
    
    /**
     *This method passes the input file path to the anlyzers to write thier
     * respective output files.
     */
    public void writeAllOutputFiles() {
        outputFileLoop();
    }
    /**
     * tokensLoop loops through the ArrayList tokens, checks for nulls then
     * passes the token to the analyzers.
     *@param tokenArray
     */
    public void tokensLoop(String[] tokenArray) {

        
        for (String token : tokenArray) {

            if (token.length() > 0) {

                passTokenToAnalyzers(token);

            }
        }
    }

    /**
     * This method passes individual tokens to the analyzers through the
     * processToken method.
     *@param token
     */

    public void passTokenToAnalyzers(String token) {
        for (Analyzer anaTokens : analyzers) {
            anaTokens.processToken(token);

        }
    }

    /**
     *This method checks that the correct number of arguments was entered and if
     * it does assigns it to  inputFilePath variable, then calls the createAnalyzers,
     * readFile and writeAllOutputFiles methods.
     *@param arguments
     */
    public void runAnalysis(String[] argument) {

        properties = new Properties();

        if (argument.length != VALID_ARGUMENTS_COUNT) {
            System.out.println("Please enter an argument on the command line");
            return;

        }
        inputFilePath = argument[0];
        loadProperties(argument[1]);
        createAnalyzers();
        
        if(inputFilePath.contains("docx")){
	    
            docxReader(inputFilePath);
	
        } else if(inputFilePath.contains("txt")){
        
            readFile();
        } else if(inputFilePath.endsWith("pdf")){
        	
        	pdfReader(inputFilePath);
        }else {
		docReader(inputFilePath);
	}
        writeAllOutputFiles();
        writeAllDialogBoxes();

    }

    /**
     * This method holds a loop of inputed lines to be read
     * and added to the array.
     *@param input
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
     * This method instantiates the BufferedReader and calls the inputLoop method.
     *@exception FileNotFoundException
     *@exception Exception
     *@exception IOException
     */
    public void readFile() {

        BufferedReader input = null;
        inputFilePath.replace("C:\\", "");
        
        try {
            input = new BufferedReader(new FileReader(inputFilePath));

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
    
    public void docReader(String docname){
		try {
			
			POIFSFileSystem fs = null;
			fs = new POIFSFileSystem(new FileInputStream(docname));
		
			WordExtractor we = new WordExtractor(fs);
			String[] tokenArray;
			String[] paras = we.getParagraphText();
		for
		 (int i = 0; i < paras.length; i++){
	
		            String inputline = paras[i].toString();
		tokenArray = inputline.split("\\W");
		
		System.out.println(tokenArray);
		tokensLoop(tokenArray);
		we.close();
		}
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

	public void docxReader(String docname){
	XWPFDocument  xs;
	try {
		 xs = new XWPFDocument(new FileInputStream(docname));
		List<XWPFParagraph> paras = xs.getParagraphs();
		String[] tokenArray;
		for(XWPFParagraph obi: paras){
	
		            String inputline = obi.getText();
		tokenArray = inputline.split("\\W");
		tokensLoop(tokenArray);
		}	
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

	public void pdfReader(String fileName){
		 PDFParser parser = null;
		      String parsedText = "";
		      PDFTextStripper pdfStripper;
		      PDDocument pdDoc = null;
		      COSDocument cosDoc = null;
		      File f = new File(fileName);
		      String[] tokenArray;
		 try {
			              parser = new PDFParser(new FileInputStream(f));
			          } catch (Exception e) {
			              System.out.println("Unable to open PDF Parser.");
			              
			          }
			    
			          try {
			              parser.parse();
			              cosDoc = parser.getDocument();
			              pdfStripper = new PDFTextStripper();
			              pdDoc = new PDDocument(cosDoc);
			              parsedText = pdfStripper.getText(pdDoc);
			          } catch (Exception e) {
			              System.out.println("An exception occured in parsing the PDF Document.");
			              e.printStackTrace();
			              try {
			                     if (cosDoc != null) cosDoc.close();
			                     if (pdDoc != null) pdDoc.close();
			                 } catch (Exception e1) {
			                 e.printStackTrace();
			              }
			              
			          }
			         tokenArray = parsedText.split("\\W");
			  		tokensLoop(tokenArray);
			  		
	}
}

