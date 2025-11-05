package Fag.objetos;

public class Produto { // Mudou de 'livro' para 'Produto'

	private String nome;
	private double preco;
	private int estoque;
	
	public Produto() {
	}
	
	public Produto(String nome, double preco, int estoque) {
		setNome(nome);
		setPreco(preco);
		setEstoque(estoque);
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome (String nome) {
		if(nome != null && !nome.isBlank()) { // Validação (requisito)
			this.nome = nome;
		}
	}
	
	public double getPreco() {
		return preco;
	}
	
	public void setPreco (double preco) {
		if(preco > 0) { // Validação (requisito)
			this.preco = preco;
		}	
	}
	
	public int getEstoque() {
		return estoque;
	}
	
	public void setEstoque (int estoque) {
		if(estoque >= 0) { // Validação (requisito)
			this.estoque = estoque;
		}
	}
	

	public void mostrarResumo() {
		System.out.printf("Nome: %s - Preço: R$%.2f - Estoque: %d\n", nome, preco, estoque);
	}

	@Override
	public String toString() {
		return "Produto [nome=" + nome + ", preco=" + preco + ", estoque=" + estoque + "]";
	}
	
	
	
}