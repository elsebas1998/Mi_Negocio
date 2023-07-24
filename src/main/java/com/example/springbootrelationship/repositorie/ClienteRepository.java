package com.example.springbootrelationship.repositorie;

import com.example.springbootrelationship.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {





    List<Cliente> findByNombresOrNumeroIdentificacion(String nombres, String numeroIdentificacion);

    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
}

