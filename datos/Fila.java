package datos;

public class Fila {
	
	private double x, error;
	
	public Fila(double x, double error) {
		this.x = x;
		this.error = error;
	}
	
	public double obtenerX() {
	 return x;
	}
	
	public double obtenerError() {
		return error;
	}
}
