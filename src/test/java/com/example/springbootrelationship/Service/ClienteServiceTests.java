package com.example.springbootrelationship.Service;


import com.example.springbootrelationship.models.Cliente;
import com.example.springbootrelationship.models.DireccionCliente;
import com.example.springbootrelationship.repositorie.ClienteRepository;
import com.example.springbootrelationship.repositorie.DireccionRepository;
import com.example.springbootrelationship.service.ClienteService;
import com.example.springbootrelationship.service.exception.ClienteExistenteException;
import com.example.springbootrelationship.service.exception.ClienteNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClienteServiceTests {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Test
    public void testCrearNuevoCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombres("Nombre del cliente");
        cliente.setNumeroIdentificacion("1234567890");
        cliente.setCorreo("cliente@example.com");
        cliente.setNumeroCelular("1234567890");

        ClienteService clienteService = new ClienteService(clienteRepository, direccionRepository);
        Cliente clienteCreado = clienteService.crearNuevoCliente(cliente);

        assertNotNull(clienteCreado);
        assertNotNull(clienteCreado.getIdCliente());
        assertEquals("Nombre del cliente", clienteCreado.getNombres());
        assertEquals("1234567890", clienteCreado.getNumeroIdentificacion());
        assertEquals("cliente@example.com", clienteCreado.getCorreo());
        assertEquals("1234567890", clienteCreado.getNumeroCelular());
    }

    @Test
    public void testCrearNuevoCliente_Duplicado() {
        Cliente cliente1 = new Cliente();
        cliente1.setNombres("Cliente 1");
        cliente1.setNumeroIdentificacion("1234567890");
        cliente1.setCorreo("cliente1@example.com");
        cliente1.setNumeroCelular("1234567890");

        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNombres("Cliente 2");
        cliente2.setNumeroIdentificacion("1234567890");
        cliente2.setCorreo("cliente2@example.com");
        cliente2.setNumeroCelular("9876543210");

        ClienteService clienteService = new ClienteService(clienteRepository, direccionRepository);

        assertThrows(ClienteExistenteException.class, () -> clienteService.crearNuevoCliente(cliente2));
    }


}
