package edu.cfp.core.controladores.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Producto {

    @Id // ◄ CRUCIAL: Define la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea auto-incremental en Postgres (Serial)
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer cantidad;
    private String codigoBarra;


}
