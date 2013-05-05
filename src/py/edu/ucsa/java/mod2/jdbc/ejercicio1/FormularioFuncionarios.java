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


public class FormularioFuncionarios 
{
	private JFrame frame ;
	private JPanel mainPanel;
	private JTable tablaDatos ;
	private JPanel panelPrincipal; 
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JButton btnInicio,btnAnterior,btnSiguiente,btnFin;
	private JButton buttonGuardar 		= null;
	private JButton buttonCancelar 		= null;
	private String campoCargo			= "";	
	private JComboBox listaActivo		;
	private Connection conPostgres   = null;
	//private FileManager fileFuncionarios = new FileManager();
	private JSplitPane splitPane;
	private JFileChooser fc;	
	private DefaultTableModel modelo;
    private JTextArea log;
    private JTextField txtLegajo, txtTitular,txtDepartamento,txtTelefono,txtActivo;
    private JFormattedTextField txtFechaIngreso;
    private JLabel lbLegajo, lbFechaIngreso,lbTitular,lbDepartamento,lbTelefono,lbActivo;
	private int registroActualizar=0;
	private Connection conn = null;
	public enum datosAccion{
		UPDATE,DELETE,INSERT
	}
	public enum cursorDatos{
		INICIO,SIGUIENTE,ANTERIOR,FIN
	}
	
	public FormularioFuncionarios()
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
		
		frame = new JFrame("ABM Funcionarios");
      
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
        txtLegajo 			= new JTextField(20);
        
        MaskFormatter mf1;
		try 
		{
			mf1 = new MaskFormatter("##-##-####");
			mf1.setPlaceholderCharacter('_');
	        txtFechaIngreso = new JFormattedTextField(mf1);
		} 
		catch (ParseException e1) 
		{		
			e1.printStackTrace();
		}                
        txtTitular 			= new JTextField(20);
        txtDepartamento		= new JTextField(20);
        txtTelefono			= new JTextField(20);
        txtActivo			= new JTextField(20);
                             
        // Labels
        lbLegajo 		= new JLabel("Legajo",  JLabel.CENTER);
        lbFechaIngreso 	= new JLabel("Fecha Ingreso",  JLabel.CENTER);
        lbTitular 		= new JLabel("Titular",  JLabel.CENTER);
        lbDepartamento 	= new JLabel("Departamento",  JLabel.CENTER);
        lbTelefono		= new JLabel("Telefono",  JLabel.CENTER);
        lbActivo 		= new JLabel("Activo",  JLabel.CENTER);
        
    
        
        //Botones
        btnInicio 	  = new JButton("<<");
        btnSiguiente  = new JButton(">");
        btnAnterior   = new JButton("<");
        btnFin  	  = new JButton(">>");
        buttonGuardar = new JButton("Agregar");
        buttonCancelar= new JButton("Cancelar");
        
