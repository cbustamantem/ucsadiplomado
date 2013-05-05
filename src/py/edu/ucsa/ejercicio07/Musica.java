package py.edu.ucsa.ejercicio07;

public class Musica 
{
	public static void main (String ... args)
	{
		System.out.println("Tocando Musica");
		Wind flute = new Wind();
		//Instruments flauta = new Instruments();
		System.out.println("Diferencias e/ upcasting");
		//flauta.play(Note.MI);
		flute.play(Note.MI);
		tune (flute);
		
	}
	public static void tune(Instruments i)
	{
		i.play(Note.DO);
		
	}

}
