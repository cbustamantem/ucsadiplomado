package py.edu.ucsa.connections;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ManejadorBD {

    private static Connection con = null;
    private static ResultSet rs = null;
    private static Statement stm  = null;
    static{
        try {
            Drivers.cargarDrivers();
            //con = Conexiones.obtenerConexion(Conexiones.DBMS_TYPE_ORACLE);
            con = Conexiones.obtenerConexion(Conexiones.DBMS_TYPE_POSTGRES);
        } catch (ClassNotFoundException e) {          
            System.out.println("No se pudo cargar el driver " + e);
        } catch (SQLException e) {
            System.out.println("No se pudo conectar" + e);            
        }        
    }
    
    //Este metodo puede remplazarse con el de la clase Conexiones
	public static Connection getConnection(int vendor,String host,String port,
													String userName,String password,String databaseName){
		try {
			Drivers.cargarDrivers();			
			if (vendor == Conexiones.DBMS_TYPE_POSTGRES){
				String url = "jdbc:postgresql://"+host+":"+port+"/"+databaseName;			
				//Se carga el driver
				con = DriverManager.getConnection(url, userName, password);	
			}
			System.out.println("Se conecto exitosamente " + con.getAutoCommit());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
			e.printStackTrace();
		}

		return con;
	}
	
    public static Vector obtenerAlumnos(){
        String query = "SELECT cedula,nombre, direccion " +
                " FROM alumnos";
        Vector filas = null;
        
        try {
            
            stm = con.createStatement(rs.TYPE_SCROLL_SENSITIVE,
                                            rs.CONCUR_READ_ONLY);
            //Se ejecuta la consulta y se obtiene el resultado
            rs = stm.executeQuery(query);

            //Se guardan las filas
            filas = new Vector();
            
            while(rs.next()){
                //se guardan las columnas
                Vector columnas = new Vector();
                
                columnas.add(new Integer(rs.getInt("cedula")));
                columnas.add(rs.getString("nombre"));
                columnas.add(rs.getString("direccion"));
                filas.add(columnas);
                
            }
            
            
        
        } catch (SQLException e) {
            System.out.println("No se pudo realizar la consulta" + e);
        }
        
        return filas;
    }
    
    public ResultSet getQueryResult(String query) throws Exception{
    	Statement stmt = null;
    	ResultSet rs;
    	try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {					
			throw new Exception("Error de SQL al realizar la consulta -> " + e.getMessage());
		}		
    	return rs;    	
    }
    
    public static Blob getBlob(int primaryKey) {
        Blob img = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT foto FROM album_fotos " +
                                            "WHERE id = ?");
            ps.setInt(1, primaryKey);
            ResultSet rs =  ps.executeQuery();
            if (rs.next()) {
                img = rs.getBlob("foto");
                rs.close();
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }
    

    public static byte [] getBlob2(int pk) {
        byte[] imgBytes = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT foto FROM album_fotos " +
                    "WHERE id = ?");
            ps.setInt(1, pk);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                   imgBytes = rs.getBytes(1);
                }
                rs.close();
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgBytes;
    }
    
    public static void insertBlob(int pk, File foto) {
        
        try {
            FileInputStream fis = new FileInputStream(foto);
            PreparedStatement ps = con
                    .prepareStatement("INSERT INTO album_fotos (id, foto) VALUES (?, ?)");
            ps.setInt(1, pk);
            
            ps.setBinaryStream(2, fis, (int) foto.length());
            ps.executeUpdate();
            ps.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
       public static void closeConnection(){
           try {               
            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {            
            e.printStackTrace();
        }
    }
        
        public static int moverAnterior(){
            int posActual = 0;
            try {
                rs.previous();
                posActual = rs.getRow();
            } catch (SQLException e) {
              System.out.println("No se pudo mover al registro anterior");
            }    
            return posActual;
        }
        
        
        public static void pruebaTransaccion(){
            
        }
        
        public static int moverSiguiente(){
            int posActual = 0;
            try {
                rs.next();
                posActual = rs.getRow();
            } catch (SQLException e) {
             System.out.println("No se pudo mover al registro siguiente");
            }
            return posActual;
        }
        
      public static void moversePosicionSeleccionada(int posicionSeleccionada){
           if (posicionSeleccionada == 0){
                try {
                    rs.first();
                } catch (SQLException e) {                   
                    e.printStackTrace();
                }
            }    
            else{
                try {
                    rs.absolute(posicionSeleccionada);
                } catch (SQLException e) {                    
                    e.printStackTrace();
                }
            }   
           
        }
      

        public static void transferirSaldo(int idOrig, int idDest, int monto){
          
            try {
                con.setAutoCommit(false); 
                PreparedStatement ps = con.prepareStatement("select * from cuentaCorriente where id_cuenta=?");
                System.out.println("El id origen es " + idOrig);
                ps.setInt(1,idOrig);
                ResultSet rs= ps.executeQuery();
             
               
                if (rs.next()){
                  
                  System.out.println("Le encontro al cliente");
                    int saldoOrigen=rs.getInt("saldo");
                    if (saldoOrigen >= monto){
                        System.out.println("El saldo origen es mayor o igual al monto");
                        PreparedStatement ps2 = con.prepareStatement("update cuentaCorriente set saldo= ? where id_cuenta=?");
                        ps2.setInt(1,(saldoOrigen - monto));
                        ps2.setInt(2,idOrig);
                        ps2.executeUpdate();
                    
                        
                        
                        ps2 = con.prepareStatement("select * from cuentaCorriente where id_cuenta=?");
                        System.out.println("El id origen es " + idDest);
                        ps2.setInt(1,idDest);
                        ResultSet rs2= ps2.executeQuery();
                        rs2.next();
                        
                        System.out.println("hola");
                        int saldoDestino = rs2.getInt("saldo");
                        PreparedStatement ps3 = con.prepareStatement("update cuentaCorriente set saldo= ? where id_cuenta=?");
                        ps3.setInt(1,(saldoDestino + monto));
                        ps3.setInt(2,idDest);
                        ps3.executeUpdate();
                        System.out.println("cahu");
                        con.commit();
                        
                    }
                }
           
            } catch (SQLException e) {
                try {
                    e.printStackTrace();
                    con.rollback();
                    
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }catch(Throwable t){
                System.out.println("Throwable " + t);
            }
        }
        
        
        public static void insertarFecha(){
            SimpleDateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy" );
            Date fecha = null;
            try {
                fecha = new Date(formatter.parse( "10-05-2010" ).getTime( ));
            } catch (ParseException e1) {             
                e1.printStackTrace();
            }
            try {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO probandoDate values (?,?)");
                pstmt.setInt(1,1);
                pstmt.setDate(2,fecha);
                pstmt.executeUpdate();
                System.out.println("Se insertó exitosamente");
            } catch (SQLException e) {             
                e.printStackTrace();
            }
            
        }
        
        public static void prueba (){
            Statement stmt = null;
        /*    try {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet rset = stmt.executeQuery("select * from alumnos");
                if (rset.first()) { 
                 while (!rset.isAfterLast()) { 
                   System.out.println(rset.getString(2)); 
                   rset.relative(1); 
                 } 
                }

            } catch (SQLException e) {              
                e.printStackTrace();
            } */
            try {
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("INSERT INTO  ALUMNOS(CEDULA,NOMBRE) " +
                        "VALUES (2312,'Mariasafasfsdf')");
                
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
              

       
        }
        
        public static void main(String[] args) {
            //ManejadorBD.insertarFecha();
            //ManejadorBD.prueba();
            ManejadorBD m = new ManejadorBD();
        }


        public static void insertarFecha(String idStr, String fechaStr) throws SQLException, ParseException {
            try {
                PreparedStatement pstmt = con.prepareStatement("INSERT INTO FECHAS VALUES (?,?)");
                
                //Parseamos un String a un int
                int id= Integer.parseInt(idStr);
                SimpleDateFormat formateador = new SimpleDateFormat("dd/mm/yyyy");
                Date fecha = new Date(formateador.parse(fechaStr).getTime());
                
                pstmt.setInt(1,id);
                pstmt.setDate(2,fecha);
                pstmt.executeUpdate();
                
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw e;
            } catch (ParseException e) {
                e.printStackTrace();
               throw e;
            }
            
            
        }
        
        public static void pruebaSensitive(){
        	Statement stmt = null;
        	try {
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery("select * from personas");
				
				while(rs.next()){
					System.out.println("Nombre " + rs.getString("nombre"));
					
					if (rs.isLast())
						rs.beforeFirst();
				}
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			}
        	
        	
        }

		public static int getFilaActual() throws SQLException {
			int filaActual = rs.getRow();
			System.out.println("Desde el manejador " + filaActual);
			return filaActual;			
		}
}
