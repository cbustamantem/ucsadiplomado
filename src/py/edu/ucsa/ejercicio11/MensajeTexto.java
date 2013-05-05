package py.edu.ucsa.ejercicio11;

public class MensajeTexto extends Mensaje
{
	private int cantMaxCaracteres=0;
	public boolean avisarCantMaxSuperada(int cantidadCaracteres)
	{
		 
		if ( cantidadCaracteres  >= this.cantMaxCaracteres)
		{
			System.out.println(" MensajeTexto  Cantidad Maxima de caracteres superada ");
			return false;
		}		
		else
		{				
			return true;
		}
	}
	public int getCantMaxCaracteres()
	{
		return this.cantMaxCaracteres;
	}
	public void setCantMaxCaracteres(int cantMaxCaracteres)
	{
		this.cantMaxCaracteres = cantMaxCaracteres;		
	}
	@Override
	public void enviar() {
		// TODO Auto-generated method stub
		
	}
	public void setCuerpo(String cuerpo)
	{
		System.out.println("Asignando cuerpo");
		if (this.avisarCantMaxSuperada(cuerpo.length()))
		{
			super.setCuerpo(cuerpo);
		}		
	}
	
}
