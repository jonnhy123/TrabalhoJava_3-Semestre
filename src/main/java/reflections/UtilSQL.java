package reflections;

import java.lang.reflect.Field;

import br.univel.Coluna;
import br.univel.Tabela;

public class UtilSQL {

	private String CREAE_TABLE (Class<?> clazz){
		StringBuilder sb = new StringBuilder();
		//Declaração da tabela
		String nomeTabela;
		if (clazz.isAnnotationPresent(Tabela.class)) {
			Tabela anotacaoTabela = clazz.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();
		}else{
			nomeTabela = clazz.getSimpleName().toUpperCase();
		}
		sb.append("CREATE TABLE ").append(nomeTabela).append(" (");
		
		Field[] atributos = clazz.getDeclaredFields();
		//Declaração das colunas
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
		}
	}
	
}
