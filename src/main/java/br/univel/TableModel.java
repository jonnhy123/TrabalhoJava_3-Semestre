package br.univel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {

	private List<String> lista;
	private Class<?> clazz;
	int numeroColuna = 0;
	List<String> nomeColuna = new ArrayList<String>();
	int index = 0;
	
	public TableModel(List<String> lista, Class<?> clazz) {
		this.lista = lista;
		this.clazz = clazz;
		
		for(Field field : clazz.getDeclaredFields()){
			if(field.isAnnotationPresent(Coluna.class)){
				numeroColuna++;
				nomeColuna.add(field.getName().toUpperCase());
			}
		}
	}
	
	public int getColumnCount() {
		return numeroColuna;
	}

	public int getRowCount() {
		return lista.size() / numeroColuna;
	}

	@Override
	public String getColumnName(int column) {
		return nomeColuna.get(column);
	}	

	public Object getValueAt(int row, int column) {
		return lista.get(index++);
	}
}

