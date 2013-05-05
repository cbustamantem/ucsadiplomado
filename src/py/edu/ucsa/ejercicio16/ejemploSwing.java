package py.edu.ucsa.ejercicio16;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*; 
public class ejemploSwing {
	 private static void createAndShowGUI() 
	 {
	        //Create and set up the window.
	        JFrame frame = new JFrame("UCSA - Ejercicio 16");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        	        	      	        
	        // MENU
	        
	        //Create the menu bar.
	        JMenuBar menuBar;
	        JMenu menu,menu2, submenu,submenu2;
	        JMenuItem menuItem, menuItem2;
	        JRadioButtonMenuItem rbMenuItem;
	        JCheckBoxMenuItem cbMenuItem;
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
	        
	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	        
	        //ScrollPane
	        JScrollPane scrollPane = new JScrollPane(new JTextArea(150,10));
	        
	        
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

	    public static void main(String[] args) 
	    {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() 
	        {
	            public void run() 
	            {
	                createAndShowGUI();
	            }
	        });
	    }

}
