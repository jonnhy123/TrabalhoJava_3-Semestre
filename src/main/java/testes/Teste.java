package testes;

import DAO.Connection;
import br.univel.Calopsita;
import br.univel.Tabela;

@Tabela(value="Teste")
public class Teste {
	public static void main(String[] args) {
		String nome = "piu piu";
		int id 		= 2;
		
		Calopsita c = new Calopsita(id, nome);
		c.setId(6);
		c.setNome("finality");
		
		System.out.println(c.getNome());
		
		Connection con = new Connection();
		con.conecta();
//		con.CREATE(c);
		con.INSERTINTO(c);
	}
}
