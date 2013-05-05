package py.edu.ucsa.ejercicio10;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Parte 
{
	private String nombre;
	private String material;
	private List <Componente> componentes;

	public String getNombre()
	{
		return this.nombre;
	}
	
	public void setNombre (String nombre)
	{
		this.nombre= nombre;
	}
	
	public String getMaterial()
	{
		return this.material;
	}
	
	public void setMaterial (String material)
	{
		this.material = material;
	}
	
	public List<Componente> getComponent()
	{	
		if (this.componentes == null)
		{
			this.componentes = new ArrayList<Componente>();			
		}
		return this.componentes;
	}
	
	public void mostrarDatos()
	{
		System.out.println("Componente -  nombre:" + this.nombre);
		System.out.println("Componente -  material:" + this.material);
					
		Iterator <Componente> it = componentes.iterator();
		while(it.hasNext())
		{
			Componente o=it.next();		
			o.mostrarDatos();			
		}
	}
}

