package br.univel;

@Tabela("CAD_CALOPSITA")//Nome da tabela no banco
public class Calopsita {
	 
	 @Coluna(pk=true)//Coluna id vai ser Primary key
	 private int id;
	 
	 @Coluna(nome="CAL_NOME")//Coluna CAL_NOME
	 private String nome;

	public Calopsita(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
}
