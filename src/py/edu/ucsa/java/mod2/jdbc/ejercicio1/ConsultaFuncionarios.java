package py.edu.ucsa.java.mod2.jdbc.ejercicio1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import py.edu.ucsa.connections.*;
public class ConsultaFuncionarios 
{
	public void consultarFuncionarios()
	{
		 Connection con  = null;
		 
	     try 
	     {
	    	 Drivers.cargarDrivers();
	         //Connection conOracle = Conexiones.obtenerConexion(Conexiones.DBMS_TYPE_ORACLE);
	         Connection conPostgres = Conexiones.obtenerConexion(Conexiones.DBMS_TYPE_POSTGRES);
	         
	         	Statement stmt = conPostgres.createStatement();
	            ResultSet rs = stmt.executeQuery("select * from funcionarios");
	            System.out.println("");
	            System.out.print("Legajo ");
                System.out.print("\t Fecha Ingreso" );
                System.out.print("\t Titular ");
                System.out.print("\t\t Departamento " );
                System.out.print("\t Telefono " );
                System.out.print("\t Activo ");
                System.out.println("");
	            while(rs.next())
	            {
	            	System.out.println("");
	                System.out.print( rs.getInt(1));
	                System.out.print("\t" + rs.getString(2));
	                System.out.print("\t" + rs.getString(3));
	                System.out.print("\t\t" + rs.getString(4));
	                System.out.print("\t" + rs.getString(5));
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
}
