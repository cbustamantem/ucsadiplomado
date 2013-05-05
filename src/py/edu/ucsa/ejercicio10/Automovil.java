package py.edu.ucsa.ejercicio10;

public class Automovil extends Vehiculo implements Arreglable 
{
	private boolean deportivo;
	public boolean isDeportivo()
	{
		return this.deportivo;
	}
	public void setDeportivo (boolean deportivo)
	{
		this.deportivo = deportivo;
	}
	public void acelerar()
	{
		System.out.println("Acelerando Automovil");
	}
	@Override
	public void arreglar() {
		// TODO Auto-generated method stub
		
	}
	
	

}
