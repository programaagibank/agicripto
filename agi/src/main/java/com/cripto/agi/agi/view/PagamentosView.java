//package com.cripto.agi.agi.view;
//
//import com.cripto.agi.agi.controller.CarteiraCriptoController;
//import com.cripto.agi.agi.controller.ClienteController;
//import com.cripto.agi.agi.dao.CarteiraCriptoDAO;
//import com.cripto.agi.agi.model.Cliente;
//import com.cripto.agi.agi.model.Transacao;
//
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Scanner;
//
//public class PagamentosView {
//
//    private final Scanner scanner = new Scanner(System.in);
//    private final ClienteController clienteController;
//    private final CarteiraCriptoDAO carteiraCriptoDAO;
//    private final CarteiraCriptoController carteiraCriptoController;
//    private Cliente cliente;
//
//    public PagamentosView(ClienteController clienteController, CarteiraCriptoDAO carteiraCriptoDAO, CarteiraCriptoController carteiraCriptoController) {
//        this.clienteController = clienteController;
//        this.carteiraCriptoDAO = carteiraCriptoDAO;
//        this.carteiraCriptoController = carteiraCriptoController;
//    }
//
//    public void telaPagamento() {
//        System.out.println("\t".repeat(7) + "Pagamentos" + "\t".repeat(7));
//        System.out.println("=".repeat(73));
//        System.out.println("Selecione a opção que deseja:");
//        System.out.println("1 - Compra");
//        System.out.println("2 - Historico de Transacoes");
//        System.out.println("3 - Sair");
//        System.out.println("=".repeat(73));
//        int escolha = lerOpcao(1, 3);
//
//        if (escolha == 1) {
//            telaCompra();
//        } else if (escolha == 2) {
//            historicoTransacoes();
//        } else if (escolha == 3) {
//            System.out.println("Saindo...");
//        }
//    }
//
//    private int lerOpcao(int min, int max) {
//        int opcao = 0;
//        boolean entradaValida = false;
//
//        while (!entradaValida) {
//            try {
//                System.out.print("Escolha uma opção: ");
//                opcao = scanner.nextInt();
//                scanner.nextLine();
//
//                if (opcao >= min && opcao <= max) {
//                    entradaValida = true;
//                } else {
//                    System.out.println("Opção inválida. Digite um número entre " + min + " e " + max + ".");
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("Entrada inválida! Digite apenas números.");
//                scanner.nextLine();
//            }
//        }
//
//        return opcao;
//    }
//
//    private void aplicarCashback(double valor, String tipoPagamento, int idCliente) {
//        double taxaCashback = tipoPagamento.equals("crédito") ? 0.01 : 0.005;
//        double valorCashback = taxaCashback * valor;
//
//        boolean cashbackSucesso = carteiraCriptoController.realizarCashback(valorCashback, idCliente);
//        if (cashbackSucesso) {
//            System.out.println("Cashback aplicado com sucesso no valor de " + valorCashback + " agicoin!");
//        } else {
//            System.out.println("Erro ao aplicar cashback.");
//        }
//    }
//
//
//    private void telaCompra() {
//        System.out.println("\t".repeat(7) + "Comprar" + "\t".repeat(7));
//        System.out.println("=".repeat(73));
//
//        System.out.println("Escolha a forma de pagamento:");
//        System.out.println("1 - Débito");
//        System.out.println("2 - Crédito");
//
//        int opcao = 0;
//        String tipoPagamento = "";
//        try {
//            opcao = scanner.nextInt();
//            if (opcao == 1) {
//                tipoPagamento = "débito";
//            } else if (opcao == 2) {
//                tipoPagamento = "crédito";
//            } else {
//                System.out.println("Opção inválida! Operação cancelada.");
//                return;
//            }
//        } catch (InputMismatchException e) {
//            System.out.println("Entrada inválida! Digite 1 para débito ou 2 para crédito.");
//            scanner.nextLine();
//            return;
//        }
//
//        double valor = obterValorCompra();
//        if (valor == 0) {
//            System.out.println("Encerrando a operação.");
//            return;
//        }
//
//        cliente = clienteController.pegarClienteLogado();
//        boolean sucesso = clienteController.comprar(valor);
//
//        if (sucesso) {
//            System.out.println("Compra no " + tipoPagamento + " realizada com sucesso!");
//            if (cliente.getStatus().equals("ativo")) {
//                aplicarCashback(valor, tipoPagamento, cliente.getId_cliente());
//            } else {
//                double cashbackPrevisto = (tipoPagamento.equals("crédito") ? 0.01 : 0.005) * valor;
//                System.out.println("Caso queira receber cashback, ative sua conta cripto! Cashback que teria recebido nesta transação: " + cashbackPrevisto + " agicoin.");
//            }
//        } else {
//            System.out.println("Erro ao realizar a compra.");
//        }
//    }
//
//    public void historicoTransacoes() {
//        List<Transacao> transacaos = clienteController.listarTransacoes();
//        System.out.println(transacaos);
//    }
//
//    private double obterValorCompra() {
//        double valor = 0;
//        try {
//            System.out.println("Digite o valor da compra: ");
//            valor = scanner.nextDouble();
//            if (valor < 1) {
//                System.out.println("Erro, não é possível realizar a compra com esse valor!");
//                return 0;
//            }
//        } catch (InputMismatchException e) {
//            System.out.println("Você digitou uma entrada inválida! Digite apenas números.");
//            scanner.nextLine();
//            return 0;
//        }
//        return valor;
//    }
//
//}
