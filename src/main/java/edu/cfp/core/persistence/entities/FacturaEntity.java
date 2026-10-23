package edu.cfp.core.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name ="facturas")
@Data

public class FacturaEntity {

    @Id
    @GeneratedValue(strategy  = GenerationType.SEQUENCE)
    private Integer idFactura;
    private String producto;
    private double precioFinal;
    private double precioUnitario;
    private double cantidad;
    private String metodoDePago;
    private String cliente;
    private String fechaEmison;
    private String numeroDeFactura;
    private String DNI;

}

