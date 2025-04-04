import com.cripto.controller.ClienteController;
import com.cripto.dao.ClienteDAO;
import com.cripto.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class ClienteServiceTestPos {

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
        String email = "agivaldo@";
        String senha = "123";
        String senhaCriptografada = cliente.criptografarSenha(senha); // Simulando a senha criptografada

        boolean resultado = clienteController.fazerLogin(email, senhaCriptografada);

        // Verificamos se o login foi bem-sucedido
        assertTrue(resultado);



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




