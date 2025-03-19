package com.cripto.view;

import com.cripto.controller.ClienteController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PagamentosView {

    private final Scanner scanner = new Scanner(System.in);
    private final ClienteController clienteController;

    // Modificando o construtor para aceitar o ClienteController
    public PagamentosView(ClienteController clienteController) {
        this.clienteController = clienteController;
    }

    public void telaPagamento() {
        System.out.println("\t".repeat(7) + "Pagamentos" + "\t".repeat(7));
        System.out.println("=".repeat(73));
        System.out.println("Selecione a opção que deseja:");
        System.out.println("1 - Compra");
        System.out.println("2 - Sair");
        System.out.println("=".repeat(73));
        int escolha = lerOpcao(1, 2);

        if (escolha == 1) {
            telaCompra();
        }
    }

    private int lerOpcao(int min, int max) {
        int opcao = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao >= min && opcao <= max) {
                    entradaValida = true;
                } else {
                    System.out.println("Opção inválida. Digite um número entre " + min + " e " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.nextLine();
            }
        }

        return opcao;
    }

    // Tela de compra
    private void telaCompra() {
        System.out.println("\t".repeat(7) + "Comprar" + "\t".repeat(7));
        System.out.println("=".repeat(73));
        System.out.println("Digite o valor: ");
        double valor = scanner.nextDouble();

        boolean sucesso = clienteController.comprar(valor); // Chama o método comprar do controller

        if (sucesso) {
            System.out.println("Compra realizada com sucesso!");
        } else {
            System.out.println("Erro ao realizar a compra.");
        }
    }
}
