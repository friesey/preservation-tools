package preservingfiles;

import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class fitsFindings {
	
	static String fitsFolder;
	
	public static void main(String args[]) throws IOException {
		
		changecolor();

		JOptionPane.showMessageDialog(null, "Please choose the folder with the FITS-xml files to analyse", "Fits Findings", JOptionPane.QUESTION_MESSAGE);
		fitsFolder = utilities.BrowserDialogs.chooseFolder();
		
		if (fitsFolder != null) {

			JFrame f = new JFrame();
			JButton but = new JButton("... Program is running ... ");
			f.add(but, BorderLayout.PAGE_END);
			f.pack();
			f.setVisible(true);
			
			//create FitsObjects and save them in an array
			
			
			
			f.dispose();
		}
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.gray);
	}
}
