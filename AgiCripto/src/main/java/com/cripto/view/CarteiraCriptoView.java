package com.cripto.view;

import com.cripto.controller.CarteiraCriptoController;
import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraDAO;
import com.cripto.model.Carteira;
import com.cripto.model.CarteiraCripto;
import com.cripto.model.Cliente;

import java.util.Locale;
import java.util.Scanner;

public class CarteiraCriptoView {
    private final Scanner scanner;
    private final CarteiraCriptoController carteiraCriptoController;
    private final ClienteController clienteController;
    private final CarteiraDAO carteiraDAO;

    public CarteiraCriptoView(CarteiraCriptoController carteiraCriptoController, ClienteController clienteController, CarteiraDAO carteiraDAO) {
        this.clienteController = clienteController;
        this.carteiraDAO = carteiraDAO;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.carteiraCriptoController = carteiraCriptoController;
    }

    public void mostrarCarteiraCripto() {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());

        System.out.println("=========================================================================");
        System.out.printf(" | %-30s | %-30s |\n", "Saldo Conta Corrente:", String.format("%.2f", carteira.getSaldoContaCorrente()));
        System.out.printf(" | %-30s | %-30s |\n", "Nome do Cliente:", cliente.getNome());
        System.out.println("=========================================================================");
        System.out.println("1 - COMPRAR CRIPTO      2 - EXIBIR PORTIFOLIO     3 - SAIR");
        int opcao = scanner.nextInt();

        if (opcao == 1){
            System.out.println(comprarCripto());
        } else if (opcao == 2) {
            mostrarPortifolioCripto();
        }
    }

    public String comprarCripto() {
        System.out.println("1 - COMPRAR BITCOIN     2 - COMPRAR ETHEREUM     3 - COMPRAR SOLANA");
        System.out.print("opcao: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Valor pra comprar: ");
        double valor = scanner.nextDouble();

        if (carteiraCriptoController.comprarCripto(opcao, valor)) {
            return "Compra bem sucedida";
        }
        return "Nao foi possivel comprar a criptomoeda";
    }

    public void mostrarPortifolioCripto(){
        Cliente cliente = clienteController.pegarClienteLogado();
        CarteiraCripto carteiraCripto = carteiraCriptoController.pegarCarteiraCripto(cliente.getId_cliente());

        if (carteiraCripto == null) {
            System.out.println("Erro: Carteira Cripto não encontrada!");
            return;
        }

        System.out.println("=========================================================================");
        System.out.printf(" | %-30s | %-7.2f |\n", "Saldo em BRL:", carteiraCripto.getSaldoBRL());
        System.out.printf(" | %-30s | %-7.6f | R$ %-5.2f |\n", "Quantidade BTC:", carteiraCripto.conversao(1, carteiraCripto.getSaldoBTC()), carteiraCripto.getSaldoBTC());
        System.out.printf(" | %-30s | %-7.6f | R$ %-5.2f |\n", "Quantidade ETH:", carteiraCripto.conversao(2, carteiraCripto.getSaldoETH()), carteiraCripto.getSaldoETH());
        System.out.printf(" | %-30s | %-7.6f | R$ %-5.2f |\n", "Quantidade SOL:", carteiraCripto.conversao(3, carteiraCripto.getSaldoSOl()), carteiraCripto.getSaldoSOl());
        System.out.printf(" | %-30s | %-7.6f |\n", "Quantidade AGICOIN:", carteiraCripto.getSaldoAGICOIN());
        System.out.println("=========================================================================");
    }
}
