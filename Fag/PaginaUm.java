package Fag; // 'Fag' como no seu novo código

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Fag.objetos.Produto; // Importando o objeto Produto
import Fag.objetos.Caixa;   // Importando o objeto Caixa

public class PaginaUm {
	static List<Produto> estoque = new ArrayList<>();
	static Scanner scan = new Scanner(System.in);
	
	static List<Caixa> notasNoCaixa = new ArrayList<>();
	
	public static void main(String[] args) {
		inicializarCaixa();
		mostrarMenu();
	}
	
	
	
	public static void mostrarMenu() {
		int escolha = 1000;
		
		do {
		System.out.println("\n--------Menu--------");
		System.out.println("1 - Cadastrar produto");
		System.out.println("2 - Realizar Venda");
		System.out.println("3 - Mostrar Estoque");
		System.out.println("4 - Mostrar Caixa");
		System.out.println("0 - Sair");
		escolha = scan.nextInt();
		scan.nextLine();
		validarEscolha(escolha);
		}while(escolha != 0);
	}
	
	
	public static void validarEscolha(int escolha) {
		switch (escolha) {
		case 1:
			cadastrarProduto();
			break;
		case 2:
			realizarVenda();
			break;
		case 3:
			mostrarEstoque();
			break;
		case 4:
			mostrarCaixa();
			break;
		case 0:
			System.out.println("encerrando programa...╰(*°▽°*)╯");
			break;	
		default:
			System.out.println("escolha uma opção válida!");
			break;
		}
	}
	
	
	public static void mostrarCaixa() {
		System.out.println("\n--- Notas no Caixa ---");
		double saldoTotal = 0;
		for (int i = 0; i < notasNoCaixa.size(); i++) {
			notasNoCaixa.get(i).mostrarResumo();
			// Calcula o saldo (valor da nota * quantidade)
			saldoTotal = saldoTotal + (notasNoCaixa.get(i).getValor() * notasNoCaixa.get(i).getQuantidade());
		} 
		System.out.println("----------------------------");
		System.out.printf("Saldo total em caixa: R$ %.2f\n", saldoTotal);
	}
	
	
	// ##### MÉTODO MODIFICADO #####
	public static void realizarVenda() {
		
		if(estoque.size() == 0) {
			System.out.println("Não há produtos cadastrados para vender!");
			return;
		}
		
		mostrarEstoque();
		System.out.println("Qual produto deseja vender? (informe a posição)");
		int escolhaProduto = scan.nextInt();
		scan.nextLine();
		
		if(escolhaProduto < 1 || escolhaProduto > estoque.size()) {
			System.out.println("Produto inexistente!");
			return;
		}
		
		Produto produtoVenda = estoque.get(escolhaProduto - 1);
		
		System.out.printf("Quantos '%s' deseja vender? (Disponível: %d)\n", produtoVenda.getNome(), produtoVenda.getEstoque());
		int quantidadeVenda = scan.nextInt();
		scan.nextLine();
		
		// Validação de estoque
		if(quantidadeVenda <= 0) {
			System.out.println("Quantidade deve ser positiva!");
			return;
		}
		if(quantidadeVenda > produtoVenda.getEstoque()) {
			System.out.println("Não há estoque suficiente para esta venda!");
			return;
		}
		
		double totalVenda = produtoVenda.getPreco() * quantidadeVenda;
		System.out.printf("Total da Venda: R$ %.2f\n", totalVenda);
		
		// Lógica de pagamento e troco
		System.out.println("Qual o valor pago pelo cliente?");
		double valorPago = scan.nextDouble();
		scan.nextLine();
		
		// --- LÓGICA DE TROCO ---
		// Ignorando centavos, como falamos
		int totalVendaInt = (int) totalVenda;
		int valorPagoInt = (int) valorPago;
		
		if(valorPagoInt < totalVendaInt) {
			System.out.println("Valor pago é insuficiente! Venda cancelada.");
			return;
		}
		
		int trocoNecessario = valorPagoInt - totalVendaInt;
		
		if (trocoNecessario == 0) {
			// Venda sem troco, mais fácil
			produtoVenda.setEstoque(produtoVenda.getEstoque() - quantidadeVenda);
			System.out.println("Venda realizada com sucesso! (Sem troco). Estoque atualizado.");
			// (Aqui poderíamos adicionar o dinheiro ao caixa, mas vamos focar no troco)
		
		} else {
			// Venda COM troco
			System.out.printf("Troco a ser dado: R$ %d\n", trocoNecessario);
			
			// Chama o novo método para TENTAR dar o troco
			boolean sucessoTroco = darTroco(trocoNecessario);
			
			if (sucessoTroco) {
				// Deu certo! O caixa já foi atualizado. Agora atualiza o estoque.
				produtoVenda.setEstoque(produtoVenda.getEstoque() - quantidadeVenda);
				System.out.println("Venda realizada com sucesso! Estoque e caixa atualizados.");
			} else {
				// Deu errado! O método darTroco já avisou o porquê.
				System.out.println("Venda CANCELADA. O caixa não tem notas suficientes.");
			}
		}
	}
	
	
	public static void mostrarEstoque() {
		System.out.println("\n--- Produtos em Estoque ---");
		if(estoque.size() == 0) {
			System.out.println("Nenhum produto cadastrado.");
		}
		
		for (int i = 0; i < estoque.size(); i++) {
			System.out.printf("Posição: %d - ", i+1);
			estoque.get(i).mostrarResumo();
		} 
		System.out.println("----------------------------\n");
	}
	
	
	public static void cadastrarProduto() {
		Produto produto = new Produto();
		System.out.println("informe o nome");
		String nome = scan.nextLine();
		
		// Validação de nome duplicado (requisito)
		for(int i = 0; i < estoque.size(); i++) {
			if(estoque.get(i).getNome().equals(nome)) {
				System.out.println("Erro: Já existe um produto com esse nome!");
				return;
			}
		}
		produto.setNome(nome);
		
		System.out.println("informe o preço");
		produto.setPreco(scan.nextDouble());
		scan.nextLine();
		
		System.out.println("informe o estoque inicial");
		produto.setEstoque(scan.nextInt());
		scan.nextLine();
		
		estoque.add(produto);
		System.out.println("o produto foi adicionado com sucesso!");
	}
	
