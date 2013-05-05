package py.edu.ucsa.ejercicio10;

public class Moto extends Vehiculo{
	private boolean tieneCambios;
	public boolean isTieneCambios()
	{
		return this.tieneCambios;
	}
	public void setTieneCambios (boolean tieneCambios)
	{
		this.tieneCambios = tieneCambios;
	}
	public void acelerar()
	{
		System.out.println("Acelerando Moto");
	}	
}
