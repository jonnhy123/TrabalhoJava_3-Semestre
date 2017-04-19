package reflections;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.univel.Coluna;
import br.univel.Tabela;

public class UtilSQL {

	private Connection con;
	
	//Criar tabela
	public String CREAE_TABLE (Object obj){
		Class<?> clazz = obj.getClass();
		StringBuilder sb = new StringBuilder();
		//Declaração da tabela
	{
		String nomeTabela;
		if (clazz.isAnnotationPresent(Tabela.class)) {
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();
		}else{
			nomeTabela = clazz.getSimpleName().toUpperCase();
		}
		sb.append("CREATE TABLE ").append(nomeTabela).append(" (");
	}
		Field[] atributos = clazz.getDeclaredFields();
		//Declaração das colunas
	{
		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i]; 
			String nomeColuna, tipoColuna;
			//Se anotação coluna está presente no atributo
			if (field.isAnnotationPresent(Coluna.class)) {
				Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
				//Se nome for vazio
				if (anotacaoColuna.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				}else{
					nomeColuna = anotacaoColuna.nome();
				}
			}else{
				nomeColuna = field.getName().toUpperCase();
			}
			
			Class<?> tipoParametro = field.getType();
			
			if (tipoParametro.equals(String.class)) {
				tipoColuna = "VARCHAR(100)";
			}else if (tipoParametro.equals(int.class)){
				tipoColuna = "INT";
			}else{
				tipoColuna = "DESCONHECIDO";
			}
			
			if (i > 0) {
				sb.append(",");
			}
			
			sb.append("\n\t").append(nomeColuna).append(' ').append(tipoColuna);
		}
	}	
		//Declaração das chaves primárias
	{
		sb.append(",\n\tPRIMARY KEY( ");
		for (int i = 0,achou = 0; i < atributos.length; i++) {
			Field field = atributos[i];
			if (field.isAnnotationPresent(Coluna.class)) {
				Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
				if (anotacaoColuna.pk()) {
					if (achou > 0) {
						sb.append(", ");
					}
					if (anotacaoColuna.nome().isEmpty()) {
						sb.append(field.getName().toUpperCase());
					}else{
						sb.append(anotacaoColuna.nome());
					}
					achou++;
				}
			}
		}
		sb.append(" )");
	}
		sb.append("\n);");
		String sqlCreate = sb.toString();
		System.out.println("SQL GERADO: \n"+sqlCreate);
		return sb.toString();
	}
	
	//Inserir na Tabela
	public String INSERTINTO(Object obj){
		Class<?> clazz = obj.getClass();
		StringBuilder sb = new StringBuilder();
		//Declaração da tabela
	{
		String nomeTabela;
		if (clazz.isAnnotationPresent(Tabela.class)) {
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();
		}else{
			nomeTabela = clazz.getSimpleName().toUpperCase();
		}
		sb.append("INSERT INTO ").append(nomeTabela).append(" \n\t(");
	}
		Field[] atributos = clazz.getDeclaredFields();
		//Declaração das colunas
	{	
		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];
			String nomeColuna;
			if (field.isAnnotationPresent(Coluna.class)) {
				Coluna anotacaoColuna = field.getAnnotation(Coluna.class);			
				if (anotacaoColuna.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				}else{
					nomeColuna = anotacaoColuna.nome();
				}
			}else{
				nomeColuna = field.getName().toUpperCase();
			}
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(nomeColuna);
		}
	}

	sb.append(") \nVALUES \n\t(");

		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];
			Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
			field.setAccessible(true);
			if (i > 0) {
				sb.append(", ");
			}
			if (field.getType().equals(int.class)) {
				try {
					sb.append(field.get(obj));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else{
				try {
					sb.append("'"+field.get(obj)+"'");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
//			sb.append('?');
		}
		sb.append(");");
	
		String strSql = sb.toString();
		System.out.println("SQL GERADO: \n"+ strSql);
		return sb.toString();
	}
}
//		
//		PreparedStatement ps;
//		try {
//			ps = con.prepareStatement(strSql);
//		
//		for (int i = 0; i < atributos.length; i++) {
//			Field field = atributos[i];
//			//importante não esquecer
//			field.setAccessible(true);
//			if (field.getType().equals(int.class)) {
//				ps.setInt(i + 1, field.getInt(obj));
//			}else if (field.getType().equals(String.class)){
//				ps.setString(i + 1, String.valueOf(field.get(obj)));
//			}else{
//				throw new RuntimeException("Tipo não suportado!!!");
//			}
//		}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//	

