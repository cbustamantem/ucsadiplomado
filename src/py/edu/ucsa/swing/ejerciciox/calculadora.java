package py.edu.ucsa.swing.ejerciciox;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class calculadora extends JFrame 
{
	
	private static final long serialVersionUID = -2906856966154801163L;
	
	private JTextField dataField 	= null;
	private JPanel  buttonsPanel 	= null;
	private JPanel  mainPanel 		= null;
	private JButton buttonOne 		= null;
	private JButton buttonTwo 		= null;
	private JButton buttonThree 	= null;
	private JButton buttonFour 		= null;
	private JButton buttonFive 		= null;
	private JButton buttonSix 		= null;
	private JButton buttonSeven 	= null;
	private JButton buttonEight 	= null;
	private JButton buttonNine 		= null;
	private JButton buttonZero 		= null;
	private JButton buttonPlus 		= null;
	private JButton buttonMin 		= null;
	private JButton buttonMult 		= null;
	private JButton buttonDiv 		= null;
	private JButton buttonEq 		= null;
	private JButton buttonClear 	= null;
	
	public calculadora()
	{
		this.inicializar();
	}

	private void inicializar() 
	{
		this.dataField 	= new JTextField("0");
		this.dataField.setHorizontalAlignment(JTextField.RIGHT);
		this.buttonZero = new JButton("0");
		this.buttonOne 	= new JButton("1");
		this.buttonTwo	= new JButton("2");
		this.buttonThree = new JButton("3");
		this.buttonFour = new JButton("4");
		this.buttonFive  = new JButton("5");
		this.buttonSix 	  = new JButton("6");
		this.buttonSeven = new JButton("7");
		this.buttonEight = new JButton("8");
		this.buttonNine  = new JButton("9");
		this.buttonClear = new JButton("C");
		this.buttonDiv = new JButton("/");
		this.buttonEq = new JButton("=");
		this.buttonMin = new JButton("-");
		this.buttonMult = new JButton("*");
		this.buttonPlus = new JButton("+");
		//Listeners
		ActionListener listenersButtons = new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO Auto-generated method stub
				JButton source = (JButton) e.getSource();
				//dataField.setText(// dataField.getText())
				dataField.setText(dataField.getText() + " " + source.getText());
				if (source.getText().trim().equals("="))
				{
					realizarCalculo();
				}
			}
				
		};
		buttonOne.addActionListener(listenersButtons);
		buttonTwo.addActionListener(listenersButtons);
		buttonThree.addActionListener(listenersButtons);
		buttonFour.addActionListener(listenersButtons);
		buttonFive.addActionListener(listenersButtons);
		buttonSix.addActionListener(listenersButtons);
		buttonSeven.addActionListener(listenersButtons);
		buttonEight.addActionListener(listenersButtons);
		buttonNine.addActionListener(listenersButtons);		
		buttonClear.addActionListener(listenersButtons);
		buttonDiv.addActionListener(listenersButtons);
		buttonEq.addActionListener(listenersButtons);
		buttonMin.addActionListener(listenersButtons);
		buttonMult.addActionListener(listenersButtons);
		buttonPlus.addActionListener(listenersButtons);
		
		this.buttonsPanel = new JPanel();
		this.buttonsPanel.setLayout(new GridLayout(4, 5));
		this.buttonsPanel.add(buttonOne);
		this.buttonsPanel.add(buttonTwo);
		this.buttonsPanel.add(buttonThree);
		this.buttonsPanel.add(buttonPlus);
		this.buttonsPanel.add(buttonMin);
		this.buttonsPanel.add(buttonFour);
		this.buttonsPanel.add(buttonFive);
		this.buttonsPanel.add(buttonSix);
		this.buttonsPanel.add(buttonMult);
		this.buttonsPanel.add(buttonDiv);
		this.buttonsPanel.add(buttonSeven);
		this.buttonsPanel.add(buttonEight);
		this.buttonsPanel.add(buttonNine);
		this.buttonsPanel.add(buttonEq);
		this.buttonsPanel.add(buttonClear);
		this.buttonsPanel.add(buttonZero);
		this.buttonsPanel.add(Box.createHorizontalGlue());
			
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.mainPanel.add(dataField);
		this.mainPanel.add(buttonsPanel);
		this.getContentPane().add(mainPanel);
		this.setSize(300, 300);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		new calculadora();
	}
	
	public void realizarCalculo()
	{
		String operacion= this.dataField.getText();
		String operaciones[] = operacion.split(" ");
		int contador = 0;
		int resultado = 0;
		
		int operando=0;
		String cadenaOperando="";
		String operador="";
		String numero="";
		
		while (contador < operaciones.length)
		{
			System.out.println("Operacion:" + operaciones[contador]);
			if (operaciones[contador] != "+" || operaciones[contador] != "*" || operaciones[contador] != "/" || operaciones[contador] !="-")
			{
				System.out.println(" Operando");
				operador=operaciones[contador];
				if (operador.equals("+"))
				{
					System.out.println(" Numerador  +" + cadenaOperando + "  " + resultado );
					resultado = Integer.parseInt(cadenaOperando) + resultado;
				}
					
			}
			else
			{
				
				numero			= operaciones[contador];
				cadenaOperando  = cadenaOperando + numero;
			}
			contador++;
		}
	}
	
}