package Fag.objetos;

public class Caixa { // Nome do arquivo como na sua imagem

	private int valor;
	private int quantidade;
	
	public Caixa() {
	}
	
	public Caixa(int valor, int quantidade) {
		setValor(valor);
		setQuantidade(quantidade);
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor (int valor) {
		if(valor > 0) {
			this.valor = valor;
		}
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade (int quantidade) {
		if(quantidade >= 0) {
			this.quantidade = quantidade;
		}	
	}
	

	public void mostrarResumo() {
		System.out.printf("Nota: R$ %d - Quantidade: %d\n", valor, quantidade);
	}

	@Override
	public String toString() {
		return "Caixa [valor=" + valor + ", quantidade=" + quantidade + "]";
	}
	
	
	
}