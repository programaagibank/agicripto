package com.cripto.dao;

import com.cripto.model.Transacao;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    private final Connection conexao;

    public TransacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }


    public boolean comprar(Transacao transacao) {
        String sql = "INSERT INTO Transacao (id_carteira, id_cliente, id_cripto, status, id_tipo_transacao, valor, data) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1,transacao.getIdCarteira());
            ps.setInt(2, transacao.getIdCliente());
            ps.setInt(3, transacao.getIdCripto());
            ps.setString(4, transacao.getStatus());
            ps.setInt(5, transacao.getIdTipoTransacao());
            ps.setDouble(6, transacao.getValor());
            ps.setDate(7, Date.valueOf(transacao.getData().toLocalDate()));

            ps.execute();
            ps.close();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao efetuar transacao", e);
        }
    }

    public List<Transacao> listarTransacoesPorCliente(int idCliente) {

        String sql = "SELECT * FROM agicripto.Transacao WHERE id_cliente = ? AND id_cripto = 1";


        List<Transacao> transacoes = new ArrayList<>();
        PreparedStatement ps;

        Transacao ts = new Transacao();

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ts.setIdTransacao(rs.getInt("id_transacao"))  ;
                ts.setIdCarteira(rs.getInt("id_carteira"));
                ts.setIdCliente(rs.getInt("id_cliente"));
                ts.setIdCripto(rs.getInt("id_cripto"));
                ts.setStatus(rs.getString("status"));
                ts.setIdTipoTransacao(rs.getInt("id_tipo_transacao"));
                ts.setValor(rs.getDouble("valor"));

                Timestamp tp = rs.getTimestamp("data");
                ts.setData(tp.toLocalDateTime());

                System.out.println("data: "+ts.getData());
                System.out.println("transacao: "+ts.getIdTransacao());
                System.out.println("cliente: "+ts.getIdCliente());
                System.out.println("carteira: "+ts.getIdCarteira());
                System.out.println("Status: "+ts.getStatus());
                System.out.println("Valor: "+ts.getValor());
                System.out.println("------------------------------");
//                transacoes.add(transacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transacoes;
    }



    public List<Transacao> listarTransacoesCriptoPorCliente(int idCliente) {

        String sql = "SELECT * FROM agicripto.Transacao WHERE id_cliente = ? AND id_cripto < 4";



        List<Transacao> transacoes = new ArrayList<>();
        PreparedStatement ps;

        Transacao ts = new Transacao();

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ts.setIdTransacao(rs.getInt("id_transacao"))  ;
                ts.setIdCarteira(rs.getInt("id_carteira"));
                ts.setIdCliente(rs.getInt("id_cliente"));
                ts.setIdCripto(rs.getInt("id_cripto"));
                ts.setStatus(rs.getString("status"));
                ts.setIdTipoTransacao(rs.getInt("id_tipo_transacao"));
                ts.setValor(rs.getDouble("valor"));

                Timestamp tp = rs.getTimestamp("data");
                ts.setData(tp.toLocalDateTime());

                System.out.println("data: "+ts.getData());
                System.out.println("transacao: "+ts.getIdTransacao());
                System.out.println("cliente: "+ts.getIdCliente());
                System.out.println("carteira: "+ts.getIdCarteira());
                System.out.println("Status: "+ts.getStatus());
                System.out.println("Valor: "+ts.getValor());
                if (ts.getIdCripto() == 1) System.out.println("BTC");
                if (ts.getIdCripto() == 2) System.out.println("ETH");
                if (ts.getIdCripto() == 3) System.out.println("SOL");
                System.out.println("------------------------------");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transacoes;
    }
}
