import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrayScale {

	public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("dora.jpg"));
		
		//first approach, uses function in java library BufferedImage.TYPE_BYTE_GRAY
		{
			BufferedImage imageGray = new BufferedImage(image.getWidth(), image.getHeight(),  
					BufferedImage.TYPE_BYTE_GRAY);  
			Graphics g = imageGray.getGraphics();  
			g.drawImage(image, 0, 0, null);  
			g.dispose();  
			File outputfile = new File("saved.jpg");
			ImageIO.write(imageGray, "jpg", outputfile);
		}
		
		//second approach, change the value of RGB manually to get grayscale effect
		{
			for(int w=0;w<image.getWidth();w++)
			{
				for(int h=0;h<image.getHeight();h++)
				{
					Color originalColor = new Color(image.getRGB(w, h));
					
					int r = originalColor.getRed();
					int g = originalColor.getGreen();
					int b = originalColor.getBlue();
					
					//perform operation to convert to grayscale
					int gray = (int) (r*0.3+g*0.59+b*0.11);
//					int gray = (int) (r*0.5+g*0.3+b*0.11); //more redish type of grayscale
					
					image.setRGB(w, h, new Color(gray,gray,gray).getRGB());
				}
			}
			File outputfile = new File("saved2.jpg");
			ImageIO.write(image, "jpg", outputfile);
		}
		
	}

}
