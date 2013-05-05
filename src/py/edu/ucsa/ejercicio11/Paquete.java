package py.edu.ucsa.ejercicio11;

public class Paquete implements Enviable 
{
	String contenido="";
	String destinatario="";
	int peso=0;
	@Override
	public void enviar() {
		// TODO Auto-generated method stub
		System.out.println("Paquete - Enviar ");
	}
	public String getContenido()
	{
		return this.contenido;
	}
	public String getDestinatario()
	{
		return this.destinatario;
	}
	public int getPeso()
	{
		return this.peso;
	}
	public void setContenido(String contenido)
	{
		this.contenido=  contenido;
	}
	public void setDestinatario(String destinatario)
	{
		this.destinatario = destinatario;
	}
	public void setPeso (int peso)
	{
		this.peso = peso;
	}

}
