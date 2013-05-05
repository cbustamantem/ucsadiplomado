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
import javax.swing.JComboBox;
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

/**
 * Formulario ABM Personas
 * @author cbustamante
 *
 */
public class FrmAbmPersonas 
{
	private JFrame frame ;
	private JPanel mainPanel;
	private JTable tablaDatos ;
	private JPanel panelPrincipal; 
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JButton buttonGuardar 		= null;
	private JButton buttonCancelar 		= null;
	private String campoSexo			= "";
	private JComboBox listaSexo 		;
	private FileManager fileProductos = new FileManager();
	private JSplitPane splitPane;
	private JFileChooser fc;	
	private DefaultTableModel modelo;
    private JTextArea log;
    private JTextField txtNombres, txtApellidos,txtTipoDocumento,txtNumeroDocumento, txtSexo;
    private JLabel lbNombre, lbApellidos, lbTipoDocumento, lbNumeroDocumento, lbSexo;
 
    
    public FrmAbmPersonas()
    {
    	this.createAndShowGUI();
    }
    /**
     * createAndShowGUI
     * Crea y asigna 
     */
	private void createAndShowGUI()
	{
		 //Create and set up the window.
		
		frame = new JFrame("ABM Personas");
      
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        fileProductos.setFileName("personas.txt");
        
        panelPrincipal = new JPanel(new FlowLayout());
        //JPANEL
        JPanel panelFormulario = new JPanel(new GridLayout(5,2) );
        JPanel panelBotonera= new JPanel(new GridLayout(1,2) );
        
        	        	        	        	     
        // TextBox        
        txtNombres 			= new JTextField(20);
        txtApellidos 		= new JTextField(20);
        txtTipoDocumento 			= new JTextField(20);
        txtNumeroDocumento 	= new JTextField(20);
        txtSexo 			= new JTextField(20);
              
        // Labels
        lbNombre 			= new JLabel("Nombre",  JLabel.CENTER);
        lbApellidos 		= new JLabel("Apellidos",  JLabel.CENTER);
        lbTipoDocumento 	= new JLabel("Tipo Documento",  JLabel.CENTER);
        lbNumeroDocumento 	= new JLabel("Numero Documento",  JLabel.CENTER);
        lbSexo 	= new JLabel("Sexo",  JLabel.CENTER);
    
        
        //Botones
        buttonGuardar = new JButton("Guardar");
        buttonCancelar = new JButton("Cancelar");
        
        //combo sexo
        String[] sexo= { "Masculino", "Femenino" };
        listaSexo= new JComboBox(sexo);
        listaSexo.setSelectedIndex(0);
        
        
        ActionListener listenerBotones = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				JButton botonListener =  (JButton) e.getSource();
				if (botonListener.getText().trim().equals("Guardar") == true)
				{
					guardarProductos();
					tablaDatos.removeAll();
					cargarTabla();
				}
				else if(botonListener.getText().trim().equals("Cancelar") == true)
				{
		
					txtNombres.setText("");
					txtApellidos.setText("");
					txtTipoDocumento.setText("");
					txtNumeroDocumento.setText("");
					txtSexo.setText("");
			        tablaDatos.updateUI();
			        cargarTabla();
				}
				
			}
		};
		
		ActionListener listenerLista = new ActionListener() 
		{
				
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JComboBox listaListener =  (JComboBox) e.getSource();
				campoSexo =  (String) listaListener.getSelectedItem();			
			}
		};
		listaSexo.addActionListener(listenerLista);
		
		buttonGuardar.addActionListener(listenerBotones);
		buttonCancelar.addActionListener(listenerBotones);
		modelo =new DefaultTableModel();
		tablaDatos = new JTable();
		
		configurarTabla();
		cargarTabla();
		tablaDatos.setModel(modelo);
		tablaDatos.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tablaDatos);
		
        panelFormulario.add(lbNombre);
        panelFormulario.add(txtNombres);
        panelFormulario.add(lbApellidos);
        panelFormulario.add(txtApellidos);
        panelFormulario.add(lbTipoDocumento);
        panelFormulario.add(txtTipoDocumento);
        panelFormulario.add(lbNumeroDocumento);
        panelFormulario.add(txtNumeroDocumento);
        panelFormulario.add(lbSexo);
        panelFormulario.add(listaSexo);      
        panelPrincipal.add(panelFormulario);        
        
        panelBotonera.add(buttonGuardar);
        panelBotonera.add(buttonCancelar);
       
        panelPrincipal.add(panelBotonera);
        
        panelPrincipal.add(scrollPane);
        
        
        frame.add(panelPrincipal);
        	        	       
        
        //frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setSize(800,500);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);
         	
	}
	/**
	 * Guardar Productos
	 * Guarda los datos de los productos en el archivo de textp
	 */
	public void guardarProductos()
	{
		System.out.println("Guardando productos ");
		String datosProducto="";
		
		
		datosProducto  = this.txtNombres.getText() + ";";
		datosProducto += this.txtApellidos.getText() + ";";
		datosProducto += this.txtTipoDocumento.getText() + ";";
		datosProducto += this.txtNumeroDocumento.getText() + ";";
		datosProducto += this.listaSexo.getSelectedItem().toString() + ";";
		System.out.println("Datos " + datosProducto);
		
		fileProductos.agregarArchivo(datosProducto);
		
	}
	/**
	 * Configurar Tabla
	 * Asigna los datos de la grilla
	 */
	public void configurarTabla()
	{
		modelo.addColumn("Nombres");
		modelo.addColumn("Apellidos");
		modelo.addColumn("Tipo de Documento");
		modelo.addColumn("Numero de Documento");
		modelo.addColumn("Sexo");
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
