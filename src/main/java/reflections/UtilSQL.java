package reflections;

import java.lang.reflect.Field;

import javax.print.attribute.standard.RequestingUserName;

import br.univel.Tabela;

public class UtilSQL {
	private Class<?> clazz;//<?> pode ser qualquer classe
	
	public String CreateTable(Object obj){
		clazz = (Class<?>) obj;
		String nomeTabela = tableName(clazz);
		
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE "+nomeTabela+"(");
		
		Field[] atributos = clazz.getDeclaredFields();
		
		for (int i = 0; i < atributos.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			
			Field field = atributos[i];
		}
	}

	private String tableName(Class<?> obj) {
		clazz = (Class<?>) obj;
		
		String nomeTabela;
		//se a anotação estiver presente na classe
		if (clazz.isAnnotationPresent(Tabela.class)) {
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			//nomeTabela recebe o valor da anotacaoTabela, tudo minusculo
			nomeTabela = anotacaoTabela.value().toLowerCase();
		}else{
			//nomeTabela recebe o nome da classe simples, EX: CALOPSITA, tudo maiusculo
			nomeTabela = clazz.getSimpleName().toUpperCase();
		}
		return nomeTabela;
	}
}
