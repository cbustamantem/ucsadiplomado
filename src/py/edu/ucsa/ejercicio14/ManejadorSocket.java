package py.edu.ucsa.ejercicio14;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ManejadorSocket implements Runnable {
	private Socket socket;
	public ManejadorSocket(Socket socket) 
	{
		this.socket = socket;	
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try 
		{
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			String mensajeDelCliente = (String)ois.readObject();
			System.out.println("Mensaje que envia el cliente : " + mensajeDelCliente);
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ois.close();
			oos.close();
			socket.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Ourrio un erro IO exception " + e);
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Ourrio un error ClassNotFound" + e);
			e.printStackTrace();
		}
		
		
	}

}
