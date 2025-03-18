package com.cripto.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class QuestionarioView {

    private Scanner scanner = new Scanner(System.in);

    public void iniciarQuestionario() {
        System.out.println("\t".repeat(7) + "Questinário Cripto" + "\t".repeat(7));
        System.out.println("=".repeat(73));
        System.out.println("Gostaria de testar seus conhecimentos sobre o mercado de criptomoedas?");
        System.out.println("1 - SIM");
        System.out.println("2 - NÃO");
        System.out.println("=".repeat(73));
        int escolha = lerOpcao(1, 2);

        if (escolha == 2) {
            System.out.println("Você será redirecionado para sua carteira digital...");
        } else if (escolha == 1) {
            int pontuacao = iniciarPerguntas();
            avaliarPontuacao(pontuacao);
        }
    }

    private int iniciarPerguntas() {
        int pontuacao = 0;

        pontuacao += fazerPergunta("O que você conhece sobre o mercado de criptomoedas?");
        pontuacao += fazerPergunta("Você sabe como funciona uma carteira digital?");
        pontuacao += fazerPergunta("Como funcionam as transações com criptomoedas?");
        pontuacao += fazerPergunta("Como funciona a valorização de criptomoedas?");

        return pontuacao;
    }

    private int fazerPergunta(String pergunta) {
        System.out.println("\t".repeat(3) + pergunta + "\t".repeat(3));
        System.out.println("=".repeat(73));
        System.out.println("1 - Nenhum conhecimento");
        System.out.println("2 - Pouco conhecimento");
        System.out.println("3 - Médio conhecimento");
        System.out.println("4 - Tenho conhecimento");

        return lerOpcao(1, 4);
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
                    entradaValida = true; // Entrada correta, sai do loop
                } else {
                    System.out.println("Opção inválida. Digite um número entre " + min + " e " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
                scanner.nextLine(); // Consumir entrada inválida
            }
        }

        return opcao;
    }

    private void avaliarPontuacao(int pontuacao) {
        System.out.println("\nSua pontuação: " + pontuacao);

        if (pontuacao == 16) {
            System.out.println("Parabéns! Você será direcionado para a carteira digital.");
        } else {
            System.out.println("Para mais informações, entre em contato com nosso suporte: (11) 99999-9999.");
        }
    }

    public static void main(String[] args) {
        QuestionarioView questionario = new QuestionarioView();
        questionario.iniciarQuestionario();
    }
}
