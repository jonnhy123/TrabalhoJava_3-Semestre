package reflections;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import br.univel.Coluna;
import br.univel.Tabela;

public class UtilSQL {
	private Class<?> clazz;// <?> pode ser qualquer classe

	public String CreateTable(Object obj) {
		clazz = (Class<?>) obj;
		String nomeTabela = tableName(clazz);

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + nomeTabela + "(");

		Field[] atributos = clazz.getDeclaredFields();

		for (int i = 0; i < atributos.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}

			Field field = atributos[i];

			Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
			String nomeColuna = nomeDaColuna(field, anotacaoColuna);
			String tipoDaColuna = "";

			Class<?> parametro = field.getType();
			if (parametro.equals(String.class)) {
				tipoDaColuna = "VARCHAR(255)";
			} else if (parametro.equals(int.class)) {
				if (!anotacaoColuna.pk()) {
					tipoDaColuna = "INTEGER";
				}
			} else if (parametro.equals(BigDecimal.class)) {
				tipoDaColuna = "NUMERIC(3, 1)";
			} else {
				tipoDaColuna = "sem tipo";
			}

			StringBuilder pk = new StringBuilder();
			if (anotacaoColuna.pk()) {
				pk.append("PRIMARY KEY ");
			}

			sb.append(nomeColuna + " " + tipoDaColuna);
		}
		sb.append("\n");

		return sb.toString();
	}

	private String nomeDaColuna(Field field, Coluna anotacaoColuna) {
		String nomeColuna;
		if (field.isAnnotationPresent(Coluna.class)) {
			if (anotacaoColuna.nome().isEmpty()) {
				nomeColuna = field.getName().toLowerCase();
			} else {
				nomeColuna = anotacaoColuna.nome();
			}
		} else {
			nomeColuna = field.getName().toLowerCase();
		}
		return nomeColuna;
	}

	private String tableName(Object obj) {
		clazz = (Class<?>) obj;

		String nomeTabela;
		// se a anotação estiver presente na classe
		if (clazz.isAnnotationPresent(Tabela.class)) {
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			// nomeTabela recebe o valor da anotacaoTabela, tudo minusculo
			nomeTabela = anotacaoTabela.value().toLowerCase();
		} else {
			// nomeTabela recebe o nome da classe simples, EX: CALOPSITA, tudo
			// maiusculo
			nomeTabela = clazz.getSimpleName().toUpperCase();
		}
		return nomeTabela;
	}

	public String insertInto(Object obj, List<?> lista) {
		clazz = obj.getClass();
		StringBuilder sb = new StringBuilder();
		StringBuilder nome = new StringBuilder();
		StringBuilder valor = new StringBuilder();
		nome.append("INSERT INTO " + tableName(obj) + "(");
		int aux = 0;
		for (Field field : clazz.getDeclaredFields()) {
			Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
			if (aux > 1) {
				nome.append(", ");
			}
			field.setAccessible(true);
			if (anotacaoColuna.pk() == false) {
				nome.append(nomeDaColuna(field, anotacaoColuna));
			}
			aux++;
		}
		nome.append(")VALUES (");

		int aux2 = 0;

		for (Field field : clazz.getDeclaredFields()) {
			Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
			field.setAccessible(true);
			if (aux2 > 1) {
				valor.append(", ");
			}
			if (anotacaoColuna.pk() == false) {
				if (field.getType().equals(String.class)) {
					valor.append("'" + lista.get(aux2) + "'");
				} else {
					valor.append(lista.get(aux2));
				}
			}
			aux2++;
		}
		valor.append(");");
		sb.append(nome.append(valor));

		return sb.toString();
	}
	
	public String delete(Object obj, int id){
		StringBuilder sb = new StringBuilder();
		for(Field field: clazz.getDeclaredFields()){
			field.setAccessible(true);
			Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
			//Somente entrará se for delete por primary key.
			if(anotacaoColuna.pk() == true){
				sb.append(nomeDaColuna(field, anotacaoColuna)+ " = ");
				try {
					sb.append(id +";");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public String drop(Object obj){
		StringBuilder sb = new StringBuilder();
		
		sb.append("DROP TABLE "+ tableName(obj) +";");
		return sb.toString();
	}
	
	public String selectFrom(Object obj){
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * FROM " + tableName(obj) + ";");		
		return sb.toString();
	}
	
	public String selectBy(Object obj, int id){
		clazz = (Class<?>) obj;
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT * FROM " + tableName(obj) + " WHERE ");
		for(Field field : clazz.getDeclaredFields()){
			field.setAccessible(true);
			Coluna anotacaoColuna = field.getAnnotation(Coluna.class);
			
			if(anotacaoColuna.pk() == true){
				sb.append(nomeDaColuna(field, anotacaoColuna)+ " = ");
				try {
					sb.append(id +";");
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
