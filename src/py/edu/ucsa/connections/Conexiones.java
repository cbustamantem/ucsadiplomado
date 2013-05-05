
package py.edu.ucsa.connections;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexiones {
	
	final public static int DBMS_TYPE_POSTGRES 	= 0;
	final public static int DBMS_TYPE_ORACLE 		= 1;
    final public static int DBMS_TYPE_ODBC_ORACLE= 2;
    final public static int DBMS_TYPE_ODBC_EXCEL = 3;
    final public static int DBMS_TYPE_ODBC_TXT 	= 4;
	
	
	public static void main(String [] args)
	{
		try 
		{
			Drivers.cargarDrivers();
            
//			Connection c = obtenerConexion(Conexiones.DBMS_TYPE_ODBC_EXCEL);
			Connection c = obtenerConexion(Conexiones.DBMS_TYPE_ORACLE);
            
			System.out.println("Hacemos algo para verificar la conexion: " + c.getAutoCommit());
			
			//Metadatos
			DatabaseMetaData db = c.getMetaData();
		
			//Nombre del producto
			System.out.println("DB PRODUCT NAME: " + db.getDatabaseProductName());
			
            //tenemos que cerrar si todo funciona bien.
			c.close();
			
		} catch (SQLException e) {
			// Ocurrio un error al conectarse
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// No se encontr� la clase del driver
			e.printStackTrace();
		}
	}
	
    //Devuelve la conexi�n de acuerdo tipo de DBMS
	public static Connection obtenerConexion(int DBMS_TYPE) throws SQLException {
		String url;
		Connection con = null;
		String hostName;
        String sid ;
        String port ;
        String userName ;
        String pass ;
        Properties prop = new Properties();
		switch (DBMS_TYPE) 
		{
		 case DBMS_TYPE_ORACLE:				
			try 
			{
				prop.load(Conexiones.class.getResourceAsStream("Connection.properties"));
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		PropertyResourceBundle prop = (PropertyResourceBundle) PropertyResourceBundle.getBundle("Connection"); 
          
            hostName = prop.getProperty("HostName");
            sid = prop.getProperty("SID");
            port = prop.getProperty("Port");
            userName = prop.getProperty("UserName");
            pass = prop.getProperty("Password");

            url = "jdbc:oracle:thin:@"+hostName+":"+port+":"+sid;			
		    con = DriverManager.getConnection(url, userName,pass);			
			break;
		 case DBMS_TYPE_POSTGRES:			
			try 
			{
				prop.load(Conexiones.class.getResourceAsStream("ConnectionPostgres.properties"));
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//    		PropertyResourceBundle prop = (PropertyResourceBundle) PropertyResourceBundle.getBundle("Connection"); 
          
            hostName 	= prop.getProperty("HostName");
            sid 		= prop.getProperty("SID");
            port 		= prop.getProperty("Port");
            userName 	= prop.getProperty("UserName");
            pass 		= prop.getProperty("Password");

			url = "jdbc:postgresql://"+hostName+":"+ port + "/"+ sid;			
			con = DriverManager.getConnection(url, userName, pass);			
			break;
        case DBMS_TYPE_ODBC_ORACLE:
            url="jdbc:odbc:OracleODBC";
            con = DriverManager.getConnection(url,"xe","123456");
            break;
        case DBMS_TYPE_ODBC_EXCEL:
            url="jdbc:odbc:funcionarios";
            con = DriverManager.getConnection(url);
            break;
        case DBMS_TYPE_ODBC_TXT:
            url="jdbc:odbc:TextFiles";
            con = DriverManager.getConnection(url);
            break;
		}
		return con;
	}
	
}
