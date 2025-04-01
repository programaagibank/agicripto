//package com.cripto.agi.agi.view;
//
//import com.cripto.agi.agi.controller.CarteiraCriptoController;
//import com.cripto.agi.agi.controller.ClienteController;
//import com.cripto.agi.agi.dao.CarteiraCriptoDAO;
//import com.cripto.agi.agi.dao.CarteiraDAO;
//import com.cripto.agi.agi.model.Carteira;
//import com.cripto.agi.agi.model.Cliente;
//import com.cripto.agi.agi.model.Transacao;
//
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Locale;
//import java.util.Scanner;
//
//public class CarteiraView {
//    private final Scanner scanner;
//    private final ClienteController controller;
//    private final CarteiraDAO carteiraDAO;
//    private final CarteiraCriptoController carteiraCriptoController;
//    private QuestionarioView questionarioView = new QuestionarioView();
//    private PagamentosView pagamentosView;
//    private final CarteiraCriptoView carteiraCriptoView;
//    private final CarteiraCriptoDAO carteiraCriptoDAO;
//
//    public CarteiraView(ClienteController controller, CarteiraDAO carteiraDAO, CarteiraCriptoController carteiraCriptoController, CarteiraCriptoView carteiraCriptoView, CarteiraCriptoDAO carteiraCriptoDAO) {
//        this.carteiraDAO = carteiraDAO;
//        this.carteiraCriptoController = carteiraCriptoController;
//        this.carteiraCriptoView = carteiraCriptoView;
//        this.carteiraCriptoDAO = carteiraCriptoDAO;
//        this.scanner = new Scanner(System.in).useLocale(Locale.US);
//        this.controller = controller;
//        this.pagamentosView = new PagamentosView(controller, carteiraCriptoDAO, carteiraCriptoController);
//    }
//
//    public void telaCarteiraContaCorrente() {
//        int resultado;
//        Cliente cliente = controller.pegarClienteLogado();
//        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
//
//        System.out.println("=".repeat(73));
//        System.out.printf(" | %-30s | %-30s |\n", "Saldo Conta Corrente:", String.format("%.2f", carteira.getSaldoContaCorrente()));
//        System.out.printf(" | %-30s | %-30s |\n", "Nome do Cliente:", cliente.getNome());
//        System.out.println("=".repeat(73));
//
//        if (cliente.getStatus().equals("ativo")) {
//            System.out.println("1 - Transacao     2 - Entrar Cripto     3 - Sair");
//        } else {
//            System.out.println("1 - Transacao     2 - Ativar Cripto     3 - Sair");
//        }
//
//        System.out.println("DIGITE: ");
//
//        try {
//            int opcao = scanner.nextInt();
//            scanner.nextLine();
//
//            if (opcao == 1) {
//                pagamentosView.telaPagamento();
//            } else if (opcao == 2 && cliente.getStatus().equals("desativado")) {
//                resultado = questionarioView.iniciarQuestionario();
//                if (resultado <= 6) {
//                    questionarioView.exibirTutorial();
//                    carteiraCriptoController.ativarCarteiraCripto();
//                } else if (resultado < 16) {
//                    System.out.println("Deseja ver o questionário?");
//                    System.out.println("1 - SIM");
//                    System.out.println("2 - NÃO");
//
//                    int escolha = lerOpcao(1, 2);
//                    if (escolha == 1) {
//                        questionarioView.exibirTutorial();
//                    } else {
//                        carteiraCriptoController.ativarCarteiraCripto();
//                    }
//                } else {
//                    carteiraCriptoController.ativarCarteiraCripto();
//                }
//            } else if (opcao == 2 && cliente.getStatus().equals("ativo")){
//              carteiraCriptoView.mostrarCarteiraCripto();
//            } else if (opcao == 3) {
//                System.out.println("Saindo do sistema...");
//            } else {
//                System.out.println("Erro, opção não encontrada!");
//            }
//        } catch (InputMismatchException e){
//            System.out.println("Erro, voce digitou uma letra!");
//        }
//    }
//
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
//        return opcao;
//    }
//
//    public void exibirTutorial(){
//        System.out.println("========================================================================================================================================");
//
//        System.out.println("----- Guia sobre funcionamento das Criptomoedas -----\n");
//
//        System.out.println("""
//                Criptomoedas sao moedas digitais descentralizadas que utilizam criptografia para garantir transacoes
//                seguras e controlar a criacao de novas unidades
//                """);
//        System.out.println("Diferente das moedas tradicionais como o real ou o dolar as criptomoedas nao sao controladas por um governo ou banco central\n");
//
//        System.out.println("Como Funcionam as Criptomoedas?");
//        System.out.println("As criptomoedas operam em uma tecnologia chamada blockchain que e um livro-razao digital descentralizado e imutavel");
//        System.out.println("As transacoes sao verificadas por uma rede de computadores chamada nos que garantem a seguranca e autenticidade das operacoes\n");
//
//        System.out.println("Criptomoedas que vamos ter em nossa operacao?");
//        System.out.println("1 - Bitcoin BTC A primeira e mais conhecida criptomoeda criada por Satoshi Nakamoto em 2009");
//        System.out.println("2 - Ethereum ETH Conhecida por sua capacidade de suportar contratos inteligentes e aplicativos descentralizados DApps");
//        System.out.println("3 - Solana SOL Destaca-se por sua escalabilidade e rapidez nas transacoes\n");
//
//        System.out.println("Vantagens das Criptomoedas:");
//        System.out.println("1 - Descentralizacao Operam sem um banco ou governo reduzindo manipulacao e censura");
//        System.out.println("2 - Seguranca Tecnologia blockchain protege contra fraudes e roubo de identidade");
//        System.out.println("3 - Transparencia Todas as transacoes sao publicas e auditaveis");
//        System.out.println("4 - Acessibilidade Global Permitem transacoes internacionais sem intermediarios");
//        System.out.println("5 - Baixas Taxas de Transacao Menores em comparacao com bancos tradicionais");
//        System.out.println("6 - Velocidade nas Transacoes Confirmacao rapida mais eficiente que bancos");
//        System.out.println("7 - Privacidade Dependendo da criptomoeda informacoes do usuario sao protegidas");
//        System.out.println("8 - Oportunidades de Investimento Possibilidade de valorizacao rapida\n");
//
//        System.out.println("Riscos e Desvantagens:");
//        System.out.println("- Volatilidade Os precos podem flutuar significativamente");
//        System.out.println("- Risco de Hackers Exchanges e carteiras digitais podem ser alvo de ataques");
//        System.out.println("- Regulacao Algumas criptomoedas sao restritas em certos paises");
//        System.out.println("- Perda de Acesso Se perder a chave privada pode perder os fundos permanentemente\n");
//
//        System.out.println("Conclusao:");
//        System.out.println("As criptomoedas representam uma revolucao financeira oferecendo maior autonomia seguranca e acessibilidade");
//        System.out.println("No entanto e fundamental estudar e compreender os riscos antes de investir ou utilizar ativos digitais");
//        System.out.println("""
//                Se deseja comecar no mundo das criptomoedas pesquise bem sobre os ativos utilize carteiras seguras
//                e nunca invista mais do que pode perder
//                """);
//
//        System.out.println("========================================================================================================================================");
//
//    }
//}
