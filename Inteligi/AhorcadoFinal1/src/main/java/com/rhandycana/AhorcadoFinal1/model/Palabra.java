package com.rhandycana.AhorcadoFinal1.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "palabras")
public class Palabra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_palabra")
    private Integer codigoPalabra;

    @Column(name = "palabra")
    private String palabra;

    @Column(name = "pista")
    private String pista;

    @Column(name = "categoria")
    private String categoria;
}