package core;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


public class ScreenCapture {
	
	private static ScreenCapture instance;
	private Robot robot;
	private ScreenCapture() throws AWTException {
		robot = new Robot();
	}
	
	public static ScreenCapture getScreenCapture() throws AWTException {
		if(instance == null) instance = new ScreenCapture();
		return instance;
	}
	
	public String captureScreen() {
		try {
	        Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	        BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
	        String name = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) ;
	        File file = new File(name+".png");
	        boolean status = ImageIO.write(bufferedImage, "png", file);
	        return file.getAbsolutePath();

	    } catch (IOException ex) {
	        System.err.println(ex);
	    	return "";
	    }
	}
}
