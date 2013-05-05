package py.edu.ucsa.ejercicio10;

public class Automatica extends Parte{
	private String anhosVidaUtil;
	
	public String getAnhosVidaUtil()
	{
		return this.anhosVidaUtil;
	}

	public void setAnhosVidaUtil(String anhosVidaUtil)
	{
		this.anhosVidaUtil = anhosVidaUtil;
	}
	
	public void mostrarDatos()
	{
		System.out.println("Parte > Automatica: Vida Util:" + this.anhosVidaUtil);		
		super.mostrarDatos();
	}

}
