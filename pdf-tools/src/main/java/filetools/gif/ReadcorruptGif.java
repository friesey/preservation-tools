package filetools.gif;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RenderedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.sanselan.formats.gif.GifImageParser;
import org.apache.xmlgraphics.image.loader.util.ImageUtil;

import ij.*;

public class ReadcorruptGif {

	public static void main(String args[]) throws Exception {

		String gifstr = utilities.BrowserDialogs.chooseFile();		
			
		File gif = new File(gifstr);			

		
		java.awt.Image toolkitImage = Toolkit.getDefaultToolkit().getImage(gifstr); // this

		



		

	}
}
