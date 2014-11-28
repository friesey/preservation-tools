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
import edu.harvard.hul.ois.jhove.module.PdfModule;

// the libray JhoveApp.jar is not in the maven library

public class RunJhoveApp {

	public static void main(String args[]) throws Exception {

		PrintWriter testingpurposes = new PrintWriter(new FileWriter("D://testingpurposes.txt"));
		String pathwriter;
		try {

			JOptionPane.showMessageDialog(null, "Please choose a Folder with PDF files", "FolderExamination", JOptionPane.QUESTION_MESSAGE);
			String folder = utilities.FolderBrowserDialog.chooseFolder();

			if (folder != null) {

				JhoveBase jb = new JhoveBase();

				// TODO: Edit the Path to JHOVE config file or find a better
				// solution for this.
				String configFilePath = "C://Users//Friese Yvonne//jhove-1_11//jhove//conf//jhove.conf";

				jb.init(configFilePath, null);

				jb.setEncoding("UTF-8");
				/*
				 * * UTF-8 does not calculate checksums, which saves time
				 */
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
					testingpurposes.println(i + files.get(i).toString());
					if (filetools.GenericFileAnalysis.testFileHeaderPdf(files.get(i)) == true) {
						writer.println("<item>");
						writer.println("<filename>" + files.get(i).toString() + "</filename>");
						jb.process(app, module, handler, files.get(i).toString());

						// filetools.pdf.PdfAnalysis.analysePdfObjects(files.get(i));

						// TODO: This does not work yet. Would be a great idea,
						// though.
						/*
						 * URL url = files.get(i).toURI().toURL(); RepInfo
						 * infoobject = new RepInfo(url.toExternalForm());
						 * List<Message> messages = infoobject.getMessage();
						 * Iterator<Message> iterator = messages.iterator();
						 * while (iterator.hasNext()) {
						 * System.out.println(iterator.next()); } String format
						 * = infoobject.getFormat();
						 */
						writer.println("</item>");
					}
				}
				writer.println("</JhoveFindings>");
				writer.close();

				externalToolAnalysis.JhoveStatistics.JhoveOutputAnalysis(pathwriter);
				testingpurposes.close();

				externalToolAnalysis.XmlParserJhove.parseXmlFile(pathwriter);

			}
		}

		catch (Exception e) {
			testingpurposes.println("Exception: " + e);
		}

	}

	public void init(String init) throws Exception {

	}

	public void reset() {

	}

}