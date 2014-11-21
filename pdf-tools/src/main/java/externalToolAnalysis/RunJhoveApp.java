/*package externalToolAnalysis;

import java.io.FileNotFoundException;

import edu.harvard.hul.ois.jhove.App;
import edu.harvard.hul.ois.jhove.JhoveBase;
import edu.harvard.hul.ois.jhove.JhoveException;
import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.OutputHandler;
import edu.harvard.hul.ois.jhove.handler.XmlHandler;
import edu.harvard.hul.ois.jhove.module.PdfModule;

 the libray JhoveApp.jar is not in the maven library

public class RunJhoveApp {

	public static void main(String args[]) throws JhoveException, FileNotFoundException {

		try {

			String outputFile = utilities.FileBrowserDialog.chooseFile();
			
			 * Findings will be written here
			 

			String folder = utilities.FolderBrowserDialog.chooseFolder();
			
			 * Choosing Folder which is to be examined
			 

			JhoveBase jb = new JhoveBase();

			//der Path stimmt so noch nicht
			String configFilePath = utilities.FileBrowserDialog.chooseFile();
			System.out.println (configFilePath);

			jb.init(configFilePath, null);

			jb.setEncoding("UTF-8"); 
									 * UTF-8 does not calculate checksums, which
									 * saves time
									 
			jb.setTempDirectory("/user/me/temp1");
			jb.setBufferSize(131072);
			jb.setChecksumFlag(false);
			jb.setShowRawFlag(false);
			jb.setSignatureFlag(false);

			String appName = "Yvonne JHOVE wrapper";
			String version = "1.0";
			int[] date = { 2014, 11, 17 };
			// year, month, day
			String usage = "Call JHOVE via own Java";
			String rights = "Copyright nestor Format Working Group";
			App app = new App(appName, version, date, usage, rights);

			Module module = new PdfModule();  to try this with PdfModule only 

			OutputHandler handler = new XmlHandler();

			jb.process(app, module, handler, folder);

		}

		catch (Exception e) {
			System.out.println("Exception: " + e);
		}

	}

}
*/