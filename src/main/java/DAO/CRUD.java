package DAO;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.univel.Coluna;
import reflections.UtilSQL;

public class CRUD {

	private Connection con;
	UtilSQL sql = new UtilSQL();
	
		public void Create(Object obj){
			String s = sql.CreateTable(obj);
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement(s);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void Insert(Object obj, List<?> lista) throws IllegalArgumentException, IllegalAccessException{
			String s = sql.insertInto(obj, lista);
			PreparedStatement ps;
			
			try{	
				ps = con.prepareStatement(s);
				ps.executeUpdate();
			} catch (SQLException e){
				e.printStackTrace();
			}
			
		}
		
		public void Delete(Object obj, int id) throws IllegalArgumentException, IllegalAccessException{
			String s = sql.delete(obj, id);
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement(s);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public List<String> selectFrom(Object obj){
			int column = 0;
			for(Field field : ((Class<?>) obj).getDeclaredFields()){
				if(field.isAnnotationPresent(Coluna.class)){
					column++;
				}
			}
			
			List<String> lista = new ArrayList<String>(column);
			String s = sql.selectFrom(obj);
			PreparedStatement ps;
			ResultSet rs;
			try{	
				ps = con.prepareStatement(s);
				rs = ps.executeQuery();
				
				while(rs.next()){
					for(int j = 1; j <= column; j++){
						lista.add(rs.getString(j));
					}			    
				}
			} catch (SQLException e){
				e.printStackTrace();
			}		
			return lista;
		}
		
		public List<String> SearchById(Object obj, int id) throws IllegalArgumentException, IllegalAccessException{
			int column = 0;
			for(Field field : ((Class<?>) obj).getDeclaredFields()){
				if(field.isAnnotationPresent(Coluna.class)){
					column++;
				}
			}
			
			String str = sql.selectBy(obj, id);
			List<String> result = new ArrayList<String>();
			PreparedStatement ps;
			ResultSet rs;
			
			try {
				ps = con.prepareStatement(str);
				rs = ps.executeQuery();
				
				while(rs.next()){
					for(int j = 1; j <= column; j++){
						result.add(rs.getString(j));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		public void drop(Object obj){
			String s = sql.drop(obj);
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement(s);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

