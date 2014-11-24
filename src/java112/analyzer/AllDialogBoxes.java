package java112.analyzer;

import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This is the main processing class that creates an AnalyzeFile object and
 * calls the process class.@author Yonas Haile
 */

public class AllDialogBoxes {
	JTextArea tracker;
	JPanel optPanel;
	JFileChooser fileDialog;
	JScrollPane scrPane;
	JButton closeButton;
	JFrame guiFrame2;

	String totalString = "";

	public void fileDialogBox(List<Analyzer> analyzers, String inputFilePath) {
		closeButton = new JButton("Close");
		guiFrame2 = new JFrame();

		guiFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame2.setTitle("Token Analyzer Output");
		guiFrame2.setExtendedState(guiFrame2.getExtendedState()
				| JFrame.MAXIMIZED_BOTH);

		// This will center the JFrame in the middle of the screen
		guiFrame2.setLocationRelativeTo(null);

		// Using a JTextArea to diplay feedback
		tracker = new JTextArea("OUTPUT DOCUMENT\n===============\n");
		tracker.setEditable(false);
		tracker.setVisible(true);
		guiFrame2.add(tracker, BorderLayout.NORTH);

		// Add scroll pane to text area
		JScrollPane scroll = new JScrollPane(tracker);
		guiFrame2.add(scroll);

		optPanel = new JPanel();
		tracker.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
		optPanel.setLayout(new GridLayout(1, 2));

		guiFrame2.add(optPanel, BorderLayout.SOUTH);

		closeButton.setActionCommand("Close");
		closeButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				closeDialog(event);
			}
		});

		optPanel.add(closeButton);

		for (Analyzer analyzerRef : analyzers) {
			totalString += analyzerRef.outputDialogBoxes(inputFilePath);
		}

		tracker.append(totalString);
		guiFrame2.setVisible(true);
	}

	public void closeDialog(ActionEvent event) {
		guiFrame2.dispose();
	}
}