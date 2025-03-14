package com.cripto.view;

import com.cripto.controller.ClienteController;
import com.cripto.model.Cliente;

import java.sql.SQLOutput;
import java.util.Locale;
import java.util.Scanner;
import java.util.SortedMap;

public class ClienteView {
    private final Scanner scanner;
    private final ClienteController controller;

    public ClienteView(ClienteController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
    }

    public void escolhaMenu() {
        System.out.println("\n 1 - Login \n 2 - Cadastro \n 3 - Esqueceu senha \n 4 - Sair");
        System.out.print("Digite:");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) System.out.println(login());
        if (opcao == 2) System.out.println(pegarInfosCliente());
        if (opcao == 3) System.out.println(esqueceuSenha());
        if (opcao == 4) System.out.println("Saindo....");
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
        Cliente cliente = controller.encontrarPeloEmail(email);

        System.out.print("\t\tDigite sua senha: ");
        String senha = scanner.nextLine();

        if (!cliente.criptografarSenha(senha).equals(cliente.getSenha())) {
            System.out.print("Vejo que esqueceu a senha, gostaria de trocar (1- SIM / 0- NAO): ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            if (opcao == 1) {
                System.out.println("\t\tEmail: " + cliente.getEmail());

                System.out.print("\t\tDigite sua nova senha: ");
                String novaSenha = scanner.nextLine();

                System.out.print("\t\tConfirme sua nova senha: ");
                String confirmarSenha = scanner.nextLine();

                controller.alterarSenha(cliente.getEmail(), novaSenha, confirmarSenha);
                return "Senha alterada com sucesso!";
            } else {
                System.out.println();
                System.out.print("\t\tDigite sua senha novamente: ");
                String senhaNovamente = scanner.nextLine();

                if (controller.fazerLogin(email, senhaNovamente)) {
                    return "Logado com sucesso";
                }
            }
        }

        if (controller.fazerLogin(email, senha)) {
            return "Logado com sucesso";
        }
        return "Email ou senha incorreto!";
    }

    public String esqueceuSenha() {
        System.out.print("\t\tDigite seu email: ");
        String email = scanner.nextLine();

        System.out.print("\t\tDigite sua nova senha: ");
        String novaSenha = scanner.nextLine();

        System.out.print("\t\tConfirme sua senha: ");
        String confirmarSenha = scanner.nextLine();

        if (controller.alterarSenha(email, novaSenha, confirmarSenha)) {
            return "Senha alterada com sucesso!";
        }
        return "Nao foi possivel alterar sua senha";
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
