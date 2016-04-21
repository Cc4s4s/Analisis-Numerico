package datos;

import java.util.ArrayList;

public class Tabla {
	
	ArrayList<Fila> datos;
	private double maximo;
	private double minimo;
	private boolean converge;
	
	public Tabla () {
		datos = new ArrayList<Fila>();
		maximo = 0;
		minimo = 0;
		converge = false;
	}
	
	public void agregarFila(double x, double error) {
		if(x > maximo)
			maximo = x;
		else if(x < minimo)
			minimo = x;
		
		datos.add(new Fila(x,error));
	}
	
	public double obtenerMaximo() {
		return maximo;
	}
	
	public double obtenerMinimo() {
		return minimo;
	}
	
	public Fila obtenerFila(int x) {
		return datos.get(x);
	}
	
	public boolean convergeAproximacion() {
		return converge;
	}
	
	public void convergeAproximacion(boolean estado) {
		converge = estado;
	}
	
	public int tamaño() {
		return datos.size();
	}
	
	public double obtenerResultadoX() {
		return datos.get(datos.size()-1).obtenerX();
	}
	
	public double obtenerResultadoError() {
		return datos.get(datos.size()-1).obtenerError();
	}
}
