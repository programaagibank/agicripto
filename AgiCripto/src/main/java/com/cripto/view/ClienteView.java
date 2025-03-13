package com.cripto.view;

import com.cripto.controller.ClienteController;

import java.util.Locale;
import java.util.Scanner;

public class ClienteView {
    private final Scanner scanner;
    private final ClienteController controller;

    public ClienteView(ClienteController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    public void escolhaMenu() {
        System.out.println("\n 1 - Login \n 2 - Cadastro \n 3 - Sair");
        System.out.print("Digite:");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) System.out.println(login());
        if (opcao == 2) System.out.println(pegarInfosCliente());
        if (opcao == 3) System.out.println("Saindo....");
    }

    public String pegarInfosCliente() {
        System.out.println("Digite seu nome completo: ");
        String nome = scanner.nextLine();

        System.out.println("Digite seu email: ");
        String email = scanner.nextLine();

        System.out.println("Digite sua senha: ");
        String senha = scanner.nextLine();

        System.out.println("Digite seu cpf (sem . e -): ");
        String cpf = scanner.nextLine();

        if (controller.cadastro(nome, email, senha, cpf)) {
            return "Cliente cadastrado com sucesso";
        }
        return "Cliente nao cadastrado!";
    }

    public String login() {
        System.out.print("\t\tDigite seu email: ");
        String email = scanner.nextLine();

        System.out.print("\t\tDigite sua senha: ");
        String senha = scanner.nextLine();

        if (controller.fazerLogin(email, senha)) {
            return "Logado com sucesso";
        }
        return "Email ou senha incorreto!";
     }
}
