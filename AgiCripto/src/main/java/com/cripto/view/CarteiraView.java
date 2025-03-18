package com.cripto.view;

import com.cripto.controller.CarteiraCriptoController;
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
    private final CarteiraCriptoController carteiraCriptoController;

    public CarteiraView(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController) {
        this.carteiraDAO = carteiraDAO;
        this.carteiraCriptoController = carteiraCriptoController;
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

        System.out.println("1 - Transacao     2 - Ativar Cripto     3 - Sair");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) {
            System.out.println(comprar());
        } else if (opcao == 2) {
            System.out.println(carteiraCriptoController.ativarCarteiraCripto());
        } else {
            System.out.println("Saindo...");
        }
    }

    public String comprar() {
        System.out.print("\t\tDigite o valor da compra: ");
        double valor = scanner.nextInt();
        scanner.nextLine();

        if (controller.comprar(valor)) {
            return "Compra bem sucedida.";
        }
        return "Nao conseguiu comprar!";
    }
}
