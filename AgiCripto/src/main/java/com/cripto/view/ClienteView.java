package com.cripto.view;

import java.util.Scanner;

public class ClienteView {
    private final Scanner scanner;

    public ClienteView(Scanner scanner) {
        this.scanner = scanner;
    }

    public String solicitaNome() {
        System.out.print("Digite o seu nome: ");
        return scanner.nextLine().trim();
    }

    public String solicitaEmail() {
        System.out.print("Digite o seu email: ");
        return scanner.nextLine().trim();
    }

    public String solicitaSenha() {
        System.out.print("Crie sua senha: ");
        return scanner.nextLine().trim();
    }

    public String solicitaCpf() {
        System.out.print("Informe seu CPF: ");
        return scanner.nextLine().trim();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public String solicitaId() {
        System.out.print("Digite o ID do usuário a ser excluído: ");
        return scanner.nextLine();
    }

}
