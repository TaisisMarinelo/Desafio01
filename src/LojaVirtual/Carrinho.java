package LojaVirtual;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Carrinho {

	public static void main(String[] args) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		ArrayList<Produto> produtos = new ArrayList<Produto>();
		ArrayList<Produto> carrinho = new ArrayList<Produto>();

		Produto p1 = new Produto(0, "Oleo", 50, 3.98);
		Produto p2 = new Produto(1, "Chocolate", 50, 4.98);
		Produto p3 = new Produto(2, "Morangos", 50, 6.98);
		Produto p4 = new Produto(3, "Danoninho", 50, 5.98);
		Produto p5 = new Produto(4, "Requeijão", 50, 2.98);
		Produto p6 = new Produto(5, "Alface", 50, 1.98);
		Produto p7 = new Produto(6, "Laranja", 50, 3.98);
		Produto p8 = new Produto(7, "Desodorante", 30, 4.98);
		Produto p9 = new Produto(8, "Papel Higienico", 50, 11.98);
		Produto p10 = new Produto(9, "Picanha", 50, 59.98);

		produtos.add(p1);
		produtos.add(p2);
		produtos.add(p3);
		produtos.add(p4);
		produtos.add(p5);
		produtos.add(p6);
		produtos.add(p7);
		produtos.add(p8);
		produtos.add(p9);
		produtos.add(p10);

		Scanner entrada = new Scanner(System.in);

		int pedido, quantidade, pagamento, quantidader;
		char resposta = 'S';
		double total, desconto = 0;
		final double imposto = 0.09;

		System.out.println("Olá, Cliente. Informe o seu nome: ");
		String nomeCliente = entrada.next();

//1 -----------------
		System.out.println("Olá, " + nomeCliente + ". Seja Bem vindo!");
		do {
			mostrarProdutos(produtos);

			do {
				System.out.println("Informe o código do produto que deseja: \n");
				pedido = entrada.nextInt();
			} while (pedido < 0 || pedido > 9);
			System.out.println("Informe a quantidade desejada do produto: \n");
			quantidade = entrada.nextInt();
			if (produtos.get(pedido).getQuantidade() - quantidade >= 0 && quantidade > 0) {
				produtos.get(pedido).setQuantidade(produtos.get(pedido).getQuantidade() - quantidade);
				carrinho.add(new Produto(pedido, produtos.get(pedido).getNomeProduto(), quantidade,
						produtos.get(pedido).getPrecounit()));
			} else {
				System.out.println("Estoque indisponível.");
			}
			System.out.println("Deseja continuar comprando? [S-Sim/N-Não]: \n");
			resposta = entrada.next().charAt(0);
		} while (resposta == 'S' || resposta == 's');

//2	-----------------	

		total = mostrarCarrinho(carrinho);
		System.out.println("Deseja remover itens do carrinho? [S-Sim/N-Não]: ");
		resposta = entrada.next().charAt(0);
		while (resposta == 'S' || resposta == 's') {
			System.out.println("Digite o código do produto a ser removido: ");
			int i = entrada.nextInt();
			remover(carrinho, i);
			if (carrinho.isEmpty()) {
				break;
			}

			System.out.println("Deseja continuar removendo? [S-Sim/N-Não]: ");
			resposta = entrada.next().charAt(0);

		}

//3 -----------------		
		if (!carrinho.isEmpty()) {
			total = mostrarCarrinho(carrinho);
			System.out.println("Deseja alterar a quantidade de algum item do carrinho? [S-Sim/N-Não]: ");
			resposta = entrada.next().charAt(0);
			while (resposta == 'S' || resposta == 's') {
				System.out.println("Digite o codigo do produto a ser alterado: ");
				int a = entrada.nextInt();
				mostrarProdutos(produtos);
				System.out.println("\nDigite o número de itens que deseja manter: ");
				quantidader = entrada.nextInt();
				alterarQuantidade(carrinho, produtos, a, quantidader);
				System.out.println("Deseja continuar alterando? [S-Sim/N-Não]: ");
				resposta = entrada.next().charAt(0);
				total = mostrarCarrinho(carrinho);
			}
		}
//4 -----------------
		if (!carrinho.isEmpty()) {
			System.out.println("\n==================================================================================");
			System.out.println("\t\tFORMA DE PAGAMENTO");

			do {
				System.out.println("\t 1 - Pix ou Dinheiro, com 20% de desconto.");
				System.out.println("\t 2 - Cartão à vista, Cartão PAN com 15% OUTROS com 10% de desconto.");
				System.out.println("\t 3 - Cartão parcelado, em até 3x SEM JUROS.");
				System.out.println("\t SELECIONE A FORMA DE PAGAMENTO DESEJADA: ");
				pagamento = entrada.nextInt();
			} while (pagamento < 1 || pagamento > 3);
			Pagamento pg = new Pagamento();
			if (pagamento == 3) {
				System.out.println("Em quantas parcelas: \n");
				int parcelas = entrada.nextInt();
				pg.setParcelado(total, parcelas);
			} else if (pagamento == 2) {
				System.out.println("Cartão Pan tem 15% de desconto.");
				System.out.println("Quer usar o seu Cartão Pan? ");
				char r = entrada.next().charAt(0);
				pg.setCredito(total, r);
			} else {
				pg.setPixDinheiro(total);
			}

//5 -----------------	
			desconto = total - pg.getformPagamento();
			if (desconto > 0) {
				desconto = total - pg.getformPagamento();
			} else {
				desconto = 0.00;
			}

			int nf = 0;
			do {
				Random gerado = new Random();
				nf = gerado.nextInt(999);
			} while (nf < 100);

			System.out.println("\n==================================================================================");
			System.out.println("\t\t\tNOTA FISCAL");
			System.out.println("\n==================================================================================");
			System.out.println("RAZÃO SOCIAL: Supermercador PANdeLó");
			System.out.println("CNPJ: 12.456.789/0001-12");
			System.out.println("ENDEREÇO: Rua do Pão de Ló 123, Beco Diagonal, São Paulo - SP");
			System.out.println(dtf.format(LocalDateTime.now()));
			System.out.println("NF-e: " + nf);
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("Item" + "\t\tQTD." + "\tPREÇO UNIT." + "\tPREÇO TOTAL ITENS");
			for (Produto p : carrinho) {
				System.out.printf("%s \t\t %.2f \n", p.toStringNota(), p.getPrecounit() * p.getQuantidade());
			}
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.printf("\tTOTAL DA COMPRA: R$ %.2f \n", total);
			System.out.printf("\tVALOR DO DESCONTO: R$ %.2f \n", desconto);
			System.out.printf("\tVALOR FINAL: R$ %.2f \n", pg.getformPagamento());
			System.out.printf("\tVALOR DO IMPOSTO: R$ %.2f \n", (total * imposto));
			System.out.println("\n==================================================================================");
		} else {
			System.out.println("Seu carrinho está vazio. Esperamos revê-lo em breve.");
		}

		entrada.close();
	}

