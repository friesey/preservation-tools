package externalToolAnalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.handler.XmlHandler;
import edu.harvard.hul.ois.jhove.module.PdfModule;

// the libray JhoveApp.jar is not in the maven library. This causes a Travis error.

public class RunJhoveApp {

	public static String folder;

	public static String JHOVE_DIR = "jhove";

	public static void main(String args[]) throws Exception {

		String pathwriter;

		try {
			JOptionPane.showMessageDialog(null, "Please choose a Folder with PDF files", "JHOVE PDF-Examination", JOptionPane.QUESTION_MESSAGE);
			folder = utilities.FolderBrowserDialog.chooseFolder();
			if (folder != null) {
				JhoveBase jb = new JhoveBase();

			//	String configFilePath = getConfigFileFromProperties();
				
				String configFilePath = JhoveBase.getConfigFileFromProperties ();
				jb.init(configFilePath, null);

				jb.setEncoding("UTF-8");// UTF-8 does not calculate checksums,
										// which saves time

				jb.setTempDirectory("C://temp1");
				jb.setBufferSize(131072);
				jb.setChecksumFlag(false);
				jb.setShowRawFlag(false);
				jb.setSignatureFlag(false);

				String appName = "Yvonne JHOVE wrapper";
				String version = "1.0";
				int[] date = { 2014, 11, 26 };
				// year, month, day
				String usage = "Call JHOVE via own Java";
				String rights = "Copyright nestor Format Working Group";
				App app = new App(appName, version, date, usage, rights);

				Module module = new PdfModule();
				/*
				 * * to try this with PdfModule only
				 */
				OutputHandler handler = new XmlHandler();

				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

				pathwriter = (folder + "//" + "JhoveExamination" + ".xml");

				PrintWriter writer = new PrintWriter(new FileWriter(pathwriter));
				handler.setWriter(writer);
				handler.setBase(jb);
				module.init("");
				module.setDefaultParams(new ArrayList<String>());

				String xmlVersion = "xml version='1.0'";
				String xmlEncoding = "encoding='ISO-8859-1'";

				writer.println("<?" + xmlVersion + " " + xmlEncoding + "?>");
				writer.println("<JhoveFindings>");

				// To handle one file after the other
				for (int i = 0; i < files.size(); i++) {

					if (filetools.GenericFileAnalysis.testFileHeaderPdf(files.get(i)) == true) {
						writer.println("<item>");
						writer.println("<filename>" + files.get(i).toString() + "</filename>");
						jb.process(app, module, handler, files.get(i).toString());

						writer.println("</item>");
					}
				}
				writer.println("</JhoveFindings>");
				writer.close();

				externalToolAnalysis.JhoveStatistics.JhoveOutputAnalysis(pathwriter);

				externalToolAnalysis.XmlParserJhove.parseXmlFile(pathwriter);

			}
		}

		catch (Exception e) {
			System.out.println("Exception: " + e);
		}

	}

	public static String getConfigFileFromProperties() {
		String configFile = null;
		String CONFIG_PROPERTY = "edu.harvard.hul.ois." + "jhove.config";

		String CONFIG_DIR = "conf";

		configFile = getFromProperties(CONFIG_PROPERTY);
		if (configFile == null) {
			try {
				String fs = System.getProperty("file.separator");
				configFile = System.getProperty("user.home") + fs + JHOVE_DIR + fs + CONFIG_DIR + fs + "jhove.conf";
				System.out.println(configFile);
			} catch (Exception e) {
			}
		}
		return configFile;
	}

	public static String getFromProperties(String name) {
		String value = null;
		try {
			String fs = System.getProperty("file.separator");
			Properties props = new Properties();
			String propsFile = System.getProperty("user.home") + fs + JHOVE_DIR + fs + "jhove.properties";
			FileInputStream stream = new FileInputStream(propsFile);
			props.load(stream);
			stream.close();
			value = props.getProperty(name);
		} catch (Exception e) {
		}
		return value;
	}

	public void init(String init) throws Exception {

	}

	public void reset() {

	}

}