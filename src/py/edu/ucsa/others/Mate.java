package py.edu.ucsa.others;

public class Mate {

	  public double div(double num,double div) throws DivByZeroException{
			
		if(div == 0)
		     throw new DivByZeroException();

		return num/div;
	  }
		
	}