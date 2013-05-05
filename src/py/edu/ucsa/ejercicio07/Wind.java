package py.edu.ucsa.ejercicio07;

public class Wind extends Instruments
{
	 public void play(Note n) {
	    System.out.println("Wind.play() " + n);
	  }
	 
	 public void tocar(Note n)
	 {
		 System.out.println("Wind.play() " + n);
	 }
	 public void adjust()
	 {
		 System.out.println("Wind.adjust() ");
	 }
	 

}
