package ejercicio.casting;

public class Perrera 
{
	public static void main(String ... args)
	{
		Perro perrito = new PerroPequines();
		Perro perrote = new PerroGranDanes();
		perrito.ladrar();
		perrote.ladrar();
	}

}
