package edu.cfp.core.controladores.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Proveedor {
    private Integer id;
    private String nombreEntidad;
    private String tipoIdentificacion;
    private String telefonoEntidad;
    private String cuitcuilEntidad;
    private String condicionIva;
    private String dniEntidad;
    private String pais;
    private String emailEntidad;
    private String ciudad;
    private String provincia;
}
