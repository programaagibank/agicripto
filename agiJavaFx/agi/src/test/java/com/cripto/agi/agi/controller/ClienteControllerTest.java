package com.cripto.agi.agi.controller;


import com.cripto.agi.agi.dao.ClienteDAO;
import com.cripto.agi.agi.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteControllerTest{

    @Mock
    private ClienteDAO clienteDAO; // Mockamos a dependência

    @Mock
    private Cliente cliente; // Mockamos o Cliente

    @InjectMocks
    private ClienteController clienteController; // A classe que estamos testando

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    public void testFazerLogin_Sucesso() {
        String email = "SLA";
        String senha = "123";
        String senhaCriptografada = "8d4e41c4"; // Simulando a senha criptografada

        // Criamos um mock do Cliente que seria retornado pelo DAO
        Cliente clienteMock = mock(Cliente.class);

        // Configuramos o mock para simular o comportamento esperado
        when(clienteDAO.encontrarEmail(email)).thenReturn(clienteMock);
        when(clienteMock.getSenha()).thenReturn(senhaCriptografada);
        when(cliente.criptografarSenha(senha)).thenReturn(senhaCriptografada);

        // Testamos o método fazerLogin

        boolean resultado = clienteController.fazerLogin(email, senha);

        // Verificamos se o login foi bem-sucedido
        assertFalse(resultado);

        // Garantimos que os métodos esperados foram chamados corretamente
        verify(clienteDAO).encontrarEmail(email);
        verify(clienteMock).getSenha();
        verify(cliente).criptografarSenha(senha);
    }

    @Test
    public void testFazerLogin_EmailNaoEncontrado() {
        String email = "naoexiste@email.com";
        String senha = "123456";

        // Simulamos que o DAO retorna null (ou seja, email não existe)
        when(clienteDAO.encontrarEmail(email)).thenReturn(null);

        boolean resultado = clienteController.fazerLogin(email, senha);

        assertFalse(resultado);

        verify(clienteDAO).encontrarEmail(email);
        verifyNoMoreInteractions(cliente);
    }

    @Test
    public void testFazerLogin_SenhaIncorreta() {
        String email = "teste@email.com";
        String senha = "senhaErrada";
        String senhaCorretaCriptografada = "senhaCriptografada";

        Cliente clienteMock = mock(Cliente.class);

        // Simulamos que o DAO retorna um cliente com senha diferente
        when(clienteDAO.encontrarEmail(email)).thenReturn(clienteMock);
        when(clienteMock.getSenha()).thenReturn(senhaCorretaCriptografada);
        when(cliente.criptografarSenha(senha)).thenReturn("senhaErradaCriptografada");

        boolean resultado = clienteController.fazerLogin(email, senha);

        assertFalse(resultado);

        verify(clienteDAO).encontrarEmail(email);
        verify(clienteMock).getSenha();
        verify(cliente).criptografarSenha(senha);
    }
}