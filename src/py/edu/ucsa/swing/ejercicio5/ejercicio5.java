package py.edu.ucsa.swing.ejercicio5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import py.edu.ucsa.swing.ejercicio5.*;
import py.edu.ucsa.swing.ejerciciox.calculadora;

public class ejercicio5 extends JFrame
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
	
	private void inicializar()
	{

		this.panelSuperior= new JPanel();
		//this.panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
		
		this.panelInferior= new JPanel();
		//this.panelInferior.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		
		this.buttonAbrir= new JButton("Abrir");
		this.buttonColor 	= new JButton("Color");
		this.txtMensajeDestino = new JTextArea(5, 20);
		this.txtMensajeOrigen  = new JTextArea(5, 20);
		txtMensajeOrigen.setColumns(40);
		txtMensajeOrigen.setLineWrap(true);
		txtMensajeOrigen.setRows(15);
		txtMensajeOrigen.setWrapStyleWord(true);
		
		
		txtMensajeDestino.setColumns(40);
		txtMensajeDestino.setLineWrap(true);
		txtMensajeDestino.setRows(15);
		txtMensajeDestino.setWrapStyleWord(true);
		Icon usuarioA = new ImageIcon("resources/foto1.png");
		Icon usuarioB = new ImageIcon("resources/foto2.jpg");
				
		JLabel imagenA = new JLabel(usuarioA);
		JLabel imagenB = new JLabel(usuarioB);
		//Create the menu bar.
        JMenuBar menuBar;
        JMenu menu,menu2;
        JMenuItem menuItem, menuItem2;
       
        menuBar = new JMenuBar();
        	        
        //Build the first menu.
        menu = new JMenu("Archivo");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("Menu");	        	       
        menuBar.add(menu);
        
        menu2 = new JMenu("Opciones");
        menu2.setMnemonic(KeyEvent.VK_A);
        menu2.getAccessibleContext().setAccessibleDescription("Menu");
        	        
        menuBar.add(menu2);
        
        //ITEMS DEL MENu
        menuItem = new JMenuItem("Sub Menu - Archivo 1",KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu.add(menuItem);
        
        //ITEM2
        menuItem2 = new JMenuItem("Sub Menu - Opciones 1",KeyEvent.VK_T);
        menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
        menuItem2.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu2.add(menuItem2);
        
        
        //splitPane.setDividerLocation(15);
        
        //mainPanel.add(panelSuperior);
        //mainPanel.add(panelInferior);
        final JFrame frame = this;
        final JTextArea tracker;
        tracker = new JTextArea("File Tracker:");
        tracker.setVisible(true);
        this.add(tracker);
        
        ActionListener listenerColorChooser = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				Color selectedColor = JColorChooser.showDialog(frame , "Pick a Color", Color.GREEN);
		                
                if (selectedColor != null)
                {
                    
					tracker.append("\nThe selected color is make up of Red: " 
                        + selectedColor.getRed() + " Blue: " + selectedColor.getBlue()
                        + " Green: " + selectedColor.getGreen());
					txtMensajeOrigen.setForeground(selectedColor);
					
                }
			}
				
		};
		
		ActionListener listenerChangeProfileImage = new ActionListener()
		{
			  @Override
				public void actionPerformed(ActionEvent e) 
				{
		  			String newline;
					//Handle open button action.
		  			 int returnVal = fc.showOpenDialog(ejercicio5.this);
					 
			            if (returnVal == JFileChooser.APPROVE_OPTION) 
			            {
			                File file = fc.getSelectedFile();
			                
							//This is where a real application would open the file.
			                //log.append("Opening: " + file.getName() + "." + newline);
			            } 
			            else 
			            {
			                
							//log.append("Open command cancelled by user." + newline);
			            }
			}
		  };
		buttonColor.addActionListener(listenerColorChooser);
		buttonAbrir.addActionListener(listenerChangeProfileImage);

    
       panelSuperior.add(this.buttonAbrir); 
       panelSuperior.add(this.txtMensajeDestino);
       panelSuperior.add(imagenA);
       panelInferior.add(this.buttonColor);
       panelInferior.add(this.txtMensajeOrigen);
       panelInferior.add(imagenB);
        
	   
       splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,panelSuperior,panelInferior);
       splitPane.setOneTouchExpandable(true);
  
        
        //put all together
		this.setJMenuBar(menuBar);
		Container cpane = this.getContentPane();
		
		this.setSize(800, 600);
		this.setPreferredSize(new Dimension(800, 600));
		cpane.add(splitPane);
		
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
						
	}
	public static void main(String[] args) 
	{
		ejercicio5  ejercicio = new ejercicio5();
		ejercicio.inicializar();
	}
}
