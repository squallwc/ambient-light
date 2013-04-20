import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Random;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerServer;
import org.simpleframework.transport.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;


public class ColorServer implements Container{
	
	private static String color="";
	
	public static String getColor() {
		return color;
	}

	public static void setColor(String color) {
		ColorServer.color = color;
	}

	public static void main(String[] args) throws Exception {
		  Container container = new ColorServer();
	      Server server = new ContainerServer(container);
	      Connection connection = new SocketConnection(server);
	      SocketAddress address = new InetSocketAddress(7766);

	      connection.connect(address);
	      
//	      while(true)
//	      {
//	    	 color=""+ new Random().nextInt();
//	    	 Thread.sleep(100);
//	      }
	}

	@Override
	public void handle(Request request, Response response) {
		try {
			PrintStream body = response.getPrintStream();
//			long time = System.currentTimeMillis();

			response.setValue("Content-Type", "text/html");
			response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
//			response.setDate("Date", time);
//			response.setDate("Last-Modified", time);

			body.println(color);
			body.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} 

}
