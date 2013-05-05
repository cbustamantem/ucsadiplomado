package py.edu.ucsa.ejercicio09;

public class SuperHeroes 
{

	public static void main ( String... args)
	{
		//Volador volador = new Volador();
		Volador volador = new Superman();
		volador.volar();
		
		//BATALLA
		Batalla batalla=new Batalla();
		Superman superman=new Superman();
		AntiHeroe antiHeroe=new AntiHeroe();
				
		batalla.hacerVolar(superman,antiHeroe);
				
		batalla.hacerPelear(superman,antiHeroe);
				
		//batalla.hacerMentir(superman,antiHeroe);
		
	}
}
