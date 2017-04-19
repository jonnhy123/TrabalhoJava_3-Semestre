package DAO;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import reflections.UtilSQL;

public class Connection {

	private java.sql.Connection con;
	UtilSQL sql = new UtilSQL();
	
	public void conecta(){
		String url  = "jdbc:postgresql://localhost:5432/postgres", 
			   user = "postgres", 
			   pass = "univel";				
		try {
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("Conectado com sucesso no banco!!!");
		} catch (SQLException e) {
			System.err.println("ERRO NO METODO \"CONECTA()\" DA CLASSE Connection\n");
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
	
	public void CREATE(Object obj){
		String str = sql.CREAE_TABLE(obj);
		PreparedStatement ps;
		
		try {
			ps = con.prepareStatement(str);
			ps.executeUpdate();
			System.out.println("CREATE TABLE SUCESS FULL");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void INSERTINTO(Object obj){
		String str = sql.INSERTINTO(obj);
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(str);
			ps.executeUpdate();
			System.out.println("Dados inseridos na tabela com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
