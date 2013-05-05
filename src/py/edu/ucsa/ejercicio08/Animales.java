package py.edu.ucsa.ejercicio08;

public class Animales 
{
	public static void main (String ... args)
	{
		Animal a=new Gato();
	    a.respirar();
	    a.comer();
	    	    
	    Animal b=new Perro();
	    b.respirar();
	    b.comer();
	   
	    ((Gato)a).maullar();	
	    ((Perro)b).ladrar();
	}
	

}
