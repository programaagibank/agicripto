package com.cripto.view;

import java.util.Scanner;

public class TelaView {
    private Scanner scanner;

    public TelaView() {
        this.scanner =  new Scanner(System.in);
    }

    public int escolha(String mensagem, int min, int max) {
        int opcao = -1;
        while (true) {
            try {
                System.out.println(mensagem);
                System.out.print("Escolha uma opção: ");
                opcao = Integer.parseInt(scanner.nextLine());

                if (opcao >= min && opcao <= max) {
                    return opcao;
                } else {
                    System.out.println("Opção inválida. Escolha entre " + min + " e " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
            }
        }
    }

    public void mostrarTelaInicial() {
        System.out.println("==== Bem-vindo à Aplicação ====");
        System.out.println("1 - Tela de Cadastro");
        System.out.println("2 - Tela de Consulta");
        System.out.println("3 - Sair");

        int opcao = escolha("Selecione uma opção:", 1, 3);
        switch (opcao) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                System.out.println("Saindo...");
                break;
        }
    }
}
