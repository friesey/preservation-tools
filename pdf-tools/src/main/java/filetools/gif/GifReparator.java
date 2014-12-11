package filetools.gif;

//import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;

//import ij.*;

public class GifReparator {

	public static void repairgif(File giffile) throws IOException {

		ImageInputStream iis = ImageIO.createImageInputStream(giffile);		
		iis.close();
		
		String ext = FilenameUtils.getExtension(giffile.toString()).toLowerCase();
		//all the files should have "gif"
		
		Iterator<?> imgReaders = ImageIO.getImageReadersByFormatName(ext);
		
		
		
	}
}