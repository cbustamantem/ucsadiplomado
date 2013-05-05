package py.edu.ucsa.ejercicio10;

public class Componente {
	private String nombre;
	
	public String getNombre()
	{
		return this.nombre;
	}
	public void setNombre(String nombre)
	{
		 this.nombre = nombre;
	}
	public void mostrarDatos()
	{
		System.out.println("Componente:" + this.nombre);
	}
}
