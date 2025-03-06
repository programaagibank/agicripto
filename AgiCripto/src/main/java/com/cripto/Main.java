package com.cripto;

import com.cripto.controller.ClienteController;
import com.cripto.model.Cliente;
import com.cripto.model.database.Conexao;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        ClienteController controller = new ClienteController();
        controller.cadastro();
    }
}