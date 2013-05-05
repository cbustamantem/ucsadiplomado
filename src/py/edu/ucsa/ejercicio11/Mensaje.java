package py.edu.ucsa.ejercicio11;

import java.util.Date;

public abstract class Mensaje implements Legible, Enviable {
	
	private String cuerpo="";
	private String destinatario="";
	private Date fecha=new Date();
	
	public String getCuerpo()
	{
		return this.cuerpo;
	}
	
	public String getDestinatario()
	{
		return this.destinatario;
	}
	
	public Date getFecha()
	{
		return this.fecha;
	}
	public void leer()
	{
		System.out.println("Leyendo " + this.cuerpo);
	}
	public void setCuerpo(String cuerpo)
	{
		this.cuerpo = cuerpo;
	}
	public void setDestinatario(String destinatario)
	{
		this.destinatario = destinatario;
	}
	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}	
}
