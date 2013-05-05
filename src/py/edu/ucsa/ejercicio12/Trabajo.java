package py.edu.ucsa.ejercicio12;

public abstract class Trabajo 
{
	private int cantHoras=0;
	private String descripcion="";
	private boolean finalizado = false;
	private int id=0;
	private double precioFinal=0;

	public int getCantHoras()
	{
		return this.cantHoras;
	}
	public String getDescripcion()
	{
		return this.descripcion;
	}
	public int getId()
	{
		return this.id;
	}
	public double getPrecioFinal()
	{
		return this.precioFinal;
	}
	public void incrementarHoras(int horas)
	{
		this.cantHoras += horas;
	}
	public boolean isFinalizado()
	{
		return finalizado;
	}
	public void setFinalizad(boolean finalizado)
	{
		this.finalizado = finalizado;
	}
	public void setId(int id)
	{
		this.id= id;
	}
	
}
