package com.example.springbootrelationship.Controller;
import com.example.springbootrelationship.service.ValidatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootrelationship.controllers.ClienteController;
import com.example.springbootrelationship.models.Cliente;
import com.example.springbootrelationship.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RunWith(SpringRunner.class)
@WebMvcTest(ClienteController.class)
public class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;
    @MockBean // Agrega esta línea para simular el servicio ValidatorService
    private ValidatorService validatorService;
    @Test
    public void testBuscarClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombres("Nombre del cliente");
        List<Cliente> clientesEncontrados = Arrays.asList(cliente);

        when(clienteService.findByNombresOrNumeroIdentificacion(anyString(), anyString()))
                .thenReturn(clientesEncontrados);

        mockMvc.perform(get("/api/clientes/buscar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Nombre del cliente")));

        verify(clienteService, times(1)).findByNombresOrNumeroIdentificacion(null, null);
    }

    @Test
    public void testCrearClienteExitoso() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombres("Nombre del cliente");

        when(clienteService.crearNuevoCliente(any(Cliente.class)))
                .thenReturn(cliente);

        mockMvc.perform(post("/api/clientes/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cliente)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Cliente creado exitosamente.")));

        verify(clienteService, times(1)).crearNuevoCliente(any(Cliente.class));
    }

    @Test
    public void testCrearClienteCedulaInvalida() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombres("Nombre del cliente");
        cliente.setNumeroIdentificacion("123456789"); // Cédula inválida

        mockMvc.perform(post("/api/clientes/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cliente)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Cédula inválida")));
    }

    // Agregar otros tests para los demás métodos del controlador
    // ...

    // Método para convertir un objeto a formato JSON
    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

