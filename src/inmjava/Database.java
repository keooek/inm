package inmjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class Database {

	public static Connection conn; // our connnection to the db - presist for
									// life of program

	public static void shutdown() throws SQLException {

		Statement st = conn.createStatement();

		// db writes out to files and performs clean shuts down
		// otherwise there will be an unclean shutdown
		// when program ends
		st.execute("SHUTDOWN");
		conn.close(); // if there are no other open connection
	}

	// use for SQL command SELECT
	public synchronized static void query(String expression) throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		st = conn.createStatement(); // statement objects can be reused with

		// repeated calls to execute but we
		// choose to make a new one each time
		rs = st.executeQuery(expression); // run the query

		// do something with the result set.
		dump(rs);
		st.close(); // NOTE!! if you close a statement the associated ResultSet
					// is

		// closed too
		// so you should copy the contents to some other object.
		// the result set is invalidated also if you recycle an Statement
		// and try to execute some other query before the result set has been
		// completely examined.
	}

	public synchronized static String exists_reg(String expression) throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		st = conn.createStatement();
		rs = st.executeQuery("select count(1) from Inmueble where id = '" + expression + "'");
		ResultSetMetaData meta = rs.getMetaData();
		int colmax = meta.getColumnCount();
		int i;
		Object o = null;
		String return_value = null;

		for (; rs.next();) {
			for (i = 0; i < colmax; ++i) {
				o = rs.getObject(i + 1);
				System.out.print(o.toString() + " ");
				return_value = o.toString().replace(" ", "");
			}
			System.out.println(" ");
		}
		st.close();
		return return_value;
	}

	// use for SQL commands CREATE, DROP, INSERT and UPDATE
	public synchronized static void update(String expression) throws SQLException {

		Statement st = null;

		st = conn.createStatement(); // statements

		int i = st.executeUpdate(expression); // run the query

		if (i == -1) {
			System.out.println("db error : " + expression);
		}

		st.close();
	} // void update()

	public static void dump(ResultSet rs) throws SQLException {

		// the order of the rows in a cursor
		// are implementation dependent unless you use the SQL ORDER statement
		ResultSetMetaData meta = rs.getMetaData();
		int colmax = meta.getColumnCount();
		int i;
		Object o = null;

		// the result set is a cursor into the data. You can only
		// point to one row at a time
		// assume we are pointing to BEFORE the first row
		// rs.next() points to next row and returns true
		// or false if there is no next row, which breaks the loop
		for (; rs.next();) {
			for (i = 0; i < colmax; ++i) {
				o = rs.getObject(i + 1); // Is SQL the first column is indexed

				// with 1 not 0
				System.out.print(o.toString() + " ");
			}

			System.out.println(" ");
		}
	} // void dump( ResultSet rs )

	public static void initialize() {

		try {

			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:" + "database", "sa", "");

		} catch (Exception ex1) {
			ex1.printStackTrace();
			return;
		}

		try {

			// make an empty table
			//
			// by declaring the id column IDENTITY, the db will automatically
			// generate unique values for new rows- useful for row keys
			// update("CREATE TABLE Idealista(idd INTEGER IDENTITY, id
			// VARCHAR(256), url VARCHAR(256), telefono INTEGER)");
			// update("DROP TABLE Idealista");
			// update("DROP TABLE SAMPLE_TABLE");
			update("CREATE TABLE Inmueble ( idd INTEGER IDENTITY, time TIMESTAMP DEFAULT NOW, id VARCHAR(256), direccion VARCHAR(256), zona VARCHAR(256), descripcion VARCHAR(2048), url VARCHAR(256), telefono INTEGER, precio INTEGER)");
			update("CREATE TABLE Inmobiliaria ( idd INTEGER IDENTITY, time TIMESTAMP DEFAULT NOW, id_source VARCHAR(256), nombre VARCHAR(2048), telefono INTEGER)");
		} catch (SQLException ex2) {

			// ignore
			ex2.printStackTrace(); // second time we run program
			// should throw execption since table
			// already there
			//
			// this will have no effect on the db
		}
	}

	public static void add_rows(ArrayList<Inmueble> inmuebleList) {

		try {
			Iterator<Inmueble> it = inmuebleList.iterator();
			while (it.hasNext()) {
				Inmueble inm = it.next();
				//if (inm.getDescripcion() == null) { inm.setDescripcion("Vacio"); }
				System.out.println(inm.getDescripcion().length());
				//inm.setDescripcion("Vacio");
				
				System.out.println("INSERT INTO Inmueble(id,direccion,zona,descripcion,url,telefono,precio) VALUES('" + inm.getId()
						+ "', '" + inm.getDireccion() + "', '" + inm.getZona() + "', '"
						+ inm.getDescripcion().substring(0, Math.min(inm.getDescripcion().length(), 2046)) + "', '" + inm.getUrl() + "', '" + inm.getTelefono()
						+ "', '" + inm.getPrecio() + "')");
				update("INSERT INTO Inmueble(id,direccion,zona,descripcion,url,telefono,precio) VALUES('" + inm.getId() + "', '"
						+ inm.getDireccion() + "', '" + inm.getZona() + "', '" + inm.getDescripcion().substring(0, Math.min(inm.getDescripcion().length(), 2046))
						+ "', '" + inm.getUrl() + "', '" + inm.getTelefono() + "', '" + inm.getPrecio() + "')");
				//update("COMMIT;");
			}
		} catch (SQLException ex3) {
			ex3.printStackTrace();
		}
	}
}
