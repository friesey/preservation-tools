package externalToolAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.JhoveException;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.handler.XmlHandler;
import edu.harvard.hul.ois.jhove.module.PdfModule;

// the libray JhoveApp.jar is not in the maven library

public class RunJhoveApp {

	public static void main(String args[]) throws Exception {

		// Choosing Folder which is to be examined

		JhoveBase jb = new JhoveBase();

		// der Path stimmt so noch nicht
		String configFilePath = "C://Users//Friese Yvonne//jhove-1_11//jhove//conf//jhove.conf";

		jb.init(configFilePath, null);

		jb.setEncoding("UTF-8"); /*
								 * UTF-8 does not calculate checksums, which
								 * saves time
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

		Module module = new PdfModule(); /* to try this with PdfModule only */

		OutputHandler handler = new XmlHandler();
		String outputFile = ("C://temp1//JHOVEOutput.xml");
		String folder = ("C://FileSample//PDFs//Doreen_6_Seiten_Druck");

		ArrayList<File> files = utilities.ListsFiles.getPaths(new File(folder), new ArrayList<File>());
		String[] filesarr = new String[files.size()];

		for (int i = 0; i < files.size(); i++) {
			filesarr[i] = files.get(i).toString();
		}

	//	jb.dispatch(app, module, null, handler, outputFile, filesarr);
		
		PrintWriter writer = new PrintWriter(new FileWriter(folder + "//" + "JhoveExamination" + ".txt")); 
		handler.setWriter (writer);
		handler.setBase (jb);
		module.init("");
		module.setDefaultParams(new ArrayList<String>());
		
		for (int i = 0; i < files.size(); i++) {		
		jb.process (app, module, handler, files.get(i).toString());
		}

	}

	/*
	 * catch (Exception e) { System.out.println("Exception: " + e); }
	 * 
	 * }
	 */
}
