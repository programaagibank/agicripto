package com.cripto.view;

import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraDAO;
import com.cripto.model.Carteira;
import com.cripto.model.Cliente;

import java.util.Locale;
import java.util.Scanner;

public class CarteiraView {
    private final Scanner scanner;
    private final ClienteController controller;
    private final CarteiraDAO carteiraDAO;

    public CarteiraView(ClienteController controller, CarteiraDAO carteiraDAO) {
        this.carteiraDAO = carteiraDAO;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.controller = controller;
    }

    public void telaCarteiraContaCorrente() {
        Cliente cliente = controller.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());

        System.out.println("=========================================================================");
        System.out.printf(" | %-30s | %-30s |\n", "Saldo Conta Corrente:", String.format("%.2f", carteira.getSaldoContaCorrente()));
        System.out.printf(" | %-30s | %-30s |\n", "Nome do Cliente:", cliente.getNome());
        System.out.println("=========================================================================");

        System.out.println("1 - Transacco");

        if (cliente.getStatus().equalsIgnoreCase("ATIVO")) {
            System.out.println("2 - Entrar na Carteira Cripto");
        } else {
            System.out.println("2 - Ativar Cripto");
        }

        System.out.println("3 - Sair");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) {
            System.out.println(comprar());
        } else if (opcao == 2) {
            if (cliente.getStatus().equalsIgnoreCase("ATIVO")) {
                entrarNaCarteiraCripto();
            } else {
                System.out.println(ativar());
            }
        } else {
            System.out.println("Saindo...");
        }
    }

    public String comprar() {
        System.out.print("\t\tDigite o valor da compra: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (controller.comprar(valor)) {
            return "Compra bem sucedida.";
        }
        return "Não conseguiu comprar!";
    }

    public String ativar() {
        if (controller.ativarContaCripto()) {
            return "Conta cripto ativada com sucesso!";
        }
        return "Falha ao ativar conta cripto.";
    }

    public void entrarNaCarteiraCripto() {
        System.out.println("Bem-vindo a sua Carteira Cripto!");
    }
}
