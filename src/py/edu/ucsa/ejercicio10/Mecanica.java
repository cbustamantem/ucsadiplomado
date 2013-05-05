package py.edu.ucsa.ejercicio10;

public class Mecanica extends Parte {
	private String origen;
	
	public String getOrigen()
	{
		return this.origen;
	}

	public void setOrigen(String origen)
	{
		this.origen = origen;
	}
	
	public void mostrarDatos()	
	{								
		System.out.println("Parte > Mecanica :");
		super.mostrarDatos();
	}
}
