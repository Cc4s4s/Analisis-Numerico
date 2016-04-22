package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import datos.Tabla;
import funciones.Funcion;
import funciones.NewtonRaphson;

public class Ventana extends JFrame implements ActionListener {
	
	private JPanel ventana = new JPanel();
	private JPanel panel = new JPanel();
	private panelIteradas Iteradas=new panelIteradas();
	private JLabel x0, maxIteraciones, error;
	private JTextField x0_entrada, maxIteraciones_entrada, error_entrada;
	private JButton iniciar;
	private JTabbedPane tabbed;
	private JPanel panelIteraciones=new JPanel();
	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane scrollpane;
	private Object[][] ArregloDatos;
	
	private JTabbedPane paneles=new JTabbedPane();
	
	//Grafico
	private XYDataset datosGraficoLinea;
	private JFreeChart tablaGrafico;
	private XYSeriesCollection datos;
	
	public Ventana() {
		panel = new JPanel();
		
		x0 = new JLabel("X0");
		maxIteraciones = new JLabel("Maximas Iteraciones");
		error = new JLabel("Error");
		
		x0_entrada = new JTextField(10);
		maxIteraciones_entrada = new JTextField(10);
		error_entrada = new JTextField(10);
		
		x0_entrada.setText("-1");
		maxIteraciones_entrada.setText("25");
		error_entrada.setText("1E-4");	
		
		iniciar = new JButton("Iniciar");
		iniciar.addActionListener(this);
		
		scrollpane = new JScrollPane();
		
		datos =  new XYSeriesCollection();
		datosGraficoLinea = funcion();
		tablaGrafico = ChartFactory.createXYLineChart("Newton Raphson", "Eje X", "Eje y",datosGraficoLinea,PlotOrientation.VERTICAL,true,true,false);
	    ChartPanel panelGrafico = new ChartPanel(tablaGrafico);
		
	    panelIteraciones.setLayout(new BorderLayout());
	    panelIteraciones.add(scrollpane);
	    
	    tabbed=new JTabbedPane();
		tabbed.add("Grafico",panelGrafico);
	    tabbed.add("Tabla",panelIteraciones);

		panel.setLayout(new FlowLayout());
		
		panel.add(x0);
		panel.add(x0_entrada);		
		panel.add(maxIteraciones);
		panel.add(maxIteraciones_entrada);
		panel.add(error);
		panel.add(error_entrada);
		panel.add(iniciar);
		
		ventana.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(20, 15,15,15));
		ventana.add(panel,BorderLayout.NORTH);
		ventana.add(tabbed,BorderLayout.CENTER);
		
		paneles.add("NewtonRaphson", ventana);
		paneles.add("Iteradas Funcionales",Iteradas);
		
		/*Se adapta a la interfaz grafica del sistema operativo*/
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch (Exception evt){}
		
		int ancho = 720;
		int alto = 550;
		add(paneles);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("Aproximacion de Raizes de Funciones - Newton Raphson");
		setLocation((d.width/2)-(ancho/2), (d.height/2)-(alto/2));//Establece la posicion en la pantalla
		setSize(ancho, alto);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//Cierre de la ventana
	}
	
	private XYDataset funcion()
    {
        //Se declaran las series y se llenan los datos
		XYSeries fx = new XYSeries("f(x)");        
       
        //Se grafica la funcion
        for(double x=-50; x<50; x=x+2) {
        	fx.add(x,Funcion.original(x));
        }

        datos.addSeries(fx);
        return datos;
    }
	
	private XYDataset aproximacion(double inicio, int maxIteraciones, double error)
    {
        //Se declaran las series y se llenan los datos
		XYSeries fx = new XYSeries("f(x)"); 
        XYSeries gx = new XYSeries("g(x)");        
        
        //Se realizan los calculos
        Tabla tabla = NewtonRaphson.calcular(inicio,maxIteraciones,error);
        
        //Se grafica la funcion
        for(double x=tabla.obtenerMinimo(); x<tabla.obtenerMaximo(); x=x+(tabla.obtenerMaximo()-tabla.obtenerMinimo())/100) {
        	fx.add(x,Funcion.original(x));
        }
        
      //Se dibuja la aproximacion
        for(int x=0; x<tabla.tamaño(); x++) {
        	gx.add(tabla.obtenerFila(x).obtenerX(),0);
        	gx.add(tabla.obtenerFila(x).obtenerX(),Funcion.original(tabla.obtenerFila(x).obtenerX()));
        }
        datos.removeAllSeries();
        datos.addSeries(fx);
        datos.addSeries(gx);        

        return datos;
    }

	@Override
	public void actionPerformed(ActionEvent evento) {
		if(evento.getSource() == iniciar) {
			try {
				datosGraficoLinea = aproximacion(Double.parseDouble(x0_entrada.getText()),Integer.parseInt(maxIteraciones_entrada.getText()),Double.parseDouble(error_entrada.getText()));
				Tabla tabla=NewtonRaphson.calcular(Double.parseDouble(x0_entrada.getText()), Integer.parseInt(maxIteraciones_entrada.getText()), Double.parseDouble(error_entrada.getText()));
				ArregloDatos=new Object[tabla.tamaño()][2];
				for(int x=0;x<tabla.tamaño();x++){
					ArregloDatos[x][0]="X"+x;
					ArregloDatos[x][1]=tabla.obtenerFila(x).obtenerX();
				}
				dtm = new DefaultTableModel(ArregloDatos, new Object[]{"X","Valor"}){
						public boolean isCellEditable(int row,int column){
							return false;
						}		
				};
				table = new JTable(dtm);
				scrollpane.setViewportView(table);
			}
			catch (NumberFormatException e) {
				x0_entrada.setText("-1");
				maxIteraciones_entrada.setText("25");
				error_entrada.setText("1E-4");
			}
			panel.updateUI();
		}
	}
}
