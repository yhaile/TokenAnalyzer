package java112.analyzer;

import javax.swing.JFileChooser;
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
import org.apache.log4j.BasicConfigurator;

/**
 * This is the main processing class that creates an AnalyzeFile object and
 * calls the process class.@author Yonas Haile
 */

public class AnalyzerDriver {

	JFrame guiFrame;
	JTextArea tracker;
	JPanel optPanel;
	JFileChooser fileDialog;
	JScrollPane scrPane;
	String fileName;
	JButton submitButton = new JButton("Submit File");

	public static void main(String[] arguments) {
		BasicConfigurator.configure();
		EventQueue.invokeLater(new Runnable() {

			public void run() {

				new AnalyzerDriver();
			}
		});
	}

	public AnalyzerDriver() {

		guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Token Analyzer");
		guiFrame.setSize(700, 500);
		guiFrame.setResizable(true);

		// This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setLayout(new BorderLayout());

		// create an instance of JFileChooser class
		fileDialog = new JFileChooser();

		// Using a JTextArea to diplay feedback
		tracker = new JTextArea("File Tracker:");
		tracker.setVisible(true);
		tracker.setEditable(false);
		tracker.selectAll();
		guiFrame.add(tracker, BorderLayout.NORTH);

		// Add scroll pane to text area
		JScrollPane scroll = new JScrollPane(tracker);
		guiFrame.add(scroll);

		optPanel = new JPanel();
		optPanel.setLayout(new GridLayout(1, 2));

		guiFrame.add(optPanel, BorderLayout.SOUTH);

		JButton openButton = new JButton("Select File");
		openButton.setActionCommand("Select File");
		openButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				openDialog();
			}
		});

		optPanel.add(openButton);

		submitButton.setActionCommand("Submit File");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				submitDialog();
			}
		});

		optPanel.add(submitButton);
		submitButton.setEnabled(false);
		guiFrame.setVisible(true);

		JButton closeButton = new JButton("Close");
		closeButton.setActionCommand("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				closeDialog(event);
			}
		});

		optPanel.add(closeButton);

		guiFrame.setVisible(true);
	}

	public void closeDialog(ActionEvent event) {
		guiFrame.dispose();
	}

	// Show a open file dialog box
	private void openDialog() {
		int openChoice = fileDialog.showOpenDialog(guiFrame);

		// display choice using tracker
		logChoice(openChoice, "Open Dialog");

		if (openChoice == JFileChooser.APPROVE_OPTION) {
			// Put open file code in here
			File openFile = fileDialog.getSelectedFile();
			fileName = openFile.getPath();
			tracker.append("\nThe file selected is " + openFile.getName());
			tracker.append("\nThe file's path is " + openFile.getPath());
			submitButton.setEnabled(true);
		}
	}

	// Show a save file dialog box
	private void submitDialog() {
		String[] argument;
		argument = new String[2];

		argument[0] = fileName;

		argument[1] = "analyzer.properties";

		AnalyzeFile analyzer = new AnalyzeFile();
		analyzer.runAnalysis(argument);

		// Put save file code in here
		File saveFile = fileDialog.getSelectedFile();
		tracker.append("\nThe output files are in the output folder! ");
		submitButton.setEnabled(false);

	}

	// append the button choice to the tracker JTextArea
	private void logChoice(int choice, String dialog) {
		switch (choice) {
		// The user pressed cancel button
		case JFileChooser.CANCEL_OPTION:
			tracker.append("\nCancel Option received from " + dialog);
			break;

		// The user pressed the open/save button
		case JFileChooser.APPROVE_OPTION:
			tracker.append("\nApprove Option received from " + dialog);
			break;

		// The user dismissed the dialog without pressing a button
		case JFileChooser.ERROR_OPTION:
			tracker.append("\nError Option received from " + dialog);
			break;
		}
	}
}