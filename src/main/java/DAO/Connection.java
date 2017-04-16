package DAO;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connection {

	private java.sql.Connection con;
	
	private void conecta(){
		String url  = "jdbc:postgresql://localhost:5432/postgres", 
			   user = "postgres", 
			   pass = "univel",
			   driver = "postgresql-9.1-901-1.jdbc4.jar";
				
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pass);
			PreparedStatement ps; 
		} catch (SQLException e) {
			System.err.println("ERRO NO METODO \"CONECTA()\" DA CLASSE Connection\n");
		} catch (ClassNotFoundException e) {
			System.err.println("ERRO NO DRIVER DO METODO \"CONECTA()\" DA CLASSE Connection\n");
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				desconecta();
			}
		}));
	}

	protected void desconecta() {
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				System.err.println("ERRO NO METODO \"Desconecta()\" DA CLASSE Connection\n");
			}
		}
	}
	
}
