
public class Robot 
{
	private int 	pasoRobot;
	private String nombreRobot;
	
	
	//INICIO DEL PROGRAMA
	public static void main(String ... args)
	{
		Robot robotin = new Robot();
		robotin.setNombre("roboludo");
		robotin.nombreRobot = "roboludo";
		robotin.Caminar(5);
		//robotin.
	}
	
	
	public void setNombre(String nombre)
	{
		this.nombreRobot = nombre;
	}
	
	//CAMINAR ROBOT
	public void Caminar(int pasosACaminar)
	{
		int pasoActual=0;		
		while (pasoActual < pasosACaminar)
		{
			System.out.println(this.nombreRobot + " - Caminando Paso " + pasoActual);
			pasoActual = pasoActual + 1;
		}
		
		
	}//fin metodo Caminar
	

}//fin clase
