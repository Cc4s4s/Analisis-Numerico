package funciones;

import datos.Tabla;

public class NewtonRaphson {
	public static Tabla calcular(double x, int maximasIteraciones, double errorMinimo)
	{
		Tabla datos = new Tabla();
		int i=0;
		double anterior = 0, error = 1;
		datos.agregarFila(x,error);
		while(i<maximasIteraciones && error > errorMinimo)
		{
			x = x - Funcion.original(x) / Funcion.derivada(x);
			error = Math.abs(x-anterior);
			anterior = x;
			i++;
			datos.agregarFila(x,error);
		}
		if(error <= errorMinimo)
			datos.convergeAproximacion(true);
		return datos;
	}
}
