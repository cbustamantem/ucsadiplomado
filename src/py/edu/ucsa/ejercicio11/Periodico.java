package py.edu.ucsa.ejercicio11;

import java.util.Date;

public class Periodico implements Legible,  Hojeable, Enviable
{
	private String destinatario="";
	private Date fecha= new Date();
	private String nombre="";
	@Override
	public void hojear() {
		// TODO Auto-generated method stub
		System.out.println(" Periodico - Hojear");	
	}

	@Override
	public void leer() {
		// TODO Auto-generated method stub
		System.out.println(" Periodico - Leer");		
	}

	@Override
	public void enviar() {
		// TODO Auto-generated method stub
		System.out.println(" Periodico - Enviar");	
	}
	public String getDestinatario()
	{
		return this.destinatario;
	}
	public Date getFecha()
	{
		return this.fecha;
	}
	public String getNombre()
	{
		return this.nombre;
	}
	public void setDestinatario(String destinatario)
	{
		this.destinatario = destinatario;
	}
	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	
}
