/*
 * Trabajo Practico UCSA
 * @author Carlos Bustamante
 */
package py.edu.ucsa.trabajoPractico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
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
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Principal extends Frame
{

	private JPanel mainPanel;
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JButton buttonAbrir 		= null;
	private JButton buttonColor 		= null;
	private JTextArea txtMensajeOrigen = null;
	private JTextArea txtMensajeDestino= null;
	private JLabel  imagenOrigen 		= null;
	private JLabel  imagenDestino		= null;
	private JSplitPane splitPane;
	private JFileChooser fc;
	private JButton openButton, saveButton;
    private JTextArea log;
    
	private Principal()
	{
		this.createAndShowGUI();	
	}	
	/**
	 * createAndShowGUI
	 * 
	 */
	private void createAndShowGUI()
	{
		 //Create and set up the window.
        JFrame frame = new JFrame("UCSA - Ejercicio 16");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the menu bar.
        JMenuBar menuBar;
        JMenu menu,menu2, submenu,submenu2;
        JMenuItem menuItem, menuItem2,menuItemSalir;
        JMenuItem menuItem3, menuItem4;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
        menuBar = new JMenuBar();
        //Build the first menu.
        menu = new JMenu("Archivo");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("Archivo");	        	       
        menuBar.add(menu);
        
        submenu = new JMenu("Nuevo");        
        submenu.getAccessibleContext().setAccessibleDescription("Nuevo");	        	       
        menu.add(submenu);
        
        menu2 = new JMenu("Ver");
        menu2.setMnemonic(KeyEvent.VK_A);
        menu2.getAccessibleContext().setAccessibleDescription("Ver");
        	        
        menuBar.add(menu2);
        
        //ITEMS DEL MENu
        menuItem = new JMenuItem("Producto",KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Producto");
        submenu.add(menuItem);
        
        //ITEMS DEL MENu
        menuItem2 = new JMenuItem("Persona",KeyEvent.VK_T);       
        menuItem2.getAccessibleContext().setAccessibleDescription("Persona");
        submenu.add(menuItem2);
        
        //ITEM2
        menuItemSalir = new JMenuItem("Salir",KeyEvent.VK_T);
        menuItemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
        menuItemSalir.getAccessibleContext().setAccessibleDescription("Salir");
        menu.add(menuItemSalir);
        
        //ITEMS 3
        menuItem3 = new JMenuItem("Productos",KeyEvent.VK_T);        
        menuItem3.getAccessibleContext().setAccessibleDescription("Productos");
        menu2.add(menuItem3);
        
        //ITEM 4
        menuItem4 = new JMenuItem("Personas",KeyEvent.VK_T);        
        menuItem4.getAccessibleContext().setAccessibleDescription("Personas");
        menu2.add(menuItem4);
        
        ActionListener listenerMenu1 = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JMenuItem sourceItem = (JMenuItem) e.getSource();
				if (sourceItem.getText().trim().equals("Producto") == true)
				{
					new FrmAbmProductos();
				}
				else if (sourceItem.getText().trim().equals("Persona") == true)
				{
					System.out.println("Form Personas");
					new FrmAbmPersonas();
				}
				else if (sourceItem.getText().trim().equals("Personas") == true)
				{
					System.out.println("Form Personas");
					new FrmViewPersonas();
				}
				else if (sourceItem.getText().trim().equals("Productos") == true)
				{
					System.out.println("Form Productos");
					new FrmViewProductos();
				}
				else if (sourceItem.getText().trim().equals("Salir") == true)
				{
					System.out.println("Salir");
					System.exit(0);
				}
				
			}
		};
		
		menuItem.addActionListener(listenerMenu1);
		menuItem2.addActionListener(listenerMenu1);
		menuItem4.addActionListener(listenerMenu1);
		menuItem3.addActionListener(listenerMenu1);
		
		
        
        //JPANEL
        JPanel panel = new JPanel(new BorderLayout());
        	        	        	        	     
        // JLabel
        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(new Color(248, 213, 131));
        yellowLabel.setPreferredSize(new Dimension(400, 280));
        	        	       
        //Setting all components in the frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);
                
        //ScrollPane
        JScrollPane scrollPane = new JScrollPane(new JTextArea(150,10));
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setPreferredSize(new Dimension(150, 50));
        	        
        frame.add(scrollPane, BorderLayout.CENTER);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        Component list = null;
		splitPane.setLeftComponent(list);
        Component textAreaScrollPane = null;
		splitPane.setRightComponent(textAreaScrollPane);
        splitPane.setOneTouchExpandable(true);
        
        frame.add(splitPane, BorderLayout.CENTER);
        
        
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new Principal();
		
	}

}
