package filetools.gif;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.JhoveException;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.handler.XmlHandler;
import edu.harvard.hul.ois.jhove.module.GifModule;
import externalToolAnalysis.RunJhoveApp;

public class GifChecker {

	public static String giffolder;
	public static App gifjhoveapp;
	public static JhoveBase jhoveBaseGif;
	static Module gifmodule;
	static OutputHandler handler;

	public static void main(String args[]) throws Exception {

		try {
			JOptionPane.showMessageDialog(null, "Please choose a Folder with files", "Gif File Examination", JOptionPane.QUESTION_MESSAGE);
			giffolder = utilities.BrowserDialogs.chooseFolder();
			if (giffolder != null) {

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(giffolder), new ArrayList<File>());

				GifXmlOutput.createXmlGifOutput();
				createJhoveChecker();

				/*
				 * TODO: This is a nice way to print every file in the folder in
				 * hex files, but there is always missing so much at the end
				 */
				/*
				 * String tempwriter; PrintWriter tempHexWriter; String
				 * filename; for (int i = 0; i < files.size(); i++) { filename =
				 * FilenameUtils.getBaseName(files.get(i).toString());
				 * tempwriter = (folder + "//" + filename + "GifinHex.hex");
				 * tempHexWriter = new PrintWriter(new FileWriter(tempwriter));
				 * utilities.HexReader.convertToHex(tempHexWriter,
				 * files.get(i)); }
				 */
				for (int i = 0; i < files.size(); i++) {
					boolean gifisvalid = checkifgifisvalid(files.get(i));
					boolean hasEofTag = checkIfGifHasEof(files.get(i));
					if (!gifisvalid) {
						GifReparator.repairgif(files.get(i));
						gifisvalid = checkifgifisvalid(files.get(i));
						if (gifisvalid) {
							// Some output that it has worked
						} else {
							// Some output of failure
						}
					}
				}
			}

			GifXmlOutput.closeXmlGifOutput();

		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "Exception", JOptionPane.WARNING_MESSAGE);
		}
	}

	private static boolean checkIfGifHasEof(File giffile) throws IOException {
		// TODO
		if (filetools.GenericFileAnalysis.testFileHeaderGif(giffile) == true) {
			System.out.println(giffile.toString());
			System.out.println(utilities.HexReader.readEofTag(giffile));
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkifgifisvalid(File giffile) throws Exception {
		GifXmlOutput.xmlgifwriter.println("<item>");
		if (giffile.toString().contains("&")) {
			String substitute = RunJhoveApp.normaliseToUtf8(giffile.toString());
			GifXmlOutput.xmlgifwriter.println("<filename>" + substitute + "</filename>");
		} else {
			GifXmlOutput.xmlgifwriter.println("<filename>" + giffile.toString() + "</filename>");
		}
		jhoveBaseGif.process(gifjhoveapp, gifmodule, handler, giffile.toString());
		GifXmlOutput.xmlgifwriter.println("</item>");
		return false;
	}

	public static void createJhoveChecker() throws Exception {
		jhoveBaseGif = new JhoveBase();

		String configFilePath = JhoveBase.getConfigFileFromProperties();
		jhoveBaseGif.init(configFilePath, null);

		jhoveBaseGif.setEncoding("UTF-8");// UTF-8 does not calculate checksums,
		// which saves time
		jhoveBaseGif.setBufferSize(131072);
		jhoveBaseGif.setChecksumFlag(false);
		jhoveBaseGif.setShowRawFlag(false);
		jhoveBaseGif.setSignatureFlag(false);

		String appName = "Customized JHOVE";
		String version = "1.0";
		int[] date = { 2014, 12, 03 };
		String usage = "Call JHOVE via own Java";
		String rights = "Copyright nestor Format Working Group";
		gifjhoveapp = new App(appName, version, date, usage, rights);

		gifmodule = new GifModule(); // JHOVE GifModule only

		handler = new XmlHandler();

		handler.setWriter(GifXmlOutput.xmlgifwriter);
		handler.setBase(jhoveBaseGif);
		gifmodule.init("");
		gifmodule.setDefaultParams(new ArrayList<String>());

	}

}
