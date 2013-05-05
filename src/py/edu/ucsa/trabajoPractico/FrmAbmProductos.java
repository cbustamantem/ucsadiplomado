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

public class FrmAbmProductos extends Frame
{
	private JFrame frame ;
	private JPanel mainPanel;
	private JTable tablaDatos ;
	private JPanel panelPrincipal; 
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JButton buttonGuardar 		= null;
	private JButton buttonCancelar 		= null;
	private FileManager fileProductos = new FileManager();
	private JSplitPane splitPane;
	private JFileChooser fc;	
	private DefaultTableModel modelo;
    private JTextArea log;
    private JTextField txtCodigo, txtDescripcion,txtPrecio,txtCantidadStock, txtPaisProcedencia;
    private JLabel lbCodigo, lbDescripcion, lbPrecio, lbCantidadStock, lbPaisProcedencia;
    
    public FrmAbmProductos()
    {
    	this.createAndShowGUI();
    }
	private void createAndShowGUI()
	{
		 //Create and set up the window.
		
		frame = new JFrame("ABM Productos");
      
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        fileProductos.setFileName("productos.txt");
        
        panelPrincipal = new JPanel(new FlowLayout());
        //JPANEL
        JPanel panelFormulario = new JPanel(new GridLayout(5,2) );
        JPanel panelBotonera= new JPanel(new GridLayout(1,2) );
        
        	        	        	        	     
        // TextBox
        txtCodigo 			= new JTextField(20);
        txtDescripcion 		= new JTextField(20);
        txtPrecio 			= new JTextField(20);
        txtCantidadStock 	= new JTextField(20);
        txtPaisProcedencia 	= new JTextField(20);
        
        // Labels
        lbCodigo 			= new JLabel("Codigo",  JLabel.CENTER);
        lbDescripcion 		= new JLabel("Descripcion",  JLabel.CENTER);
        lbPrecio 			= new JLabel("Precio",  JLabel.CENTER);
        lbCantidadStock 	= new JLabel("Cantidad Stocl",  JLabel.CENTER);
        lbPaisProcedencia 	= new JLabel("Pais Procedencia",  JLabel.CENTER);
    
        
        //Botones
        buttonGuardar = new JButton("Guardar");
        buttonCancelar = new JButton("Cancelar");
        
        ActionListener listenerBotones = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton botonListener =  (JButton) e.getSource();
				if (botonListener.getText().trim().equals("Guardar") == true)
				{
					guardarProductos();
					tablaDatos.removeAll();
					cargarTabla();
				}
				else if(botonListener.getText().trim().equals("Cancelar") == true)
				{
					txtCodigo.setText("");
			        txtDescripcion.setText("");
			        txtPrecio.setText("");
			        txtCantidadStock.setText("");
			        txtPaisProcedencia.setText("");
			        tablaDatos.updateUI();
			        cargarTabla();
				}
				
			}
		};
		buttonGuardar.addActionListener(listenerBotones);
		buttonCancelar.addActionListener(listenerBotones);
		modelo =new DefaultTableModel();
		tablaDatos = new JTable();
		
		configurarTabla();
		cargarTabla();
		tablaDatos.setModel(modelo);
		tablaDatos.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tablaDatos);
		
        panelFormulario.add(lbCodigo);
        panelFormulario.add(txtCodigo);
        panelFormulario.add(lbDescripcion);
        panelFormulario.add(txtDescripcion);
        panelFormulario.add(lbPrecio);
        panelFormulario.add(txtPrecio);
        panelFormulario.add(lbCantidadStock);
        panelFormulario.add(txtCantidadStock);
        panelFormulario.add(lbPaisProcedencia);
        panelFormulario.add(txtPaisProcedencia);      
        panelPrincipal.add(panelFormulario);        
        
        panelBotonera.add(buttonGuardar);
        panelBotonera.add(buttonCancelar);
       
        panelPrincipal.add(panelBotonera);
        
        panelPrincipal.add(scrollPane);
        
        
        frame.add(panelPrincipal);
        	        	       
        
        //frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setSize(800,600);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);
         	
	}
	public void guardarProductos()
	{
		System.out.println("Guardando productos ");
		String datosProducto="";
		datosProducto  = this.txtCodigo.getText() + ";";
		datosProducto += this.txtDescripcion.getText() + ";";
		datosProducto += this.txtPrecio.getText() + ";";
		datosProducto += this.txtCantidadStock.getText() + ";";
		datosProducto += this.txtPaisProcedencia.getText() + ";";
		System.out.println("Datos " + datosProducto);
		
		fileProductos.agregarArchivo(datosProducto);
		
	}
	public void configurarTabla()
	{
		modelo.addColumn("Codigo");
		modelo.addColumn("Descripcion");
		modelo.addColumn("Precio");
		modelo.addColumn("Cantidad en Stock");
		modelo.addColumn("PaisProcedencia");
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
		String[] columnNames = {"Codigo",
                "Descripcion",
                "Precio",
                "Cantidad en Stock",
                "Pais de Procedencia"};
		
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
