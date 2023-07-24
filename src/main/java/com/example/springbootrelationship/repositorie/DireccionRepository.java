package com.example.springbootrelationship.repositorie;


import com.example.springbootrelationship.models.DireccionCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends JpaRepository<DireccionCliente, Long> {
}
