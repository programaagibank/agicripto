package com.cripto.controller;

import com.cripto.dao.CarteiraCriptoDAO;
import com.cripto.dao.CarteiraDAO;
import com.cripto.dao.ClienteDAO;
import com.cripto.dao.TransacaoDAO;
import com.cripto.model.Carteira;
import com.cripto.model.CarteiraCripto;
import com.cripto.model.Cliente;
import com.cripto.model.Transacao;

import java.time.LocalDateTime;

public class CarteiraCriptoController {
    private final ClienteController clienteController;
    private final CarteiraCriptoDAO carteiraCriptoDAO;
    private final CarteiraDAO carteiraDAO;
    private final ClienteDAO clienteDAO;
    private final TransacaoDAO transacaoDAO;

    public CarteiraCriptoController(ClienteController clienteController, CarteiraCriptoDAO carteiraCriptoDAO, CarteiraDAO carteiraDAO, ClienteDAO clienteDAO, TransacaoDAO transacaoDAO) {
        this.clienteController = clienteController;
        this.carteiraCriptoDAO = carteiraCriptoDAO;
        this.carteiraDAO = carteiraDAO;
        this.clienteDAO = clienteDAO;
        this.transacaoDAO = transacaoDAO;
    }

    public boolean ativarCarteiraCripto() {
        Cliente cliente = clienteController.pegarClienteLogado();
        if (cliente == null) return false;

        CarteiraCripto carteiraCripto = new CarteiraCripto(
                cliente.getId_cliente(),
                0.00,
                0.00,
                0.00,
                0.00,
                0.00
        );

        if (carteiraCriptoDAO.criarCarteiraCripto(carteiraCripto)) {
            clienteDAO.ativarConta(cliente.getId_cliente());
            return true;
        }
        return false;
    }

    public boolean comprarCripto(Integer opcao, Double valor) {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        if (carteiraCripto == null) return false;
        if (carteira == null) return false;

        double novoValor;
        int idCripto;
        if (opcao == 1) {
            novoValor = carteiraCripto.getSaldoBTC() + valor;
            idCripto = 1;
        } else if (opcao == 2) {
            novoValor = carteiraCripto.getSaldoETH() + valor;
            idCripto = 2;
        } else if (opcao == 3) {
            novoValor = carteiraCripto.getSaldoSOl() + valor;
            idCripto = 3;
        } else {
            return false;
        }

        if (carteira.getSaldoContaCorrente() < valor || carteira.getSaldoContaCorrente() == 0) return false;

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteira.getId_carteira(),
                cliente.getId_cliente(),
                idCripto,
                "PAGO",
                1,
                valor,
                data
        );

        carteiraDAO.atualizarSaldo((carteira.getSaldoContaCorrente() - valor), carteira.getId_carteira());
        transacaoDAO.comprar(transacao);

        double saldoBRl = carteiraCripto.getSaldoSOl() + carteiraCripto.getSaldoETH() + carteiraCripto.getSaldoBTC() + valor;
        carteiraCriptoDAO.atualizarSaldoBrl(saldoBRl, cliente.getId_cliente());

