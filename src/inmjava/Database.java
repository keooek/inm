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
			// db = new Database("db_file");
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:" + "database", // filenames
					"sa", // username
					""); // password

		} catch (Exception ex1) {
			ex1.printStackTrace(); // could not start db

			return; // bye bye
		}

		try {

			// make an empty table
			//
			// by declaring the id column IDENTITY, the db will automatically
			// generate unique values for new rows- useful for row keys
			// update("CREATE TABLE Idealista(idd INTEGER IDENTITY, id
			// VARCHAR(256), url VARCHAR(256), telefono INTEGER)");
			//update("DROP TABLE Idealista");
			//update("DROP TABLE SAMPLE_TABLE");
			update("CREATE TABLE Idealista ( idd INTEGER IDENTITY, id VARCHAR(256), direccion VARCHAR(256), zona VARCHAR(256), descripcion VARCHAR(2048), url VARCHAR(256), telefono INTEGER, precio INTEGER)");
		} catch (SQLException ex2) {

			// ignore
			// ex2.printStackTrace(); // second time we run program
			// should throw execption since table
			// already there
			//
			// this will have no effect on the db
		}
	}

	public static void add_rows(ArrayList<Inmueble> inmuebleList) {

		try {

			// add some rows - will create duplicates if run more then once
			// the id column is automatically generated
			// update("INSERT INTO sample_table(str_col,num_col) VALUES('Ford',
			// 100)");
			Iterator<Inmueble> it = inmuebleList.iterator();
			while (it.hasNext()) {
				Inmueble inm = it.next();
				System.out.println("INSERT INTO Idealista(id,direccion,zona,url,telefono,precio) VALUES('" + inm.getId()
						+ "', '" + inm.getDireccion() + "', '" + inm.getZona()
						+ "', '" + inm.getUrl() + "', '" + inm.getTelefono() + "', '" + inm.getPrecio() + "')");
				update("INSERT INTO Idealista(id,direccion,zona,url,telefono,precio) VALUES('" + inm.getId()
						+ "', '" + inm.getDireccion() + "', '" + inm.getZona()
						+ "', '" + inm.getUrl() + "', '" + inm.getTelefono() + "', '" + inm.getPrecio() + "')");
			}
			// update("INSERT INTO sample_table(str_col,num_col) VALUES('Honda',
			// 300)");
			// update("INSERT INTO sample_table(str_col,num_col) VALUES('GM',
			// 400)");

			// do a query
			//query("SELECT * FROM Idealista");

			// at end of program
			// shutdown();
		} catch (SQLException ex3) {
			ex3.printStackTrace();
		}
	}
}
