package edu.cfp.core.controladores.dominio;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductoProveedor {

    // Representa la combinación única que identifica la relación
    private Integer idProducto;
    private Integer idProveedor;

    // El precio de costo específico que este proveedor le da a este producto
    private Double precioCosto;

}