	public static void inicializarCaixa() {
		// Requisito: 5 de 50, 5 de 20, 5 de 10
		// IMPORTANTE: Adicionar da nota MAIOR para a MENOR
		Caixa nota50 = new Caixa(50, 5);
		Caixa nota20 = new Caixa(20, 5);
		Caixa nota10 = new Caixa(10, 5);
		notasNoCaixa.add(nota50);
		notasNoCaixa.add(nota20);
		notasNoCaixa.add(nota10);
	}
	
	
	// ##### NOVO MÉTODO #####
	/**
	 * Tenta calcular e subtrair o troco do caixa.
	 * Retorna 'true' se conseguir, e 'false' se não tiver notas.
	 */
	public static boolean darTroco(int trocoPendente) {
		// Este método é complexo. Ele primeiro "planeja" o troco.
		// Se o plano der certo (trocoPendente chegar a 0),
		// ele atualiza o caixa de verdade.
		
		int trocoOriginal = trocoPendente; // Salva para a mensagem final
		
		// Arrays "paralelos" para planejar o troco
		// Isso é mais simples que criar objetos novos
		int notasParaUsar_50 = 0;
		int notasParaUsar_20 = 0;
		int notasParaUsar_10 = 0;
		
		// Pega as referências dos objetos 'Caixa' na lista
		// (Assume que a ordem é 50, 20, 10, como em 'inicializarCaixa')
		Caixa objNota50 = notasNoCaixa.get(0);
		Caixa objNota20 = notasNoCaixa.get(1);
		Caixa objNota10 = notasNoCaixa.get(2);
		
		// --- FASE 1: Planejamento (sem alterar o caixa) ---
		
		// Tenta usar notas de 50
		int notasNecessarias = trocoPendente / 50;
		if (notasNecessarias > objNota50.getQuantidade()) {
			notasParaUsar_50 = objNota50.getQuantidade(); // Usa todas que tem
		} else {
			notasParaUsar_50 = notasNecessarias; // Usa só as necessárias
		}
		trocoPendente = trocoPendente - (notasParaUsar_50 * 50);
		
		// Tenta usar notas de 20
		notasNecessarias = trocoPendente / 20;
		if (notasNecessarias > objNota20.getQuantidade()) {
			notasParaUsar_20 = objNota20.getQuantidade();
		} else {
			notasParaUsar_20 = notasNecessarias;
		}
		trocoPendente = trocoPendente - (notasParaUsar_20 * 20);
		
		// Tenta usar notas de 10
		notasNecessarias = trocoPendente / 10;
		if (notasNecessarias > objNota10.getQuantidade()) {
			notasParaUsar_10 = objNota10.getQuantidade();
		} else {
			notasParaUsar_10 = notasNecessarias;
		}
		trocoPendente = trocoPendente - (notasParaUsar_10 * 10);
		
		
		// --- FASE 2: Veredito ---
		
		if (trocoPendente == 0) {
			// SUCESSO! O plano funcionou.
			// Agora, e somente agora, atualiza o caixa.
			objNota50.setQuantidade( objNota50.getQuantidade() - notasParaUsar_50 );
			objNota20.setQuantidade( objNota20.getQuantidade() - notasParaUsar_20 );
			objNota10.setQuantidade( objNota10.getQuantidade() - notasParaUsar_10 );
			
			System.out.printf("Troco de R$ %d dado com sucesso:\n", trocoOriginal);
			if(notasParaUsar_50 > 0) { System.out.printf(" - %d nota(s) de R$50\n", notasParaUsar_50); }
			if(notasParaUsar_20 > 0) { System.out.printf(" - %d nota(s) de R$20\n", notasParaUsar_20); }
			if(notasParaUsar_10 > 0) { System.out.printf(" - %d nota(s) de R$10\n", notasParaUsar_10); }
			
			return true; // Retorna SUCESSO
			
		} else {
			// FALHA! O plano não funcionou.
			// Não faltam só R$10, faltam notas de R$10.
			// (Ex: Troco de R$30, mas só tem 1 de R$20 e 0 de R$10. Faltam R$10)
			System.out.printf("Erro: Não foi possível dar o troco. Faltaram R$ %d.\n", trocoPendente);
			System.out.println("O caixa não tem a combinação certa de notas.");
			return false; // Retorna FALHA
		}
	}

}