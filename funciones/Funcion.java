package funciones;

public class Funcion {
	public static double original(double x)
	{
		return (10*x*x)*Math.sin(x)+(2.3*x*x*x)+(6);
	}
	
	public static double derivada(double x)
	{
		return (20*x)*Math.sin(x)+(10*x*x)*Math.cos(x)+(3.3*x*x);
	}
}