        // Cargos
        String[] datosActivo= { "SI", "NO" };
        listaActivo= new JComboBox(datosActivo);
        listaActivo.setSelectedIndex(0);
        
        
        ActionListener listenerNavegacion = new ActionListener() 
        {	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JButton botonListener =  (JButton) e.getSource();
				if (botonListener.getText().trim().equals("<<") == true)
				{
					System.out.println("INICIO");
					navegarRegistros(cursorDatos.INICIO);
					
				}
				else if (botonListener.getText().trim().equals("<") == true)
				{
					System.out.println("ANTERIOR");
					navegarRegistros(cursorDatos.ANTERIOR);
				}
				else if (botonListener.getText().trim().equals(">") == true)
				{
					System.out.println("SIGUIENTE");
					navegarRegistros(cursorDatos.SIGUIENTE);
				}
				else if (botonListener.getText().trim().equals(">>") == true)
				{
					System.out.println("FIN");
					navegarRegistros(cursorDatos.FIN);
				}
			}
        };
        ActionListener listenerBotones = new ActionListener() 
        {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JButton botonListener =  (JButton) e.getSource();
				if (botonListener.getText().trim().equals("Agregar") == true)
				{
					System.out.println("Agregar funcionario");
					try {
						guardarfuncionarios();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					tablaDatos.removeAll();
					cargarTabla();					
				}
				else if(botonListener.getText().trim().equals("Actualizar") == true)
				{
					System.out.println("Actualizar funcionario");
					try
					{						
						actualizarDatos();
						cargarTabla();
					}
					catch (Exception ex)
					{
						System.out.println("Error:" + ex.toString());
					}
					tablaDatos.removeAll();
					cargarTabla();		
				}
				else if(botonListener.getText().trim().equals("Cancelar") == true)
				{
					txtLegajo.setText("");
			        txtFechaIngreso.setText("");
			        txtTitular.setText("");
			        txtDepartamento.setText("");
			        txtTelefono.setText("");
			        txtActivo.setText("");				
					buttonGuardar.setText("Agregar");
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
				campoCargo =  (String) listaListener.getSelectedItem();			
			}
		};
		
		
		buttonGuardar.addActionListener(listenerBotones);
		buttonCancelar.addActionListener(listenerBotones);
		
		btnInicio.addActionListener(listenerNavegacion);
		btnAnterior.addActionListener(listenerNavegacion);
		btnSiguiente.addActionListener(listenerNavegacion);
		btnFin.addActionListener(listenerNavegacion);
		modelo =new DefaultTableModel();
		tablaDatos = new JTable();
		
		
		configurarTabla();
		cargarTabla();
		tablaDatos.setModel(modelo);
		tablaDatos.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tablaDatos);
		
		panelFormulario.add(lbLegajo);
        panelFormulario.add(txtLegajo);
		panelFormulario.add(lbFechaIngreso);
        panelFormulario.add(txtFechaIngreso);
        panelFormulario.add(lbTitular);
        panelFormulario.add(txtTitular);
        panelFormulario.add(lbDepartamento);
        panelFormulario.add(txtDepartamento);
        panelFormulario.add(lbTelefono);
        panelFormulario.add(txtTelefono);
        panelFormulario.add(lbActivo);
        panelFormulario.add(listaActivo);                       
        panelPrincipal.add(panelFormulario);                
        panelBotonera.add(buttonGuardar);
        panelBotonera.add(buttonCancelar);       
        panelPrincipal.add(panelBotonera);  
        panelNavegacion.add(btnInicio);
        panelNavegacion.add(btnAnterior);
        panelNavegacion.add(btnSiguiente);
        panelNavegacion.add(btnFin);
        panelPrincipal.add(panelNavegacion);
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
	private boolean navegarRegistros(cursorDatos accion)
	{
		int fila = 0;
		switch (accion)
		{
		case ANTERIOR:
			fila= this.tablaDatos.getSelectedRow();
			if (fila > 0)
			{
				
				tablaDatos.changeSelection(fila -1, 1, false, false);
			}
			break;
			
		case FIN:
			
			tablaDatos.changeSelection(this.tablaDatos.getRowCount() -1, 1, false, false);
			break;			
		case INICIO:			
			tablaDatos.changeSelection(0, 1, false, false);
			break;
		case SIGUIENTE:
			fila= this.tablaDatos.getSelectedRow();
			if (fila < (this.tablaDatos.getRowCount() - 1))
			{
				tablaDatos.changeSelection(fila + 1, 1, false, false);				
			}
			break;			
		default:
			
			break;
		}
		verDatos();
		return true;
	}
	/**
	 * Guardar funcionarios
	 * Guarda los datos de los funcionarios en el archivo de textp
	 * @throws SQLException 
	 */
	public void guardarfuncionarios() throws SQLException
	{
		System.out.println("Guardando funcionarios ");
		 PreparedStatement pstmt = null;
		 

		    try {
		    
		    	
		        

		      String query = "INSERT INTO funcionarios( legajo, fecha_ingreso, titular, departamento, telefono, activo) " + 
		    		  		" VALUES (?, ?, ?, ?, ?, ?)";

		      pstmt = (PreparedStatement) conPostgres.prepareStatement(query); // create a statement
		      pstmt.setInt(1, Integer.parseInt(this.txtLegajo.getText())); // set input parameter 1
		      String [] datosFecha = this.txtFechaIngreso.getText().split("-");
		      System.out.println("Fechas " + this.txtFechaIngreso.getText());
		      String startDate= datosFecha[2] + "-" + datosFecha[1] + "-" + datosFecha[0];
		      //java.sql.Date theDate = new java.sql.Date(Integer.parseInt(startDate.substring(6, 10))- 1900, Integer.parseInt(startDate.substring(3, 5))-1,Integer.parseInt(startDate.substring(0, 2)));
		      java.sql.Date  theDate = java.sql.Date.valueOf(startDate);
		      pstmt.setDate(2,theDate); // set input parameter 2
		      pstmt.setString(3, this.txtTitular.getText()); // set input parameter 3
		      pstmt.setString(4, this.txtDepartamento.getText()); // set input parameter 3
		      pstmt.setString(5, this.txtTelefono.getText()); // set input parameter 3
		      pstmt.setString(6, this.listaActivo.getSelectedItem().toString()); // set input parameter 3		      
		      pstmt.executeUpdate(); // execute insert statement
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		      //conn.close();
		    }		
	}
	public void actualizarDatos() throws SQLException
	{
		System.out.println("Actualizando funcionarios ");
		 PreparedStatement pstmt = null;
		 

		    try {
		    
		      String query = "UPDATE funcionarios SET legajo=?, fecha_ingreso=?, titular=?, departamento=?, telefono=?," + 
		    		  		"activo=? WHERE legajo=?";

		      pstmt = (PreparedStatement) conPostgres.prepareStatement(query); // create a statement
		      pstmt.setInt(1, Integer.parseInt(this.txtLegajo.getText())); // set input parameter 1
		      String [] datosFecha = this.txtFechaIngreso.getText().split("-");
		      System.out.println("Fechas " + this.txtFechaIngreso.getText());
		      String startDate= datosFecha[2] + "-" + datosFecha[1] + "-" + datosFecha[0];
		      //java.sql.Date theDate = new java.sql.Date(Integer.parseInt(startDate.substring(6, 10))- 1900, Integer.parseInt(startDate.substring(3, 5))-1,Integer.parseInt(startDate.substring(0, 2)));
		      java.sql.Date  theDate = java.sql.Date.valueOf(startDate);
		      pstmt.setDate(2,theDate); // set input parameter 2
		      pstmt.setString(3, this.txtTitular.getText()); // set input parameter 3
		      pstmt.setString(4, this.txtDepartamento.getText()); // set input parameter 3
		      pstmt.setString(5, this.txtTelefono.getText()); // set input parameter 3
		      pstmt.setString(6, this.listaActivo.getSelectedItem().toString()); // set input parameter 3
		      pstmt.setInt(7, Integer.parseInt(this.txtLegajo.getText())); // set input parameter 1
		      pstmt.executeUpdate(); // execute insert statement
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      pstmt.close();
		      //conn.close();
		    }				
	}
	public void verDatos()
	{
		int fila = this.tablaDatos.getSelectedRow();
		registroActualizar=fila;
		this.txtLegajo.setText(this.modelo.getValueAt(fila,0).toString());
		String fecha =this.modelo.getValueAt(fila,1).toString();
		//java.text.SimpleDateFormat dia = new java.text.SimpleDateFormat("yyyy-MM-dd");		
		//String fechaFormateada=dia.format(fecha).toString();
		String [] datosFecha = fecha.split("-");
		String fechaFormateada= datosFecha[2] + "-" + datosFecha[1] + "-" + datosFecha[0];
		this.txtFechaIngreso.setText(fechaFormateada);
		this.txtTitular.setText(this.modelo.getValueAt(fila,2).toString());
		this.txtDepartamento.setText(this.modelo.getValueAt(fila,3).toString());
		this.txtTelefono.setText(this.modelo.getValueAt(fila,4).toString());
		this.listaActivo.setSelectedItem(this.modelo.getValueAt(fila,5).toString());
		this.buttonGuardar.setText("Actualizar");
	}
	public void cargarTabla()
	{
		//TABLA
		limpiarTabla();

				 
	     try 
	     {
	    	 Drivers.cargarDrivers();	       
	         Connection conPostgres = Conexiones.obtenerConexion(Conexiones.DBMS_TYPE_POSTGRES);
	         
	         	Statement stmt = conPostgres.createStatement();
	            ResultSet rs = stmt.executeQuery("select * from funcionarios");	           
	            String [] datos = new String[6];
	            modelo.setRowCount(0);
	            while(rs.next())
	            {	            	
	            	for (int i=0; i< 6;i++)
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
		modelo.addColumn("Legajo");
		modelo.addColumn("Fecha Ingreso");
		modelo.addColumn("Titular");
		modelo.addColumn("Departamento");
		modelo.addColumn("Telefono");
		modelo.addColumn("Automatico");
			
		//modelo.addColumn("PaisProcedencia");
	}


}
