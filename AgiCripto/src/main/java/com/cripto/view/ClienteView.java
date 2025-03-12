package com.cripto.view;

import java.util.Scanner;

public class ClienteView {
    private Scanner scanner;

    public ClienteView() {
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

    public boolean mostrarTelaInicial() {
        System.out.println("==== Bem-vindo à Aplicação ====");
        System.out.println("1 - Tela de Cadastro");
        System.out.println("2 - Tela de Login");
        System.out.println("3 - Sair");

        int opcao = escolha("Selecione uma opção:", 1, 3);
        switch (opcao) {
            case 1:
                break;
            case 2:
                mostrarTelaLogin();
                break;
            case 3:
                System.out.println("Saindo...");
                return true;
        }
        return false;
    }

    public void mostrarTelaLogin() {
        System.out.println("==== Bem-vindo à tela login ====");
        System.out.println("Digite seu email: ");
        String email = scanner.nextLine();
        System.out.println("Digite sua senha: ");
        String senha = scanner.nextLine();


    }

    public void clearScreen() {
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }





}


