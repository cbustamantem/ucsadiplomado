package py.edu.ucsa.ejercicio14.socketClient;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class socketClient 
{
	public static void main(String ... args)
	{
		try
		{
			InetAddress host = InetAddress.getLocalHost();
			Socket socket = Socket(host,7777);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject("Hola Server  soy cliente");
			
			
		}
		catch (Exception e)
		{
			
		}
		
	}

	private static Socket Socket(InetAddress host, int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
