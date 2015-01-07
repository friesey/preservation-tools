package externalToolAnalysis;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class JhoveGuiStarterDialog {

	public static void main(String args[]) throws Exception {

		changecolor();

		String path = "D://Eclipse New//jhove-logo_small.gif";
		String description = "JHOVE Logo";
		ImageIcon icon = new ImageIcon(path, description);

		int eingabe = JOptionPane.showConfirmDialog(null, "Do you want to use JHOVE to validate?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
		// yes == 0; no == 1

		if (eingabe == 0) {
			Object[] options = { "PDF", "GIF", "XML", "TIFF" };
			int inteingabe = JOptionPane.showOptionDialog(null, "Which file format do you want to validate?", "Jhove Validation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (inteingabe == 0) {
				JOptionPane.showMessageDialog(null, "JHOVE will be used to validate PDF files from a chosen folder", "PDF Validation", JOptionPane.INFORMATION_MESSAGE);
				//externalToolAnalysis.JhoveValidator.JhovePdfValidator();
			} else if (inteingabe == 1) {
				JOptionPane.showMessageDialog(null, "JHOVE will be used to validate GIF files from a chosen folder", "GIF Validation", JOptionPane.PLAIN_MESSAGE);
				//externalToolAnalysis.JhoveValidator.JhoveGifValidator();
			} else if (inteingabe == 2) {
				JOptionPane.showMessageDialog(null, "JHOVE will be used to validate XML files from a chosen folder", "XML Validation", JOptionPane.INFORMATION_MESSAGE);
			} else if (inteingabe == 3) {
				JOptionPane.showMessageDialog(null, "JHOVE will be used to validate TIFF files from a chosen folder", "TIFF Validation", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Nothing will be done. Please choose properly.", "Misbehaviour", JOptionPane.WARNING_MESSAGE);
			}
		}

		else {
			JOptionPane.showMessageDialog(null, "Close program to use other tool", "Information", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}

}
