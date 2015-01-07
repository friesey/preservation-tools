package externalToolAnalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.handler.XmlHandler;
import edu.harvard.hul.ois.jhove.module.GifModule;
import edu.harvard.hul.ois.jhove.module.PdfModule;
import filetools.gif.GifXmlOutput;

// the libray JhoveApp.jar is not in the maven library. This causes a Travis error.

public class JhoveValidator {

	static App gifjhoveapp;
	static JhoveBase jhoveBaseGif;
	static Module gifmodule;
	static OutputHandler handler;

	public static String folder;

	public static void JhovePdfValidator() {

		String pathwriter;

		try {
			JOptionPane.showMessageDialog(null, "Please choose a Folder with PDF files", "JHOVE PDF-Examination", JOptionPane.QUESTION_MESSAGE);
			folder = utilities.BrowserDialogs.chooseFolder();
			if (folder != null) {
				JhoveBase jb = new JhoveBase();

				String configFilePath = JhoveBase.getConfigFileFromProperties();
				jb.init(configFilePath, null);

				jb.setEncoding("UTF-8");// UTF-8 does not calculate checksums,
										// which saves time
				jb.setBufferSize(131072);
				jb.setChecksumFlag(false);
				jb.setShowRawFlag(false);
				jb.setSignatureFlag(false);

				String appName = "Customized JHOVE";
				String version = "1.0";
				
				int[] date = getDate();
				String usage = "Call JHOVE via own Java";
				String rights = "Copyright nestor Format Working Group";
				App app = new App(appName, version, date, usage, rights);

				Module module = new PdfModule(); // JHOVE PdfModule only

				OutputHandler handler = new XmlHandler();
				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

				pathwriter = (folder + "//" + "JhoveExamination.xml");

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
						if (files.get(i).toString().contains("&")) {
							String substitute = normaliseToUtf8(files.get(i).toString());
							writer.println("<filename>" + substitute + "</filename>");
						} else {
							writer.println("<filename>" + files.get(i).toString() + "</filename>");
						}
						jb.process(app, module, handler, files.get(i).toString());
						writer.println("</item>");
					}
				}
				writer.println("</JhoveFindings>");
				writer.close();
				externalToolAnalysis.XmlParserJhove.parseXmlFile(pathwriter);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);

		}
	}
	
	private static int[] getDate() {
		String date = new java.util.Date().toString();

		String[] parts = date.split(" ");

/*		for (int i = 0; i < parts.length; i++) {
			System.out.println(i + " " + parts[i]);
		}*/

		int year = Integer.parseInt(parts[5]);
		int day = Integer.parseInt(parts[2]);

		String monthstr = (parts[1]);
		int month = 0;

		switch (monthstr) {
		case "Jan":
			month = 1;
		case "Feb":
			month = 2;
		case "Mar":
			month = 3;
		case "Apr":
			month = 4;
		case "May":
			month = 5;
		case "Jun":
			month = 6;
		case "Jul":
			month = 7;
		case "Aug":
			month = 8;
		case "Sep":
			month = 9;
		case "Oct":
			month = 10;
		case "Nov":
			month = 11;
		case "Dec":
			month = 12;
		}

		int[] dateInt = { year, month, day };

/*		for (int j = 0; j < dateInt.length; j++) {
			System.out.println(dateInt[j]);
		}*/
		
		return dateInt;
	}

	public static void JhoveGifValidator() {
		
		//TODO: XML has to be customized for GIF

		String pathwriter;

		try {
			JOptionPane.showMessageDialog(null, "Please choose a Folder with Gif files", "JHOVE Gif-Examination", JOptionPane.QUESTION_MESSAGE);
			folder = utilities.BrowserDialogs.chooseFolder();
			if (folder != null) {
				JhoveBase jb = new JhoveBase();

				String configFilePath = JhoveBase.getConfigFileFromProperties();
				jb.init(configFilePath, null);

				jb.setEncoding("UTF-8");// UTF-8 does not calculate checksums,
										// which saves time
				jb.setBufferSize(131072);
				jb.setChecksumFlag(false);
				jb.setShowRawFlag(false);
				jb.setSignatureFlag(false);

				String appName = "Customized JHOVE";
				String version = "1.0";
				int[] date =  getDate();
				String usage = "Call JHOVE via own Java";
				String rights = "Copyright nestor Format Working Group";
				App app = new App(appName, version, date, usage, rights);

				Module module = new GifModule(); 

				OutputHandler handler = new XmlHandler();
				ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());

				pathwriter = (folder + "//" + "JhoveExamination.xml");

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
						if (files.get(i).toString().contains("&")) {
							String substitute = normaliseToUtf8(files.get(i).toString());
							writer.println("<filename>" + substitute + "</filename>");
						} else {
							writer.println("<filename>" + files.get(i).toString() + "</filename>");
						}
						jb.process(app, module, handler, files.get(i).toString());
						writer.println("</item>");
					}
				}
				writer.println("</JhoveFindings>");
				writer.close();
				externalToolAnalysis.XmlParserJhove.parseXmlFile(pathwriter);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "error message", JOptionPane.ERROR_MESSAGE);

		}
	}

	public static String normaliseToUtf8(String string) {
		String[] splitstring = string.split("&");
		String substitute = splitstring[0] + "&#38;" + splitstring[1];
		return substitute;
	}

	public void init(String init) throws Exception {

	}

	public void reset() {
	}

	public static void checkGifWithJhove(File giffile) throws Exception {
		GifXmlOutput.xmlgifwriter.println("<item>");
		if (giffile.toString().contains("&")) {
			String substitute = JhoveValidator.normaliseToUtf8(giffile.toString());
			GifXmlOutput.xmlgifwriter.println("<filename>" + substitute + "</filename>");
		} else {
			GifXmlOutput.xmlgifwriter.println("<filename>" + giffile.toString() + "</filename>");
		}
		JhoveValidator.jhoveBaseGif.process(JhoveValidator.gifjhoveapp, JhoveValidator.gifmodule, JhoveValidator.handler, giffile.toString());
		GifXmlOutput.xmlgifwriter.println("</item>");
	}	

	

	public static void createJhoveChecker() throws Exception {
		JhoveValidator.jhoveBaseGif = new JhoveBase();

		String configFilePath = JhoveBase.getConfigFileFromProperties();
		JhoveValidator.jhoveBaseGif.init(configFilePath, null);

		JhoveValidator.jhoveBaseGif.setEncoding("UTF-8");
		JhoveValidator.jhoveBaseGif.setBufferSize(131072);
		JhoveValidator.jhoveBaseGif.setChecksumFlag(false);
		JhoveValidator.jhoveBaseGif.setShowRawFlag(false);
		JhoveValidator.jhoveBaseGif.setSignatureFlag(false);

		String appName = "Customized JHOVE";
		String version = "1.0";
		int[] date = { 2014, 12, 03 };
		String usage = "Call JHOVE via own Java";
		String rights = "Copyright nestor Format Working Group";
		JhoveValidator.gifjhoveapp = new App(appName, version, date, usage, rights);

		JhoveValidator.gifmodule = new GifModule(); // JHOVE GifModule only
		JhoveValidator.handler = new XmlHandler();

		JhoveValidator.handler.setWriter(GifXmlOutput.xmlgifwriter);
		JhoveValidator.handler.setBase(JhoveValidator.jhoveBaseGif);
		JhoveValidator.gifmodule.init("");
		JhoveValidator.gifmodule.setDefaultParams(new ArrayList<String>());

	}

}