package py.edu.ucsa.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 
 * Esta clase obtiene de antemano conexiones, las recicla y maneja.
 */
public class PoolConexiones implements Runnable {

	//parámetros de conexión
	private String driver, url, userName, password;

	private int maxConexiones; // La cantidad máxima permitida de conexiones
								// que se pueden tener abiertas a la vez
	
	private boolean esperarSiNoHayConexiones;//Opcion de esperar si no hay
											 // conexiones disponibles y se
											 // alcanzo el max

	private Vector<Connection> conexionesDisponibles, conexionesOcupadas;

	private boolean conexionPendiente = false; // si hay alguna conexion que se
												// esta creando en background

	// Constructor
	public PoolConexiones(String driver, String url, String userName,
			String password, int cantConIniciales, int maxConexiones,
			boolean esperarSiNoHayConexiones) throws SQLException {

		this.driver = driver;
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.esperarSiNoHayConexiones = esperarSiNoHayConexiones;
		this.maxConexiones = maxConexiones;

		// Si la cantidad de conexiones iniciales supera el maximo, se le asigna
		// el maximo.
		if (cantConIniciales > maxConexiones)
			cantConIniciales = maxConexiones;

		// Se crean los vectores que van a mantener las conexiones ocupadas y
		// disponibles en todo momento
		conexionesDisponibles = new Vector<Connection>(cantConIniciales);
		conexionesOcupadas = new Vector<Connection>();

		for (int i = 0; i < cantConIniciales; i++) {
			conexionesDisponibles.addElement(obtenerNuevaConexion());
		}
	}

	
	public synchronized Connection getConnection()
				throws SQLException {
		if(!conexionesDisponibles.isEmpty()){ // si hay conexiones disponibles
			//se obtiene el útlimo elemento
			Connection conAUtilizar = (Connection) conexionesDisponibles.lastElement();
			//se elimina de ese vector la conexion que se va a utilizar
			conexionesDisponibles.removeElementAt(conexionesDisponibles.size()-1);
			/*
			 * Si una conexion disponible  esta cerrada (ej. x time out)
			 * entonces se remueve de la lista y se repite el proceso de obtener una conexión
			 * También se les depierta a los otros threads q estan esperando por una conexión.
			 */
			if (conAUtilizar.isClosed()) {
				notifyAll(); 
				return(getConnection()); //Vuelve a pedir la conexion y a esperar si es que otro thread entro primero
			} else { //si no estaba cerrada
				conexionesOcupadas.addElement(conAUtilizar); //se coloca la conexion q se va a utilizar en el vector
															//de las conexiones ocupadas
				return conAUtilizar;
			}
		}else{ //si no hay conexiones disponibles
			/**
			 * Pueden pasar 3 casos:
			 * 	1) No se alcanzó todavía el limite de conexiones máximas. Por lo tanto se obtiene una en background 
			 * 	(si es que no hay alguna pendiente) y luego se espera la siguiente conexion disponible (q puede o no ser la nueva creada)
			 *  
			 *  2) Ya se llego a la cantidad máxima de conexiones y la bandera de esperarSiNoHayConexiones es falsa. Entonces
			 *  lanzar una excepcion en ese caso
			 *  
			 *  3) Ya se llego a la cantidad máxima de conexiones y la bandera de  esperarSiNoHayConexiones es verdadera. Entonces
			 *  espera hasta que haya notificacion "notifyAll"y ahi intenta obtener de vuelta una conexión.
			 */
			if ((totalConnections() < maxConexiones) &&
					!conexionPendiente) {
				crearConexionBackground();
			}else if (!esperarSiNoHayConexiones) {
				throw new SQLException("Se alcanzó el límite de conexiones");
			}
			//Se espera que una conexión sea creada o si una conexión existente es liberada
			try {
				wait();
			} catch(InterruptedException ie) {}
				//Alguien liberó la conexión por lo tanto se trata de nuevo.
				return getConnection();
		}
		
	}
	
	/**
	 * Uno no puede realizar una nueva conexión en primer plano cuando no hay ninguna disponible ya que tomaria mucho segundo en una
	 * conexión de red lenta. En vez de eso, se empieza un thread que establece una nueva conexión y espera, y se levanta cuando la
	 * nueva conexión se creo o si alguien dejó de usar una y la liberó.
	 */
	private void crearConexionBackground() {
		conexionPendiente = true;
		Thread connectThread = new Thread(this);
		connectThread.start();
	}


	public void run() {
		try {
			Connection connection = obtenerNuevaConexion();
			synchronized(this) {
				conexionesDisponibles.addElement(connection);
				conexionPendiente = false;
				notifyAll();
			}
		} catch(Exception e) { // SQLException or OutOfMemory
		}	
	}
	
	
	/**
	 * Este método crea explicitamente una conexión. Se le llama en primer plano
	 * cuando se inicializa el Pool, y se crea en segundo plano cuando ya se
	 * esta usando.
	 * 
	 * @return
	 * @throws SQLException
	 */
	private Connection obtenerNuevaConexion() throws SQLException {
		Connection connection = null;
		try {
			// carga el driver
			Class.forName(driver);
			// Establece la conexion con la BD
			connection = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public synchronized int totalConnections() {
		return(conexionesDisponibles.size() + conexionesOcupadas.size());
	}

	
	//Despues de utilizar se llama a este método para liberar la conexión y que otro cliente pueda utilizar
	public synchronized void liberarConexion(Connection connection) {
		conexionesOcupadas.removeElement(connection);
		conexionesDisponibles.addElement(connection);

		//Se les notifica a los threads que estan esperando una conexión
		notifyAll();
   }
	
	
	/** 
	* Cierra todas las conexiones. 
	* Se debe estar seguro que ninguna conexión esta en uso
	*/
	public synchronized void closeAllConnections() {
		closeConnections(conexionesDisponibles);
		conexionesDisponibles = new Vector<Connection>();
		closeConnections(conexionesOcupadas);
		conexionesOcupadas = new Vector<Connection>();
	}
	
	private void closeConnections(Vector<Connection> connections) {
		try {
			for(int i=0; i<connections.size(); i++) {
				Connection connection =
					(Connection)connections.elementAt(i);
				if (!connection.isClosed()) {
					connection.close();
				}
			}
		} catch(SQLException sqle) {		
		}
	}

	public synchronized String toString() {
		String info =
			"ConnectionPool(" + url + "," + userName + ")" +
			", available=" + conexionesDisponibles.size() +
			", busy=" + conexionesOcupadas.size() +
			", max=" + maxConexiones;
		return(info);
	}
	
	public static void main(String[] args) throws SQLException {
		
		PoolConexiones p = new PoolConexiones("oracle.jdbc.driver.OracleDriver",
							"jdbc:oracle:thin:@localhost:1521:xe",
							"diplo",
							"xantus",
							2,
							2,true);
		Connection c = p.getConnection();
		System.out.println("Se obtuvo la conexion  " + c.getAutoCommit());
		p.liberarConexion(c);
		
	}
}
