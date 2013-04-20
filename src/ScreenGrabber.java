import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;


public class ScreenGrabber {

	private static final String colorHtml="<body bgcolor=\"silver\">";
	
	public static void main(String[] args) throws Exception {
		Container container = new ColorServer();
		Server server = new ContainerServer(container);
		Connection connection = new SocketConnection(server);
		SocketAddress address = new InetSocketAddress(7766);

		connection.connect(address);
		
		while(true)
		{
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage bitmap = new Robot().createScreenCapture(screenRect);
		
		long redBucket = 0;
		long greenBucket = 0;
		long blueBucket = 0;
		long pixelCount = 0;

		for (int y = 0; y < bitmap.getHeight(); y++)
		{
		    for (int x = 0; x < bitmap.getWidth(); x++)
		    {
		        Color c = new Color(bitmap.getRGB(x, y));

		        pixelCount++;
		        redBucket += c.getRed();
		        greenBucket += c.getGreen();
		        blueBucket += c.getBlue();
		        // does alpha matter?
		    }
		}

		int averageRed = (int)(redBucket / pixelCount);
		int averageGreen = (int)(greenBucket / pixelCount);
		int averageBlue = (int)(blueBucket / pixelCount);
		
		String hex = String.format("#%02x%02x%02x", averageRed, averageGreen, averageBlue);
		
		Color averageColor = new Color(averageRed,
									   averageGreen,
									   averageBlue);
		
		System.out.println(averageColor);
		ColorServer colorServer = (ColorServer) container;
		String htmlCodeColor = "<META HTTP-EQUIV=\"refresh\" CONTENT=\"1\"><body bgcolor=\""+hex+"\">";
		colorServer.setColor(htmlCodeColor);
		
		System.out.println(htmlCodeColor);
		
//		FileOutputStream out = new FileOutputStream("temp.bmp");
		
//		ImageIO.write(capture, "bmp", out);
		
		Thread.sleep(100);
		}
	}
	
}
