package testes;

import DAO.Connection;
import br.univel.Calopsita;
import br.univel.Tabela;

public class Teste {
	public static void main(String[] args) {
		String nome = "piu piu";
		int id 		= 1;
		
		Calopsita c = new Calopsita(id, nome);
		
		System.out.println(c.getNome());
		
		Connection con = new Connection();
		con.conecta();
//		con.CREATE(c);
//		con.INSERTINTO(c);
		int id_teste = 1;
		con.DELETETABLE(c,id_teste);
	}
}
