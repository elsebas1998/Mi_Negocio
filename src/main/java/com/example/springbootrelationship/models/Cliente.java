package com.example.springbootrelationship.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;


    @NotEmpty
    @Column(nullable = false)
    private String tipoIdentificacion;

    @Size(min = 10, max = 10)
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String numeroIdentificacion;

    @Size(min = 3, max = 15)
    @NotEmpty
    @Column(nullable = false)
    private String nombres;

    @Size(min = 5, max = 50)
    @NotEmpty
    @Email(message = "El formato del correo electrónico no es válido")

    @Column(nullable = false)
    private String correo;

    @Size(min = 10, max = 10)
    @NotEmpty
    @Column(nullable = false)
    private String numeroCelular;


    @NotEmpty
    @Column(nullable = false)
    private String direccionMatriz;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DireccionCliente> direcciones = new ArrayList<>();;



    // Método para agregar una dirección adicional
    public void agregarDireccion(DireccionCliente nuevaDireccion) {
        if (direcciones == null) {
            direcciones = new ArrayList<>();
        }
        direcciones.add(nuevaDireccion);
        nuevaDireccion.setCliente(this);
    }




    // Constructor con todos los atributos
    public Cliente(Long idCliente, String tipoIdentificacion, String numeroIdentificacion, String nombres,
                   String correo, String numeroCelular, DireccionCliente direccionMatriz, List<DireccionCliente> direcciones) {
        this.idCliente = idCliente;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.correo = correo;
        this.numeroCelular = numeroCelular;
    }
    public Cliente(String tipoIdentificacion, String numeroIdentificacion, String nombres,
                   String correo, String numeroCelular, String direccionMatriz) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.correo = correo;
        this.numeroCelular = numeroCelular;
        this.direccionMatriz = direccionMatriz; // Asignar el valor proporcionado
    }

    // Constructor con argumentos mínimos
    public Cliente(String tipoIdentificacion, String numeroIdentificacion, String nombres,
                   String correo, String numeroCelular) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombres = nombres;
        this.correo = correo;
        this.numeroCelular = numeroCelular;
    }

    public Cliente() {
    }

    // Getters y Setters


    public String getDireccionMatriz() {
        return direccionMatriz;
    }

    public void setDireccionMatriz(String direccionMatriz) {
        this.direccionMatriz = direccionMatriz;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }



    public List<DireccionCliente> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<DireccionCliente> direcciones) {
        this.direcciones = direcciones;
    }
}

