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
	private String CREAE_TABLE (Class<?> clazz){
		StringBuilder sb = new StringBuilder();
		//Declara��o da tabela
		String nomeTabela;
		if (clazz.isAnnotationPresent(Tabela.class)) {
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();
		}else{
			nomeTabela = clazz.getSimpleName().toUpperCase();
		}
		sb.append("CREATE TABLE ").append(nomeTabela).append(" (");
		
		Field[] atributos = clazz.getDeclaredFields();
		//Declara��o das colunas
		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i]; 
			String nomeColuna, tipoColuna;
			//Se anota��o coluna est� presente no atributo
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
		//Declara��o das chaves prim�rias
		sb.append(",\nPRIMARY KEY( ");
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
			sb.append(" )");
		}
		sb.append("\n);");
		return sb.toString();
	}
	
	//Inserir na Tabela
	private PreparedStatement INSERTINTO(Object obj){
		Class<? extends Object> clazz = obj.getClass();
		StringBuilder sb = new StringBuilder();
		//Declara��o da tabela
		String nomeTabela;
		if (clazz.isAnnotationPresent(Tabela.class)) {
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();
		}else{
			nomeTabela = clazz.getSimpleName().toUpperCase();
		}
		sb.append("INSERT INTO ").append(nomeTabela).append(" (");
		Field[] atributos = clazz.getDeclaredFields();
		//Declara��o das colunas
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
		sb.append(") VALUES (");
		
		for (int i = 0; i < atributos.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append('?');
		}
		sb.append(')');
		
		String strSql = sb.toString();
		System.out.println("SQL GERADO: "+ strSql);
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(strSql);
		
		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];
			//importante n�o esquecer
			field.setAccessible(true);
			if (field.getType().equals(int.class)) {
				ps.setInt(i + 1, field.getInt(obj));
			}else if (field.getType().equals(String.class)){
				ps.setString(i + 1, String.valueOf(field.get(obj)));
			}else{
				throw new RuntimeException("Tipo n�o suportado!!!");
			}
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return ps;
	}
}