//6.1 -----------------
	public static boolean alterarQuantidade(ArrayList<Produto> carrinho, ArrayList<Produto> produtos, int a,
			int quantidader) {
		int ver = 0;
		for (Produto produto : carrinho) {
			if (produto.getCodigo() == a) {
				ver = 1;
				do {
					if (quantidader > produtos.get(a).getQuantidade() + produto.getQuantidade()) {
						System.out.println("Quantidade indisponivel.");
						return false;
					} else if (quantidader < 0) {
						System.out.println("Indisponivel, favor verifique a quantidade disponivel em nosso estoque!");
						return false;
					}
				} while (quantidader > produtos.get(a).getQuantidade() + produto.getQuantidade());
				produtos.get(a).setQuantidade(produtos.get(a).getQuantidade() + produto.getQuantidade() - quantidader);
				produto.setQuantidade(quantidader);
				return true;
			}
		}

		if (ver == 0) {
			System.out.println("Código não localizado...");
		}
		return false;

	}

//6.2 -----------------	
	public static boolean remover(ArrayList<Produto> carrinho, int i) {
		for (Produto produto : carrinho) {
			if (produto.getCodigo() == i) {
				Produto pRemovido = produto;
				System.out.printf("Produto removido do carrinho: %s\n", pRemovido.getNomeProduto());
				carrinho.remove(produto);
				mostrarCarrinho(carrinho);
				return true;
			}
		}

		System.out.println("Produto não existe no carrinho!");
		return false;
	}

//6.3 -----------------	
	public static double mostrarCarrinho(ArrayList<Produto> carrinho) {
		double total = 0.0;
		System.out.println("\n===================================================================================");
		System.out.println("\t\t\tCARRINHO DE COMPRAS");
		System.out.println("\n===================================================================================");
		System.out.println("COD.\t" + "\tPRODUTO" + "\t\t\tQTD." + "\t\tPREÇO UNIT." + "\tVALOR TOTAL");
		for (Produto p : carrinho) {
			total += p.getPrecounit() * p.getQuantidade();
			System.out.printf("%s\t\tR$%.2f\n", p, p.getPrecounit() * p.getQuantidade());
		}
		System.out.println("\n==================================================================================");
		System.out.printf("TOTAL: R$ %.2f \n", total);

		return total;
	}

//6.4 -----------------
	public static void mostrarProdutos(ArrayList<Produto> produtos) {
		System.out.println("==================================================================================");
		System.out.println("\t\t\tSUPERMERCARDO PANdeLÓ");
		System.out.println("==================================================================================");
		System.out.println("COD\t" + "\tPRODUTO" + "\t\tQUANTIDADE" + "\t\tPREÇO/UNI");
		for (Produto p : produtos) {
			System.out.printf("%s \t \n", p);
		}

	}
}
