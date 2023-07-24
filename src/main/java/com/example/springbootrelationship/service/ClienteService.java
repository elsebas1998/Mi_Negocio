package com.example.springbootrelationship.service;

import com.example.springbootrelationship.models.DireccionCliente;
import com.example.springbootrelationship.repositorie.DireccionRepository;
import com.example.springbootrelationship.service.exception.ClienteExistenteException;
import com.example.springbootrelationship.models.Cliente;
import com.example.springbootrelationship.repositorie.ClienteRepository;
import com.example.springbootrelationship.service.exception.ClienteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final DireccionRepository direccionRepository;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, DireccionRepository direccionRepository) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }


    //Buscar cliente por nombre o identificacion
    public List<Cliente> findByNombresOrNumeroIdentificacion(String nombres, String numeroIdentificacion) {
        return clienteRepository.findByNombresOrNumeroIdentificacion(nombres, numeroIdentificacion);
    }

    // Crear nuevo cliente
    public Cliente crearNuevoCliente(Cliente cliente) {
        // Validar que no exista otro cliente con el mismo número de identificación
        if (clienteRepository.existsByNumeroIdentificacion(cliente.getNumeroIdentificacion())) {
            throw new ClienteExistenteException("Ya existe un cliente con este número de identificación.");
        }

        // Guardar el nuevo cliente en la base de datos
        return clienteRepository.save(cliente);
    }

    // Guardar dirección en la base de datos
    public DireccionCliente guardarDireccion(DireccionCliente direccion) {
        return direccionRepository.save(direccion);
    }

    //Editar cliente
    public Cliente editarCliente(Long clienteId, Cliente clienteActualizado) {
        // Verificar que el cliente a editar existe en la base de datos
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("El cliente con ID " + clienteId + " no existe."));

        // Verificar si la cédula del cliente actualizado es diferente a la del cliente existente
        if (!clienteExistente.getNumeroIdentificacion().equals(clienteActualizado.getNumeroIdentificacion())) {
            // Realizar la validación de cédula
            if (!validatorService.validarCedula(clienteActualizado.getNumeroIdentificacion())) {
                throw new ClienteExistenteException("Cédula inválida.");
            }
        }

        // Actualizar los datos del cliente existente con los nuevos datos
        clienteExistente.setNombres(clienteActualizado.getNombres());
        clienteExistente.setCorreo(clienteActualizado.getCorreo());
        clienteExistente.setNumeroCelular(clienteActualizado.getNumeroCelular());

        // Guardar el cliente actualizado en la base de datos
        return clienteRepository.save(clienteExistente);
    }

    public void actualizarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    //Eliminar cliente por Id

    public void eliminarCliente(Long clienteId) {
        // Verificar que el cliente a eliminar existe en la base de datos
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("El cliente con ID " + clienteId + " no existe."));

        // Eliminar el cliente de la base de datos
        clienteRepository.delete(clienteExistente);
    }


    // Listar dirreciones adicionales del cliente
    public List<DireccionCliente> listarDireccionesAdicionales(Long clienteId) {
        // Verificar que el cliente exista en la base de datos
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("El cliente con ID " + clienteId + " no existe."));

        // Obtener todas las direcciones del cliente
        List<DireccionCliente> todasLasDirecciones = clienteExistente.getDirecciones();

        // Filtrar las direcciones adicionales (excluyendo la dirección matriz)
        List<DireccionCliente> direccionesAdicionales = new ArrayList<>();
        for (DireccionCliente direccion : todasLasDirecciones) {
            if (!direccion.equals(clienteExistente.getDireccionMatriz())) {
                direccionesAdicionales.add(direccion);
            }
        }

        return direccionesAdicionales;
    }

    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }



}
