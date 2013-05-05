package py.edu.ucsa.ejercicio10;

import java.util.Iterator;
import java.util.List;

public abstract class  Vehiculo {
	private String marca;
	private String duenho;
	private String modelo;
	List <Parte> partes;
	
	public String getMarca()
	{
		return this.marca;
	}
	
	public void setMarca(String marca)
	{
		this.marca = marca;
	}
	public String getModelo()
	{
		return this.modelo;
	}
	public void setModelo(String modelo)
	{
		this.modelo = modelo;
	}
	public List<Parte> getPartes()
	{
		return this.partes;
	}
	public String getDuenho()
	{
		return this.duenho;
	}
	public void setDuenho(String duenho)
	{
		this.duenho = duenho;
	}
	public abstract void acelerar();
	
	public void mostrarPartes()
	{
		System.out.println("Vehiculo  Marca:" + this.marca );
		System.out.println("Vehiculo  Modelo:" + this.modelo );
		Iterator <Parte> it = partes.iterator();
		while(it.hasNext())
		{
			Parte o=it.next();		
			o.mostrarDatos();
		}
	}
	//public String 

}
