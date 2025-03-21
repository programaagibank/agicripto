package com.cripto.view;

import com.cripto.controller.CarteiraCriptoController;
import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraCriptoDAO;
import com.cripto.model.Cliente;
import com.cripto.model.Transacao;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PagamentosView {

    private final Scanner scanner = new Scanner(System.in);
    private final ClienteController clienteController;
    private final CarteiraCriptoDAO carteiraCriptoDAO;
    private final CarteiraCriptoController carteiraCriptoController;
    private Cliente cliente;

    public PagamentosView(ClienteController clienteController, CarteiraCriptoDAO carteiraCriptoDAO, CarteiraCriptoController carteiraCriptoController) {
        this.clienteController = clienteController;
        this.carteiraCriptoDAO = carteiraCriptoDAO;
        this.carteiraCriptoController = carteiraCriptoController;
    }

    public void telaPagamento() {
        System.out.println("\t".repeat(7) + "Pagamentos" + "\t".repeat(7));
        System.out.println("=".repeat(73));
        System.out.println("Selecione a opção que deseja:");
        System.out.println("1 - Compra");
        System.out.println("2 - Historico de Transacoes");
        System.out.println("3 - Sair");
        System.out.println("=".repeat(73));
        int escolha = lerOpcao(1, 3);

        if (escolha == 1) {
            telaCompra();
        } else if (escolha == 2) {
            historicoTransacoes();
        } else if (escolha == 3) {
            System.out.println("Saindo...");
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

    private void telaCompra() {
        System.out.println("\t".repeat(7) + "Comprar" + "\t".repeat(7));
        System.out.println("=".repeat(73));
        System.out.println("Digite o valor: ");
        double valor = scanner.nextDouble();
        cliente = clienteController.pegarClienteLogado();
        boolean sucesso = clienteController.comprar(valor);

        if (sucesso) {
            if (cliente.getStatus().equals("ativo")) {
                System.out.println("Compra realizada com sucesso!");
                boolean cashbackSucesso = carteiraCriptoController.realizarCashback(valor, cliente.getId_cliente());
                if (cashbackSucesso) {
                    System.out.println("Cashback aplicado com sucesso no valor de " + 0.005 * valor + "!");
                } else {
                    System.out.println("Erro ao aplicar cashback.");
                }
            }
            else
                System.out.println("Compra realizada com sucesso! Caso queira receber cashback, " +
                        "ative sua conta cripto! Cashback que teria recebido nesta transação:" +
                        valor * 0.005 + " agicoin.");
        } else {
            System.out.println("Erro ao realizar a compra.");
        }
    }

    public void historicoTransacoes() {
        List<Transacao> transacaos = clienteController.listarTransacoes();
        System.out.println(transacaos);
    }
}
