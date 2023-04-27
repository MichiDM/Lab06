package it.polito.tdp.meteo.DAO;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

public class ConnectDB {
	
	// check user e password
	//static private final String jdbcUrl = "jdbc:mysql://127.0.0.1/meteo?user=root&password=Root3rdam";
	
	
	static HikariDataSource dataSource ;  // singleton
	
	public static Connection getConnection() throws SQLException {
		if(dataSource == null) {
			// crea la data source
			dataSource = new HikariDataSource() ;
			dataSource.setJdbcUrl("jdbc:mariadb://localhost/meteo");
			dataSource.setUsername("root");
			dataSource.setPassword("Root3rdam");
		}
		
		return dataSource.getConnection() ;
	}
}
	


