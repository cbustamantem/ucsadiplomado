package py.edu.ucsa.ejercicio12;

public abstract class Reparacion extends Trabajo 
{
	private double precioTotalMateriales = 0;
	public double getPrecioTotalMateriales()
	{
		return this.precioTotalMateriales;
	}
	public void incrementarPrecioTotalMateriales(double precio)
	{
		this.precioTotalMateriales+= precio;
	}
}
