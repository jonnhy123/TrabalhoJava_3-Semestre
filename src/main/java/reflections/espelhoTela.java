package reflections;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import DAO.Connection;
import br.univel.Coluna;

public class espelhoTela {
	
	private JTable table;
	private Class<?> clazz;
	int id;
	
	public JPanel painel(Object o){
		
		final Connection sql = new Connection();
		JPanel contentPane = new JPanel();
		clazz = o.getClass();
		
		final List<JTextField> textFields = new ArrayList<JTextField>();
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
//		int x = 0, y = 2, z = 0;
		int x = 0, y = 2, z = 0;
		
		for (Field field : clazz.getDeclaredFields()) {
			JLabel lblNewLabel = new JLabel(field.getAnnotation(Coluna.class).nome().toUpperCase());
			//aonde (0, x); 0 = column && x = row
			contentPane.add(lblNewLabel, createConstraints(0, x));
			JTextField textField = new JTextField();
			textFields.add(textField);
			textField.setName(field.getName());
			//aonde (1, x); 1 = column && x = row
			contentPane.add(textField, createConstraints(1, x));
			textField.setColumns(10);
			x++;
		}
		x++;
		
		
final JScrollPane scrollPane = new JScrollPane();
		
//		JButton create = new JButton("CRIAR TABELA");
//		create.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				sql.CREATE(clazz);
//			}
//		});
//		contentPane.add(create, createConstraints(x++, y));
//		
//		JButton insert = new JButton("INSERIR DADOS");		
//		insert.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				List<String> lista = new ArrayList<String>();
//				for(int i = 0; i < textFields.size(); i++){
//					lista.add(textFields.get(i).getText());
//				}
//				try {
//					sql.INSERTINTO(clazz);
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		contentPane.add(insert, createConstraints(x++, y));
//		
//		JButton delete = new JButton("DELETAR");		
//		delete.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				//Pega o id que está presente no primeiro campo.
//				id = Integer.parseInt(textFields.get(0).getText());
//				try {
//					sql.DELETETABLE(clazz, id);
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		contentPane.add(delete, createConstraints(x++, y));
//		
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 600 + z;
		gbc_scrollPane.gridwidth = 600 + z;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 10;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		return contentPane;
	}

	
	public GridBagConstraints createConstraints(int x, int y){
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridx = x;
		gbc_textField.gridy = y;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.anchor = GridBagConstraints.EAST;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		return gbc_textField;
	}
}
