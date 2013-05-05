/*
 * Trabajo Practico UCSA
 * @author Carlos Bustamante
 */
package py.edu.ucsa.trabajoPractico;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
public class FrmViewProductos 
{

	private JFrame frame ;
	private JPanel mainPanel;
	private JTable tablaDatos ;
	private JPanel panelPrincipal; 
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JButton buttonBuscar 		= null;

	private FileManager fileProductos = new FileManager();
	private JSplitPane splitPane;
	private JFileChooser fc;	
	private DefaultTableModel modelo;
    private JTextArea log;
    private JTextField txtBuscar;
    private JLabel lbBuscar;
    
    public FrmViewProductos()
    {
    	this.createAndShowGUI();
    }
	private void createAndShowGUI()
	{
		 //Create and set up the window.
		
		frame = new JFrame("Vista Productos");
      
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        fileProductos.setFileName("productos.txt");
        
        panelPrincipal = new JPanel(new FlowLayout());
        //JPANEL
        JPanel panelFormulario = new JPanel(new GridLayout(5,2) );
        JPanel panelBotonera= new JPanel(new GridLayout(1,2) );
        
        	        	        	        	     
        // TextBox        
        txtBuscar 			= new JTextField(20);
        
              
        // Labels
        lbBuscar = new JLabel("Nombre",  JLabel.CENTER);
        
    
        
        //Botones
        buttonBuscar = new JButton("Buscar");       
        
        ActionListener listenerBotones = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				JButton botonListener =  (JButton) e.getSource();
				if (botonListener.getText().trim().equals("Buscar") == true)
				{
					buscarTabla();
					
					//cargarTabla();
				}
				else if(botonListener.getText().trim().equals("Cancelar") == true)
				{									       
			        cargarTabla();
				}
				
			}
		};
		buttonBuscar.addActionListener(listenerBotones);
		
		modelo =new DefaultTableModel();
		tablaDatos = new JTable();
		
		configurarTabla();
		cargarTabla();
		tablaDatos.setModel(modelo);
		tablaDatos.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tablaDatos);
		
        panelFormulario.add(lbBuscar);
        panelFormulario.add(txtBuscar);            
        panelPrincipal.add(panelFormulario);        
        
        panelBotonera.add(buttonBuscar);       
       
        panelPrincipal.add(panelBotonera);
        
        panelPrincipal.add(scrollPane);
        
        
        frame.add(panelPrincipal);
        	        	       
        
        //frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setSize(600,500);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);
         	
	}
	public void guardarProductos()
	{
		System.out.println("Guardando productos ");
		String datosProducto="";
		
		
		
		System.out.println("Datos " + datosProducto);
		
		fileProductos.agregarArchivo(datosProducto);
		
	}
	public void configurarTabla()
	{
		modelo.addColumn("Codigo");
		modelo.addColumn("Descripcion");
		modelo.addColumn("Precio");
		modelo.addColumn("Cantidad en Stock");
		modelo.addColumn("Pais de Procedencia");
		//modelo.addColumn("PaisProcedencia");
	}
	public void cargarTabla()
	{
		//TABLA
		limpiarTabla();
		String[][] datosProductos = obtenerDatos();
		int contador=0;
		while (contador < datosProductos.length)
		{
			modelo.addRow(datosProductos[contador]);
			contador++;
		}						
	}
	public void buscarTabla()
	{
		//TABLA
		limpiarTabla();
		String[][] datosProductos = obtenerDatos();
		int contador=0;
		String textoABuscar=".*"+ txtBuscar.getText()+".*";
		while (contador < datosProductos.length)
		{
			if (datosProductos[contador][0].matches(textoABuscar) == true)
			{
				modelo.addRow(datosProductos[contador]);
			}
			else if (datosProductos[contador][1].matches(textoABuscar) == true)
			{
				modelo.addRow(datosProductos[contador]);
			}
			else if (datosProductos[contador][2].matches(textoABuscar) == true)
			{
				modelo.addRow(datosProductos[contador]);
			}
			
				
			contador++;
		}						
	}
	public void limpiarTabla()
	{
		while (modelo.getRowCount() >0)
		{
			modelo.removeRow(0);
		}			
	}
	public String [][] obtenerDatos()
	{
		System.out.println("Cargar Tabla");
		
		ArrayList datos = new ArrayList();
		datos = fileProductos.leerArchivo();
		System.out.println("Cargar Tabla> Datos obtenidos "+ datos.size());

		int contador=0;
		String datosProductos[][] = new String [datos.size()][5];
		while (contador < datos.size() )
		{
			String cadena = datos.get(contador).toString();
			if ((cadena != null) || !(cadena.trim().equals("")) )
			{
				try
				{
					datosProductos[contador]= cadena.split(";");
				}
				catch(Exception ex)
				{
					System.out.println("Error" + ex);
				}
			}
			
					
			contador ++;
		}		
		return datosProductos;
		
	}

}
