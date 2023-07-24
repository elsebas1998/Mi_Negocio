package com.example.springbootrelationship.Repository;

import com.example.springbootrelationship.models.Cliente;
import com.example.springbootrelationship.repositorie.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// Indicar a JUnit que se debe cargar la configuración de Spring Boot para las pruebas de JPA
@DataJpaTest
public class ClienteRepositoryTests {

    // Inyectar el EntityManager de prueba proporcionado por Spring Boot
    @Autowired
    private TestEntityManager entityManager;

    // Inyectar el repositorio que se va a probar
    @Autowired
    private ClienteRepository clienteRepository;

    // Aquí puedes escribir los casos de prueba específicos usando los métodos del repositorio

    @Test
    public void testFindClienteByNombres() {
        // Crear datos de prueba en la base de datos de prueba usando el EntityManager
        Cliente cliente1 = new Cliente("Cedula", "1234567890", "sebas", "elpepe@hotmail.com", "0987685248", "Santo domingo");
        Cliente cliente2 = new Cliente("Cedula", "1719539361", "sebas", "elsebas@hotmail.com", "0987685248", "Santo domingo2");
        entityManager.persist(cliente1);
        entityManager.persist(cliente2);
        entityManager.flush();

        // Realizar la búsqueda en el repositorio
        List<Cliente> clientesEncontrados = clienteRepository.findByNombresOrNumeroIdentificacion("sebas", "");

        // Realizar las afirmaciones para verificar los resultados
        Assertions.assertEquals(2, clientesEncontrados.size());
    }

    @Test
    public void testExistsByNumeroIdentificacion() {
        // Crear un cliente en la base de datos de prueba usando el EntityManager
        Cliente cliente = new Cliente("Cedula", "1234567890", "sebas", "elpepe@hotmail.com", "0987685248", "Santo domingo");
        entityManager.persist(cliente);
        entityManager.flush();

        // Realizar la verificación usando el repositorio
        boolean exists = clienteRepository.existsByNumeroIdentificacion("1234567890");

        // Realizar la afirmación para verificar el resultado
        Assertions.assertTrue(exists);
    }

    @Test
    public void testGuardarCliente() {
        // Crear un nuevo cliente y guardarlo en la base de datos
        Cliente cliente = new Cliente("CEDULA", "1234567890", "Nombre Cliente", "cliente@example.com", "1234567890");
        cliente.setDireccionMatriz("Dirección de Matriz");
        clienteRepository.save(cliente);

        // Verificar si el cliente se ha guardado correctamente
        assertThat(cliente.getIdCliente()).isNotNull();
        assertThat(clienteRepository.existsById(cliente.getIdCliente())).isTrue();
    }

    @Test
    public void testBuscarClientePorId() {
        // Crear un nuevo cliente y guardarlo en la base de datos
        Cliente cliente = new Cliente("CEDULA", "1234567890", "Nombre Cliente", "cliente@example.com", "1234567890");
        cliente.setDireccionMatriz("Dirección de Matriz");
        entityManager.persistAndFlush(cliente);

        // Buscar el cliente por ID y verificar si se recupera correctamente
        Cliente clienteEncontrado = clienteRepository.findById(cliente.getIdCliente()).orElse(null);
        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.getNumeroIdentificacion()).isEqualTo("1234567890");
        assertThat(clienteEncontrado.getNombres()).isEqualTo("Nombre Cliente");
    }

    @Test
    public void testActualizarCliente() {
        // Crear un nuevo cliente y guardarlo en la base de datos
        Cliente cliente = new Cliente("CEDULA", "1234567890", "Nombre Cliente", "cliente@example.com", "1234567890");
        cliente.setDireccionMatriz("Dirección de Matriz");
        entityManager.persistAndFlush(cliente);

        // Actualizar la información del cliente
        cliente.setNombres("Nuevo Nombre");
        cliente.setCorreo("nuevo_cliente@example.com");
        clienteRepository.save(cliente);

        // Verificar si el cliente se ha actualizado correctamente
        Cliente clienteActualizado = clienteRepository.findById(cliente.getIdCliente()).orElse(null);
        assertThat(clienteActualizado).isNotNull();
        assertThat(clienteActualizado.getNombres()).isEqualTo("Nuevo Nombre");
        assertThat(clienteActualizado.getCorreo()).isEqualTo("nuevo_cliente@example.com");
    }

    @Test
    public void testEliminarCliente() {
        // Crear un nuevo cliente y guardarlo en la base de datos
        Cliente cliente = new Cliente("CEDULA", "1234567890", "Nombre Cliente", "cliente@example.com", "1234567890");
        cliente.setDireccionMatriz("Dirección de Matriz");
        entityManager.persistAndFlush(cliente);

        // Eliminar el cliente
        clienteRepository.deleteById(cliente.getIdCliente());

        // Verificar si el cliente ha sido eliminado correctamente
        assertThat(clienteRepository.existsById(cliente.getIdCliente())).isFalse();
    }

}
