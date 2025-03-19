package com.cripto.view;

import com.cripto.controller.CarteiraCriptoController;
import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraDAO;
import com.cripto.model.Carteira;
import com.cripto.model.Cliente;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class CarteiraView {
    private final Scanner scanner;
    private final ClienteController controller;
    private final CarteiraDAO carteiraDAO;
    private final CarteiraCriptoController carteiraCriptoController;
    private QuestionarioView questionarioView = new QuestionarioView();
    private PagamentosView pagamentosView;
    private final CarteiraCriptoView carteiraCriptoView;

    public CarteiraView(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController, CarteiraCriptoView carteiraCriptoView) {
        this.carteiraDAO = carteiraDAO;
        this.carteiraCriptoController = carteiraCriptoController;
        this.carteiraCriptoView = carteiraCriptoView;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.controller = controller;
        this.pagamentosView = new PagamentosView(controller);
    }

    public void telaCarteiraContaCorrente() {
        int resultado;
        Cliente cliente = controller.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());

        System.out.println("=".repeat(73));
        System.out.printf(" | %-30s | %-30s |\n", "Saldo Conta Corrente:", String.format("%.2f", carteira.getSaldoContaCorrente()));
        System.out.printf(" | %-30s | %-30s |\n", "Nome do Cliente:", cliente.getNome());
        System.out.println("=".repeat(73));

        if (cliente.getStatus().equals("ativo")) {
            System.out.println("1 - Transacao     2 - Entrar Cripto     3 - Sair");
        } else {
            System.out.println("1 - Transacao     2 - Ativar Cripto     3 - Sair");
        }

        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) {
            pagamentosView.telaPagamento();
        } else if (opcao == 2 && cliente.getStatus().equals("desativado")) {
            resultado = questionarioView.iniciarQuestionario();
            if (resultado <= 6) {
                questionarioView.exibirTutorial();
                carteiraCriptoController.ativarCarteiraCripto();
            } else if (resultado < 16) {
                System.out.println("Deseja ver o questionário?");
                System.out.println("1 - SIM");
                System.out.println("2 - NÃO");

                int escolha = lerOpcao(1, 2);
                if (escolha == 1) {
                    questionarioView.exibirTutorial();
                } else {
                    carteiraCriptoController.ativarCarteiraCripto();
                }
            } else {
                carteiraCriptoController.ativarCarteiraCripto();
            }
        } else {
            carteiraCriptoView.mostrarCarteiraCripto();
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
}
