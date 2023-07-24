package com.example.springbootrelationship.service;

import com.example.springbootrelationship.models.DireccionCliente;
import com.example.springbootrelationship.repositorie.ClienteRepository;
import com.example.springbootrelationship.repositorie.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionService {


    private DireccionRepository direccionRepository;

    @Autowired
    public DireccionService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    // Método para guardar una dirección en la base de datos
    public DireccionCliente guardarDireccion(DireccionCliente direccion) {
        return direccionRepository.save(direccion);
    }
}
