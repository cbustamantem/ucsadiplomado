package py.edu.ucsa.others;

import java.util.ArrayList;

public class Deposito {

	private ArrayList datos;
	public Deposito()
	{
		datos = new ArrayList();
	}
	public void agregar(Ladrillo ladrillo)
	{
		if(ladrillo == null)		
			throw new NullPointerException();
		datos.add(ladrillo);
	}
	public static void main(String[] args) 
	{
		Deposito d = new Deposito();
		d.agregar(new Ladrillo());
		//d.agregar(null);
		System.out.println("Resultado: " + Boolean.toString(prueba()));
	}
	
	public static boolean prueba()
	{
		try
		{
			return false;
		}
		catch (Exception ex)
		{
			
		}
		finally
		{
			return true;
		}
	}
}

