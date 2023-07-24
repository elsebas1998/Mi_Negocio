package com.example.springbootrelationship.controllers;
import com.example.springbootrelationship.models.Cliente;
import com.example.springbootrelationship.models.DireccionCliente;
import com.example.springbootrelationship.service.ValidatorService;
import com.example.springbootrelationship.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final ValidatorService validatorService;

    @Autowired
    public ClienteController(ClienteService clienteService, ValidatorService validatorService) {
        this.clienteService = clienteService;
        this.validatorService = validatorService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Cliente>> buscarClientes(@RequestParam(required = false) String nombres,
                                                        @RequestParam(required = false) String numeroIdentificacion) {
        List<Cliente> clientesEncontrados = clienteService.findByNombresOrNumeroIdentificacion(nombres, numeroIdentificacion);
        return ResponseEntity.ok(clientesEncontrados);
    }


    @PostMapping("/crear")
    public ResponseEntity<String> crearCliente(@RequestBody Cliente cliente) {
        // Validar la cédula antes de crear el cliente
        if (!validatorService.validarCedula(cliente.getNumeroIdentificacion())) {
            return ResponseEntity.badRequest().body("Cédula inválida");
        }

        // Validar el correo electrónico utilizando ValidatorService
        if (!validatorService.validarCorreoElectronico(cliente.getCorreo())) {
            return ResponseEntity.badRequest().body("Correo electrónico inválido");
        }

        // Lógica para crear el cliente
        Cliente clienteCreado = clienteService.crearNuevoCliente(cliente);
        return ResponseEntity.ok("Cliente creado exitosamente.");
    }

    // Método para validar el formato de correo electrónico
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }


    @PutMapping("/editar/{clienteId}")
    public ResponseEntity<Cliente> editarCliente(@PathVariable Long clienteId, @RequestBody Cliente clienteActualizado) {
        // Lógica para editar el cliente
        Cliente clienteEditado = clienteService.editarCliente(clienteId, clienteActualizado);

        if (clienteEditado != null) {
            return ResponseEntity.ok(clienteEditado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{clienteId}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long clienteId) {
        clienteService.eliminarCliente(clienteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/direccionesAddi/{clienteId}")
    public ResponseEntity<List<DireccionCliente>> listarDireccionesAdicionales(@PathVariable Long clienteId) {
        List<DireccionCliente> direccionesAdicionales = clienteService.listarDireccionesAdicionales(clienteId);
        return ResponseEntity.ok(direccionesAdicionales);
    }


   /* @PostMapping("/direccionesN/{clienteId}")
    public ResponseEntity<String> agregarDireccionCliente(@PathVariable Long clienteId, @RequestBody DireccionCliente direccion) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        cliente.agregarDireccion(direccion);
        clienteService.actualizarCliente(cliente);

        return ResponseEntity.ok("Dirección agregada con éxito.");
    }*/

    @PostMapping("/direccionesN/{clienteId}")
    public ResponseEntity<String> agregarDireccionCliente(@PathVariable Long clienteId, @RequestBody DireccionCliente direccion) {
        Cliente cliente = clienteService.obtenerClientePorId(clienteId);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }

        // Validar la provincia antes de agregar la dirección
        if (!validatorService.validarProvincia(direccion.getProvincia())) {
            return ResponseEntity.badRequest().body("Provincia inválida");
        }

        // Crear una nueva dirección asociada al cliente existente
        DireccionCliente nuevaDireccion = new DireccionCliente(direccion.getProvincia(), direccion.getCiudad(), direccion.getDireccion(), cliente);
        cliente.agregarDireccion(nuevaDireccion);
        clienteService.actualizarCliente(cliente);

        return ResponseEntity.ok("Dirección agregada con éxito.");
    }

}
