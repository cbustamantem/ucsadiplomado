package py.edu.ucsa.ejercicio14;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class EjemploSocketServer {
	
	private ServerSocket servidorSocket;
	private int port=7777;
	
	public EjemploSocketServer()
	{
		try 
		{
			servidorSocket = new ServerSocket(port);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Ourrio un erro IO exception " + e);
			e.printStackTrace();
		}
	}
	
	private void manejarConexion()
	{
		try {
			System.out.println("Escuchando conexion al puerto: " + this.port);
			Socket socket = servidorSocket.accept();
			new ManejadorSocket(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String ... args)
	{
		EjemploSocketServer ejSocket = new EjemploSocketServer();
		ejSocket.manejarConexion();
	}
}
