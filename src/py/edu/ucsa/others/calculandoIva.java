package py.edu.ucsa.others;

public class calculandoIva 
{
	double monto = getMonto();
	double ivaPorcentaje = getPorcentaje();
	public static void main(String ... args)
	{
		calculandoIva  calc = new calculandoIva();
		calc.calcularIva();
	}
	public double getMonto()
	{
		return new Double(2.2);
	}
	public double getPorcentaje()
	{
		return new Double(0);
	}
	public void calcularIva()
	{
		Mate mate = new Mate();
		try 
		{
			double montoIva = mate.div(monto,ivaPorcentaje);
			System.out.println("El monto de IVA es" + montoIva);
		} 
		catch (DivByZeroException e) 
		{
			System.out.println("El IVA no puede ser cero");
		}

	}
}
