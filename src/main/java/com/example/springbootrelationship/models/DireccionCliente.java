package com.example.springbootrelationship.models;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "direcciones_cliente")
public class DireccionCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long idDireccion;

    @Size(min = 5, max = 20)
    @NotEmpty
    @Column(nullable = false)
    private String provincia;

    @Size(min = 5, max = 20)
    @NotEmpty
    @Column(nullable = false)
    private String ciudad;


    @Size(min = 3, max = 150)
    @NotEmpty
    @Column(nullable = false)
    private String direccion;

    // Relación Muchos-a-Uno con Cliente (direcciones adicionales)
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
   // Constructor

    // Constructor con todos los atributos
    public DireccionCliente(Long idDireccion, String provincia, String ciudad, String direccion, Cliente cliente) {
        this.idDireccion = idDireccion;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.cliente = cliente;

    }

    // Constructor con argumentos mínimos
    public DireccionCliente(String provincia, String ciudad, String direccion, Cliente cliente) {
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.cliente = cliente;
    }

    public DireccionCliente() {
    }

    // Getters y Setters
    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


}

