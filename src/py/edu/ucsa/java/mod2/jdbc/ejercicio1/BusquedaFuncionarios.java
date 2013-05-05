package py.edu.ucsa.java.mod2.jdbc.ejercicio1;



import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;

import java.sql.PreparedStatement;

import py.edu.ucsa.connections.*;


public class BusquedaFuncionarios 
{
	private JFrame frame ;
	private JPanel mainPanel;
	private JTable tablaDatos ;
	private JPanel panelPrincipal; 
	private JPanel panelSuperior;
	private JPanel panelInferior;
	
	private JButton buttonGuardar 		= null;
	private JButton buttonCancelar 		= null;
    private Integer registroActualizar=0;
	private Connection conPostgres   = null;
	//private FileManager fileFuncionarios = new FileManager();
	private DefaultTableModel modelo;
    private JTextArea log;
    private JTextField txtNombre, txtApellido;
    private JFormattedTextField txtFechaInicio,txtFechaFin;
    private JLabel lbNombre, lbFechaInicio,lbApellido,lbFechaFin;
    private String filtro="";
	private Connection conn = null;
	
	
	public BusquedaFuncionarios()
	{
		try {
			createAndShowGUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
     * createAndShowGUI
     * Crea y asigna 
	 * @throws SQLException 
     */
	private void createAndShowGUI() throws SQLException
	{
		 //Create and set up the window.	
		frame = new JFrame("Busqueda Usuarios");
      
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        try {
			Drivers.cargarDrivers();
			conPostgres = Conexiones.obtenerConexion(Conexiones.DBMS_TYPE_POSTGRES);
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}
        
        
        panelPrincipal = new JPanel(new FlowLayout());
        //JPANEL
        JPanel panelFormulario = new JPanel(new GridLayout(6,2) );
        JPanel panelBotonera= new JPanel(new GridLayout(1,2) );
        JPanel panelNavegacion = new JPanel(new GridLayout(1,4) );
        
        	        	        	        	     
        // TextBox        
        txtNombre 			= new JTextField(20);
        txtApellido 			= new JTextField(20);
        MaskFormatter mf1;
		try 
		{
			mf1 = new MaskFormatter("##-##-####");
			mf1.setPlaceholderCharacter('_');
	        txtFechaInicio  = new JFormattedTextField(mf1);
	        txtFechaFin 	= new JFormattedTextField(mf1);
		} 
		catch (ParseException e1) 
		{		
			e1.printStackTrace();
		}                                             
        // Labels
        lbNombre 		= new JLabel("Nombre",  JLabel.CENTER);
        lbFechaInicio 	= new JLabel("Fecha Inicial",  JLabel.CENTER);
        lbApellido 		= new JLabel("Aoellido",  JLabel.CENTER);
        lbFechaFin 	= new JLabel("Fecha Final",  JLabel.CENTER);        
        buttonGuardar = new JButton("Buscar");
        buttonCancelar = new JButton("Cancelar");            
        //Botones
        
        ActionListener listenerBotones = new ActionListener() 
        {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JButton botonListener =  (JButton) e.getSource();
				if (botonListener.getText().trim().equals("Buscar") == true)
				{
					asignarFiltro();
					cargarTabla();					
				}				
				else if(botonListener.getText().trim().equals("Cancelar") == true)
				{
					txtNombre.setText("");
			        txtFechaInicio.setText("");
			        txtApellido.setText("");
			    	buttonGuardar.setText("Buscar");
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
		panelFormulario.add(lbNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lbApellido);
        panelFormulario.add(txtApellido);
		panelFormulario.add(lbFechaInicio);
        panelFormulario.add(txtFechaInicio);
		panelFormulario.add(lbFechaFin);
        panelFormulario.add(txtFechaFin);                             
        panelPrincipal.add(panelFormulario);                
        panelBotonera.add(buttonGuardar);
        panelBotonera.add(buttonCancelar);       
        panelPrincipal.add(panelBotonera);  
        panelPrincipal.add(scrollPane);                
        frame.add(panelPrincipal);        	        	              
        //frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);        
        //Display the window.
        frame.pack();
        frame.setSize(500,500);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        tablaDatos.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				System.out.println("Tabla Click");
				verDatos();
			}
		});
         	
	}	
	public String limpiarFecha (String fecha)
	{
		fecha=fecha.replace("__-__-____", "");
		if (! fecha.isEmpty())
		{
			String [] datosFecha = fecha.split("-");		
			String startDate= datosFecha[2] + "-" + datosFecha[1] + "-" + datosFecha[0];
			fecha = startDate;
		}
		return fecha ;
	}
	public void asignarFiltro()
	{
	
		filtro="";
		boolean asignado=false;
		String nombre=this.txtNombre.getText().trim();
		String apellido=this.txtApellido.getText().trim();
		String fechaInicio = limpiarFecha(this.txtFechaInicio.getText().trim());
		String fechaFin = limpiarFecha(this. txtFechaFin.getText().trim());
		
		fechaFin=fechaFin.replace("__-__-____", "");
		if (! nombre.isEmpty())
		{
			filtro=" nombre like '%"+ this.txtNombre.getText().trim() + "%' ";
			asignado=true;
		}
		if (! apellido.isEmpty())
		{
			if (asignado)
			{
				filtro = filtro + " AND ";
			}
			filtro=filtro + " apellido like '%"+ this.txtApellido.getText().trim() + "%' ";			
		}
		//Si la fecha inicio esta en blanco y la fecha fin no
		if ( (fechaInicio.isEmpty()) && (! fechaFin.isEmpty()) )
		{
			if (asignado)
			{
				filtro = filtro + " AND ";
			}
			filtro=filtro + " fechanacimiento <=  '" + fechaFin  + "'";
		}
		//Si la fecha inicio no esta en blanco y la fecha fin si
		if ( (! fechaInicio.isEmpty()) && ( fechaFin.isEmpty()) )
		{
			if (asignado)
			{
				filtro = filtro + " AND ";
			}
			filtro=filtro +" fechanacimiento >= '" + fechaInicio + "'";
		}
		//Si la fecha inicio no esta en blanco y la fecha fin si
		if ( (! fechaInicio.isEmpty()) && (! fechaFin.isEmpty()) )
		{
			if (asignado)
			{
				filtro = filtro + " AND ";
			}
			filtro= filtro +" fechanacimiento BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "' ";
		}
		System.out.println("Filtro Asignado: " + filtro );
	}
	public void verDatos()
	{
		
	}
	public void cargarTabla()
	{
		//TABLA
		limpiarTabla();
		String condicion ="";
				 
	     try 
	     {
	    	 Drivers.cargarDrivers();	       
	         Connection conPostgres = Conexiones.obtenerConexion(Conexiones.DBMS_TYPE_POSTGRES);
	         	
	         	Statement stmt = conPostgres.createStatement();
	         	if (! filtro.equals(""))
	         	{
	         		condicion = " WHERE " + filtro;
	         	}
	            ResultSet rs = stmt.executeQuery("select * from usuarios " + condicion);
	            System.out.println("QUERY:" + "select * from usuarios " + condicion);
	           
	            String [] datos = new String[6];
	            modelo.setRowCount(0);
	            while(rs.next())
	            {	            	
	            	for (int i=0; i< 4;i++)
	            	{
	            		datos[i]= rs.getString(i + 1 );
	            	}
	            	modelo.addRow(datos);
	            }
	            
	     } 
	     catch (ClassNotFoundException e) 
	     {         
	         System.out.println("No se encontro el driver");
	         e.printStackTrace();         
	     } 
	     catch (SQLException e) 
	     {
	         System.out.println("No se pudo conectar" +  e.getMessage());
	         e.printStackTrace();
	     }
	}
	public void limpiarTabla()
	{
		while (modelo.getRowCount() >0)
		{
			modelo.removeRow(0);
		}			
	}
	
	public void configurarTabla()
	{
		modelo.addColumn("Id");
		modelo.addColumn("Nombre");
		modelo.addColumn("Apellido");
		modelo.addColumn("Fecha Nac.");
					
		//modelo.addColumn("PaisProcedencia");
	}


}