        return carteiraCriptoDAO.comprarCriptomoedas(opcao, novoValor, cliente.getId_cliente());
    }

    public CarteiraCripto pegarCarteiraCripto(Integer id) {
        return carteiraCriptoDAO.acharPeloIdCliente(id);
    }

    public boolean realizarCashback(double valor, int id) {
        try {
            carteiraCriptoDAO.cashback(valor, id);
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao processar cashback: " + e.getMessage());
            return false;
        }
    }


    public boolean desativarCarteiraCripto() {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        if (clienteDAO.desativarCarteira(cliente.getId_cliente())) {
            carteiraDAO.atualizarSaldo((carteiraCripto.getSaldoBRL() + carteira.getSaldoContaCorrente()), carteira.getId_carteira());
            carteiraCriptoDAO.atualizarSaldoBrl(0.0, cliente.getId_cliente());
            carteiraCriptoDAO.excluirCarteiraCripto(cliente.getId_cliente());

            return true;
        }
        return false;
    }

    public boolean trocarCripto(int criptoOrigem, int criptoDestino, double valor) {
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        if (carteiraCripto == null) return false;

        double saldoAtual;
        if (criptoOrigem == 1) saldoAtual = carteiraCripto.getSaldoBTC();
        else if (criptoOrigem == 2) saldoAtual = carteiraCripto.getSaldoETH();
        else if (criptoOrigem == 3) saldoAtual = carteiraCripto.getSaldoSOl();
        else if (criptoOrigem == 4) saldoAtual = carteiraCripto.getSaldoAGICOIN();
        else return false;

        if (saldoAtual < valor) return false;

        double valorConvertido = carteiraCripto.conversao(criptoDestino, valor);

        carteiraCriptoDAO.atualizarSaldoCripto(cliente.getId_cliente(), criptoOrigem, saldoAtual - valor);
        carteiraCriptoDAO.atualizarSaldoCripto(cliente.getId_cliente(), criptoDestino, (valor + saldoAtual));

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteira.getId_carteira(),
                cliente.getId_cliente(),
                 criptoDestino,
                "PAGO",
                2,
                valor,
                data
        );
        transacaoDAO.comprar(transacao);

        return true;
    }

    public boolean venderCriptoMoeda(int opcao, double valor){
        Cliente cliente = clienteController.pegarClienteLogado();
        Carteira carteira = carteiraDAO.pegarCarteiraPeloClienteId(cliente.getId_cliente());
        CarteiraCripto carteiraCripto = carteiraCriptoDAO.acharPeloIdCliente(cliente.getId_cliente());

        if (carteiraCripto == null) return false;
        if (carteira == null) return false;

        int idCripto;
        double novoValor;

        if (opcao == 1){
            if (carteiraCripto.getSaldoBTC() < valor) return false;
            idCripto = 1;
            novoValor = carteiraCripto.getSaldoBTC() - valor;
            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente());
            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira());
        } else if (opcao == 2){
            if (carteiraCripto.getSaldoETH() < valor) return false;
            idCripto = 2;
            novoValor = carteiraCripto.getSaldoETH() - valor;
            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente());
            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira());
        } else if (opcao == 3){
            if (carteiraCripto.getSaldoSOl() < valor) return false;
            idCripto = 3;
            novoValor = carteiraCripto.getSaldoSOl() - valor;
            carteiraCriptoDAO.subtrairCripto(valor,opcao,carteiraCripto.getIdCliente()); // subtrair valor
            carteiraDAO.atualizarSaldo(carteira.getSaldoContaCorrente() + valor,carteira.getId_carteira()); // adc saldo na conta
        } else {
            return false;
        }

        LocalDateTime data = LocalDateTime.now();
        Transacao transacao = new Transacao(
                carteira.getId_carteira(),
                cliente.getId_cliente(),
                idCripto,
                "PAGO",
                3,
                valor,
                data
        );

        carteiraDAO.atualizarSaldo((carteira.getSaldoContaCorrente() + valor), carteira.getId_carteira());
        transacaoDAO.comprar(transacao);

        double saldoBRl = carteiraCripto.getSaldoSOl() + carteiraCripto.getSaldoETH() + carteiraCripto.getSaldoBTC() + valor;
        carteiraCriptoDAO.atualizarSaldoBrl(saldoBRl, cliente.getId_cliente());

        return carteiraCriptoDAO.venderCriptomoedas(opcao, novoValor, cliente.getId_cliente());
    }

    public void exibirTutorial(){
        System.out.println("========================================================================================================================================");

        System.out.println("----- Guia sobre funcionamento das Criptomoedas -----\n");

        System.out.println("""
                Criptomoedas sao moedas digitais descentralizadas que utilizam criptografia para garantir transacoes
                seguras e controlar a criacao de novas unidades
                """);
        System.out.println("Diferente das moedas tradicionais como o real ou o dolar as criptomoedas nao sao controladas por um governo ou banco central\n");

        System.out.println("Como Funcionam as Criptomoedas?");
        System.out.println("As criptomoedas operam em uma tecnologia chamada blockchain que e um livro-razao digital descentralizado e imutavel");
        System.out.println("As transacoes sao verificadas por uma rede de computadores chamada nos que garantem a seguranca e autenticidade das operacoes\n");

        System.out.println("Criptomoedas que vamos ter em nossa operacao?");
        System.out.println("1 - Bitcoin BTC A primeira e mais conhecida criptomoeda criada por Satoshi Nakamoto em 2009");
        System.out.println("2 - Ethereum ETH Conhecida por sua capacidade de suportar contratos inteligentes e aplicativos descentralizados DApps");
        System.out.println("3 - Solana SOL Destaca-se por sua escalabilidade e rapidez nas transacoes\n");

        System.out.println("Vantagens das Criptomoedas:");
        System.out.println("1 - Descentralizacao Operam sem um banco ou governo reduzindo manipulacao e censura");
        System.out.println("2 - Seguranca Tecnologia blockchain protege contra fraudes e roubo de identidade");
        System.out.println("3 - Transparencia Todas as transacoes sao publicas e auditaveis");
        System.out.println("4 - Acessibilidade Global Permitem transacoes internacionais sem intermediarios");
        System.out.println("5 - Baixas Taxas de Transacao Menores em comparacao com bancos tradicionais");
        System.out.println("6 - Velocidade nas Transacoes Confirmacao rapida mais eficiente que bancos");
        System.out.println("7 - Privacidade Dependendo da criptomoeda informacoes do usuario sao protegidas");
        System.out.println("8 - Oportunidades de Investimento Possibilidade de valorizacao rapida\n");

        System.out.println("Riscos e Desvantagens:");
        System.out.println("- Volatilidade Os precos podem flutuar significativamente");
        System.out.println("- Risco de Hackers Exchanges e carteiras digitais podem ser alvo de ataques");
        System.out.println("- Regulacao Algumas criptomoedas sao restritas em certos paises");
        System.out.println("- Perda de Acesso Se perder a chave privada pode perder os fundos permanentemente\n");

        System.out.println("Conclusao:");
        System.out.println("As criptomoedas representam uma revolucao financeira oferecendo maior autonomia seguranca e acessibilidade");
        System.out.println("No entanto e fundamental estudar e compreender os riscos antes de investir ou utilizar ativos digitais");
        System.out.println("""
                Se deseja comecar no mundo das criptomoedas pesquise bem sobre os ativos utilize carteiras seguras
                e nunca invista mais do que pode perder
                """);

        System.out.println("========================================================================================================================================");

    }
}
