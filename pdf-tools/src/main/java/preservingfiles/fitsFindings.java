package preservingfiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.util.List;

import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Element;

public class fitsFindings {

	static String fitsFolder;

	public static void main(String args[]) throws IOException, JDOMException {

		changecolor();

		JOptionPane.showMessageDialog(null, "Please choose the folder with the FITS-xml files to analyse", "Fits Findings", JOptionPane.QUESTION_MESSAGE);
		fitsFolder = utilities.BrowserDialogs.chooseFolder();

		if (fitsFolder != null) {

			JFrame f = new JFrame();
			JButton but = new JButton("... Program is running ... ");
			f.add(but, BorderLayout.PAGE_END);
			f.pack();
			f.setVisible(true);

			// create FitsObjects and save them in an array

			ArrayList<File> files = utilities.ListsFiles.getPaths(new File(fitsFolder), new ArrayList<File>());
			ArrayList<File> fitsfindings = new ArrayList<File>();

			String temp;
			int len;

			for (int i = 0; i < files.size(); i++) {
				len = files.get(i).toString().length();
				temp = files.get(i).toString().substring(len - 8, len);
				if (temp.equals("fits.xml")) {
					fitsfindings.add(files.get(i));
				}
			}

			for (int i = 0; i < fitsfindings.size(); i++) {
			//	System.out.println(fitsfindings.get(i).toString());

				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(fitsfindings.get(i));
				XMLOutputter fmt = new XMLOutputter();

				Element rootElement = doc.getRootElement();
				@SuppressWarnings("unchecked")
				List<Element> allnods = (List<Element>) rootElement.getChildren();

				
				ZbwFile testfile = new ZbwFile();
				FitsObject fitsObject = new FitsObject();
				
				fitsObject.lengNods = allnods.size();

				// prints out first child element after root element
				for (int j = 0; j < allnods.size(); j++) {					
							//	System.out.println(((Element) allnods.get(j)).getName());					
							
					}
				Element fileinfoElement = rootElement.getChild( "filename" );
				String fileinfoText = rootElement.getChildText( "filename" );
				
				System.out.println(fileinfoElement);
			
				
				System.out.println(fileinfoText);
				 
				 
				f.dispose();
			}
		}
		
	}

	private static void changecolor() {
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);
	}
}
