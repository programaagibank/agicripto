package com.cripto.view;

import com.cripto.controller.ClienteController;
import com.cripto.model.Carteira;
import com.cripto.model.Cliente;

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
        System.out.print("\t".repeat(5) + "Agi - Cripto");
        System.out.println();
        System.out.println("=".repeat(50));
        System.out.println("1 - Login \n2 - Cadastro \n3 - Esqueceu senha \n4 - Sair");
        System.out.println("=".repeat(50));
        System.out.print("Digite: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) {
            Cliente clienteLogado = login();
            if (clienteLogado != null) {
                escolhaMenuLogado(clienteLogado);
            }
        } else if (opcao == 2) {
            System.out.println(pegarInfosCliente());
        } else if (opcao == 3) {
            System.out.println(esqueceuSenha());
        } else if (opcao == 4) {
            System.out.println("Saindo....");
        } else {
            System.out.println("Opção inválida!");
        }
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

    public Cliente login() {
        System.out.print("\t\tDigite seu email: ");
        String email = scanner.nextLine();

        Cliente cliente = controller.encontrarPeloEmail(email);
        if (cliente == null) {
            System.out.println("Email não encontrado!");
            return null;
            // TODO: FLUXO DE CADASTRO
        }

        System.out.print("\t\tDigite sua senha: ");
        String senha = scanner.nextLine();

        String senhaCriptografada = cliente.criptografarSenha(senha);
        if (senhaCriptografada.equals(cliente.getSenha())) {
            System.out.println("Logado com sucesso!");
            return cliente;
        }

        System.out.print("Senha incorreta. Deseja redefinir sua senha? (1 - SIM / 0 - NÃO): ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) {
            System.out.println("\t\tEmail: " + cliente.getEmail());

            System.out.print("\t\tDigite sua nova senha: ");
            String novaSenha = scanner.nextLine();

            System.out.print("\t\tConfirme sua nova senha: ");
            String confirmarSenha = scanner.nextLine();

            if (novaSenha.equals(confirmarSenha)) {
                controller.alterarSenha(cliente.getEmail(), novaSenha, confirmarSenha);
                System.out.println("Senha alterada com sucesso!");
            } else {
                System.out.println("As senhas não coincidem. Tente novamente.");
            }

        } else {
            System.out.print("\t\tDigite sua senha novamente: ");
            String senhaNovamente = scanner.nextLine();
            String senhaCriptografada2 = cliente.criptografarSenha(senhaNovamente);

            if (senhaCriptografada2.equals(cliente.getSenha())) {
                System.out.println("Logado com sucesso!");
                return cliente;
            } else {
                System.out.println("Senha incorreta novamente. Acesso negado.");
            }
        }
        return null;
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

    public void escolhaMenuLogado(Cliente clienteLogado) {
        System.out.printf("%sConta: %s", "\t".repeat(5), clienteLogado.getId_cliente());
        System.out.println();
        System.out.println("=".repeat(50));
        System.out.println("1 - Pix ");
        System.out.println("=".repeat(50));
        System.out.print("Digite:");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) menuPix(clienteLogado);
    }

    public void menuPix(Cliente clienteLogado) {
        System.out.println("=".repeat(50));
        controller.realizarPix(200, clienteLogado.getId_cliente(), 17);
        System.out.println("=".repeat(50));

    }
}
