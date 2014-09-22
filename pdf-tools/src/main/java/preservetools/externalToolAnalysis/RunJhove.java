package preservetools.externalToolAnalysis;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;

public class RunJhove {

	static String JhovePath;

	public static void main(String args[]) throws IOException, InterruptedException {

		JhovePath = "C://Users//Friese Yvonne//jhove-1_11//jhove//bin//JhoveView.jar";

		try {

			ProcessBuilder runsJhove = new ProcessBuilder("java", "-Xmx1024m", "-Xms1024m",
					"-DTOOLS_DIR=/home/IM/work/dist", "-Daoi=whole", "-jar", JhovePath);

			Process p = runsJhove.start();
			
			File testPdf = new File("C://PresToolsTest//Input.txt");
			
			p.getInputStream();
			
			assert runsJhove.redirectInput() == Redirect.from(testPdf);

			File log = new File("C://PresToolsTest//log.txt");
			
			// TODO: Find out how to put files/folder in runsJHOVE and how to get the output

			runsJhove.redirectErrorStream(true);
			runsJhove.redirectOutput(Redirect.appendTo(log));

			
			p.waitFor();			
			p.destroy();
		}

		catch (IOException e) {
			System.out.println(e);
		}
	}
}
