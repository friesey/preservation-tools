package preservetools.externalToolAnalysis;

import java.io.IOException;

public class RunJhove {

	static String JhovePath;

	public static void main(String args[]) throws IOException {

		JhovePath = "C://Users//Friese Yvonne//jhove-1_11//jhove//bin//JhoveView.jar";

		try {

			ProcessBuilder startJhove = new ProcessBuilder("java", "-Xmx1024m", "-Xms1024m",
				    "-DTOOLS_DIR=/home/IM/work/dist", "-Daoi=whole", 
				    "-jar", JhovePath);
			
			startJhove.start();
		}

		catch (IOException e) {
			System.out.println(e);
		}

	}

}
