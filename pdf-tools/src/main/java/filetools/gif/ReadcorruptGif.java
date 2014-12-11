package filetools.gif;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

public class ReadcorruptGif {

	public static void main(String args[]) throws Exception {

		String giffile = utilities.BrowserDialogs.chooseFile();

		File gif = new File(giffile);

		try {
			URL url = new URL("file://" + gif.getPath());
			System.out.println(url);
			BufferedImage img = ImageIO.read(url);
			
			

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
