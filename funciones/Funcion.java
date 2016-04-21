package funciones;

public class Funcion {
	public static double original(double x)
	{
		return (10*Math.pow(x, 2))*Math.sin(x)+(2.3*Math.pow(x, 3))+(6);
	}
	
	public static double derivada(double x)
	{
		return (20*x)*Math.sin(x)+(10*Math.pow(x, 2))*Math.cos(x)+(3.3*Math.pow(x, 2));
	}
}
