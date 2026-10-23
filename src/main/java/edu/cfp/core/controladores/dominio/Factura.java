package edu.cfp.core.controladores.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    // CORREGIDO: "id" en minúsculas para unificar con tus otros servicios y métodos
    private Integer idfactura;

    private String producto;
    private Double precioFinal;
    private Double precioUnitario;
    private Double cantidad;
    private String metodoDePago;
    private String cliente;
    private String fechaEmison;
    private String numeroDeFactura;
    private String dni;

    @JsonProperty("productos")
    private List<Producto> productos;


    public Integer getId() {
        return this.idfactura;
    }

    public void setId(Integer id) {
        this.idfactura= id;
    }
}

