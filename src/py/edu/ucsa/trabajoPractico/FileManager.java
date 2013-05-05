package py.edu.ucsa.trabajoPractico;

import java.io.*;
import java.util.*; 
public class FileManager 
{
	private String filePath="";
	private String fileName="";
	private ArrayList arreglo;
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
	
	public ArrayList leerArchivo() 
	{
		arreglo = new ArrayList(); 
		 try
		 {
			  // Open the file that is the first 
			  // command line parameter
			 FileInputStream fstream = null;
			 try
			 {
				 fstream = new FileInputStream(filePath + fileName);
			 }
			 catch(FileNotFoundException ex)
			 {
				 System.out.println("el archivo no existe");				
				 crearArchivo();
				 fstream = new FileInputStream(filePath + fileName);
			 }
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   
			  {
				  // Print the content on the console
				  System.out.println (strLine);
				  arreglo.add(strLine);
			  }
			  //Close the input stream
			  in.close();
			  return arreglo;
		 }
		 catch (Exception e)
		 {//Catch exception if any
			 	System.err.println("Error: " + e.getMessage());
			 	return null;
		 }
	}
	
	public boolean agregarArchivo(String datos)
	{
		 try
		 {
			 FileWriter fstream = new FileWriter(filePath + fileName,true);
			 BufferedWriter out = new BufferedWriter(fstream);
			 out.append( datos +"\r\n");			 
			 //Close the output stream
			 out.close();
			 return true;
		  }
		 catch (Exception e)
		 {
			 //Catch exception if any
			 System.err.println("Error: " + e.getMessage());
			 return false;
		 }
	}
	public boolean crearArchivo()
	{
		 try
		 {
			 FileWriter fstream = new FileWriter(filePath + fileName,true);
			 BufferedWriter out = new BufferedWriter(fstream);
			 out.write( "");			 
			 //Close the output stream
			 out.close();
			 return true;
		  }
		 catch (Exception e)
		 {
			 //Catch exception if any
			 System.err.println("Error: " + e.getMessage());
			 return false;
		 }
	}

}
