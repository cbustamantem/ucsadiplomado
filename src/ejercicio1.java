
public class ejercicio1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{		
		int inicio=0;
		int fin=10;
		int contador=0;
		
		while (contador < fin)
		{
			/*
			 *  contador % 2  -> Indica si el numero que contiene la variable cuyo valor
			 *  				 divisible por 2 tenga como resto 0
			 *  
			 *  ejemplo				resto
			 *  contador=           \/   
			 *  		1	/  2  = 0,5
			 *  		2 	/  2  = 0
			 *  		3 	/  2  = 1,5
			 *  			   /\
			 *  		     divisor
			 *  Esto es muy util para buscar los valores que son primos o no primos
			 * 
			 */
			
			
			if (contador % 2== 0)
			{
				System.out.println("El numero " + contador + " SI es divisible por 2" );				
			}
			else
			{
				System.out.println("El numero " + contador + " NO divisible por 2" );
			}
			contador = contador +1;
		}

	}

}
