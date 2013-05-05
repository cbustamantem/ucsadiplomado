package py.edu.ucsa.ejercicio11;

public class Email extends Mensaje
{
	private String asunto;
	public void adjuntar()
	{
		System.out.println("Adjuntar");
	}
	public void enviar()
	{
		System.out.println("Enviar");
	}
	public String getAsunto()
	{
		return this.asunto;
	}
	public void setAsunto(String asunto)
	{
		this.asunto = asunto;
	}
}
