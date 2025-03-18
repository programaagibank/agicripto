package com.cripto.view;

import com.cripto.controller.CarteiraCriptoController;
import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraDAO;
import com.cripto.model.Carteira;
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
        System.out.println(comprarCripto());
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

}
