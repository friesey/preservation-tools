package filetools.gif;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class GifChecker {

	public static String folder;

	public static void main(String args[]) throws Exception {

		String pathwriter;

		try {
			JOptionPane.showMessageDialog(null, "Please choose a Folder with files", "JHOVE PDF-Examination", JOptionPane.QUESTION_MESSAGE);
			folder = utilities.FolderBrowserDialog.chooseFolder();
			if (folder != null) {

				pathwriter = (folder + "//" + "GifAnalysis.xml");
				PrintWriter writer = new PrintWriter(new FileWriter(pathwriter));

				String xmlVersion = "xml version='1.0'";
				String xmlEncoding = "encoding='ISO-8859-1'";
				String xmlxslStyleSheet = "<?xml-stylesheet type=\"text/xsl\" href=\"GifAnalysisCustom.xsl\"?>";

				writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
				writer.println(xmlxslStyleSheet);

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

				/*TODO: This is a nice way to print every file in the folder in hex files, but there is always missing so much at the end */
				/*
				 * String tempwriter; PrintWriter tempHexWriter; String
				 * filename; for (int i = 0; i < files.size(); i++) { filename =
				 * FilenameUtils.getBaseName(files.get(i).toString());
				 * tempwriter = (folder + "//" + filename + "GifinHex.hex");
				 * tempHexWriter = new PrintWriter(new FileWriter(tempwriter));
				 * utilities.HexReader.convertToHex(tempHexWriter,
				 * files.get(i)); }
				 */
				
				
				
				writer.println("<GifAnalysis>");
				writer.println("</GifAnalysis>");
				writer.close();

			}
		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Exception", JOptionPane.WARNING_MESSAGE);
		}
	}
}
