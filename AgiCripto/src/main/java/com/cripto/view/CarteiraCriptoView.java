package com.cripto.view;

import com.cripto.controller.AssinaturaController;
import com.cripto.controller.CarteiraCriptoController;
import com.cripto.controller.ClienteController;
import com.cripto.dao.CarteiraDAO;
import com.cripto.model.Assinatura;
import com.cripto.model.Carteira;
import com.cripto.model.CarteiraCripto;
import com.cripto.model.Cliente;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class CarteiraCriptoView {
    private final Scanner scanner;
    private final CarteiraCriptoController carteiraCriptoController;
    private final ClienteController clienteController;
    private final CarteiraDAO carteiraDAO;
    private final AssinaturaController assinaturaController;

    public CarteiraCriptoView(CarteiraCriptoController carteiraCriptoController, ClienteController clienteController, CarteiraDAO carteiraDAO, AssinaturaController assinaturaController) {
        this.clienteController = clienteController;
        this.carteiraDAO = carteiraDAO;
        this.assinaturaController = assinaturaController;
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.carteiraCriptoController = carteiraCriptoController;
    }

    public void mostrarCarteiraCripto() {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());

        System.out.println("=========================================================================");
        System.out.printf(" | %-30s | %-30s |\n", "Saldo Conta Corrente:", String.format("%.2f", carteira.getSaldoContaCorrente()));
        System.out.printf(" | %-30s | %-30s |\n", "Nome do Cliente:", cliente.getNome());
        System.out.println("=========================================================================");
        System.out.println("=========================================================================");
        System.out.printf(" | %-30s | %-30s |\n", "Saldo Conta Corrente:", String.format("%.2f", carteira.getSaldoContaCorrente()));
        System.out.printf(" | %-30s | %-30s |\n", "Nome do Cliente:", cliente.getNome());
        System.out.println("=========================================================================");
        System.out.println("""
        1 - COMPRAR CRIPTO
        2 - EXIBIR PORTIFOLIO
        3 - DESATIVAR CARTEIRA CRIPTO
        4 - TROCAR CRIPTO
        5 - VENDER CRIPTO
        6 - VER TUTORIAL
        7 - SAIR"""
        );
        System.out.println("DIGITE: ");
        try {
            int opcao = scanner.nextInt();

            if (opcao == 1){
                System.out.println(comprarCripto());
            } else if (opcao == 2) {
                mostrarPortifolioCripto();
            } else if (opcao == 3) {
                desativarCarteiraCripto();
            } else if (opcao == 4){
                trocarCripto();
            }else if (opcao == 5){
                System.out.println(venderCripto());
            }else if (opcao == 6) {
                CarteiraView carteiraView = null;
                carteiraView.exibirTutorial();
            }else {
                System.out.println("saindo...");
            }
        }catch (InputMismatchException e){
            System.out.println("Erro, voce digitou uma letra!");
        }
    }

    public String comprarCripto() {
        System.out.println("1 - COMPRAR BITCOIN     2 - COMPRAR ETHEREUM     3 - COMPRAR SOLANA");
        System.out.print("opcao: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Valor pra comprar: ");
        double valor = scanner.nextDouble();

        if (carteiraCriptoController.comprarCripto(opcao, valor)) {
            return "Compra bem sucedida";
        }
        return "Nao foi possivel comprar a criptomoeda";
    }

    public String venderCripto() {
        System.out.println("""
                1 - VENDER BITCOIN
                2 - VENDER ETHEREUM
                3 - VENDER SOLANA
                """);
        System.out.print("OPCAO: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Valor pra venda: ");
        double valor = scanner.nextDouble();

        if (carteiraCriptoController.venderCriptoMoeda(opcao, valor)) {
            return "Venda feita";
        }
        return "Não foi possivel concluir a venda";

    }

    public void mostrarPortifolioCripto(){
        Cliente cliente = clienteController.pegarClienteLogado();
        CarteiraCripto carteiraCripto = carteiraCriptoController.pegarCarteiraCripto(cliente.getId_cliente());

        if (carteiraCripto == null) {
            System.out.println("Erro: Carteira Cripto não encontrada!");
            return;
        }

        System.out.println("=========================================================================");
        System.out.printf(" | %-30s | %-7.2f |\n", "Saldo em BRL:", carteiraCripto.getSaldoBRL());
        System.out.printf(" | %-30s | %-7.6f | R$ %-5.2f |\n", "Quantidade BTC:", carteiraCripto.conversao(1, carteiraCripto.getSaldoBTC()), carteiraCripto.getSaldoBTC());
        System.out.printf(" | %-30s | %-7.6f | R$ %-5.2f |\n", "Quantidade ETH:", carteiraCripto.conversao(2, carteiraCripto.getSaldoETH()), carteiraCripto.getSaldoETH());
        System.out.printf(" | %-30s | %-7.6f | R$ %-5.2f |\n", "Quantidade SOL:", carteiraCripto.conversao(3, carteiraCripto.getSaldoSOl()), carteiraCripto.getSaldoSOl());
        System.out.printf(" | %-30s | %-7.6f |\n", "Quantidade AGICOIN:", carteiraCripto.getSaldoAGICOIN());
        System.out.println("=========================================================================");
    }

    public String trocarCripto() {
        System.out.println("Escolha a criptomoeda de origem:");
        System.out.println("1 - Bitcoin (BTC)");
        System.out.println("2 - Ethereum (ETH)");
        System.out.println("3 - Solana (SOL)");
        System.out.println("4 - Agicoin (AGI)");
        System.out.print("Digite a opção: ");

        int criptoOrigem = scanner.nextInt();

        System.out.println("Escolha a criptomoeda de destino:");
        System.out.println("1 - Bitcoin (BTC)");
        System.out.println("2 - Ethereum (ETH)");
        System.out.println("3 - Solana (SOL)");
        System.out.println("4 - Agicoin (AGI)");
        System.out.print("Digite a opção: ");

        int criptoDestino = scanner.nextInt();

        if (criptoOrigem == criptoDestino) {
            return "Você não pode trocar uma criptomoeda por ela mesma!";
        }

        System.out.print("Digite o valor a ser convertido: ");
        double valor = scanner.nextDouble();

        boolean sucesso = carteiraCriptoController.trocarCripto(criptoOrigem, criptoDestino, valor);

        return sucesso ? "Troca realizada com sucesso!" : "Não foi possível realizar a troca.";
    }

    public String desativarCarteiraCripto() {
        System.out.println("Certeza que gostaria de encerrar sua carteira cripto?" +
                "Todas as criptomoedas que voce possui sera convertida em real e coloca" +
                "na sua conta principal. Caso deseja desativar digite 1, para voltar digite 2");
        System.out.print("Opcao: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 1) {
            if (carteiraCriptoController.desativarCarteiraCripto()) {
                return "Desativado com sucesso";
            }
        } else {
            mostrarCarteiraCripto();
        }


        return "Nao foi possivel desativar";
    }

    public String ativarAssinatura() {
        Cliente cliente = clienteController.pegarClienteLogado();
        System.out.println(
                "1 - ASSINAR BITCOIN" +
                        "2 - ASSINAR ETHEREUM" +
                        "3 - ASSINAR SOLANA" +
                        "4 - DESATIVAR ASSINATURA" +
                        "5 - VER INFORMACOES"
        );
        System.out.print("opcao: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 4) {
            assinaturaController.desativar(cliente.getId_cliente());
            return "Assinatura cancelada com sucesso!";
        }
        if (opcao == 5) {
            mostrarInformacoesAssinatura();
        }

        System.out.println("Valor: ");
        double valor = scanner.nextInt();
        scanner.nextLine();

        assinaturaController.assinar(valor, opcao);
        carteiraCriptoController.comprarCripto(opcao, valor);

        return "Assinatura realizada!";
    }

    public Assinatura mostrarInformacoesAssinatura() {
        Cliente cliente = clienteController.pegarClienteLogado();
        return assinaturaController.pegarPeloId(cliente.getId_cliente());
    }
}
