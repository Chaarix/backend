package edu.cfp.core.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity(name="proveedor")
@Data
public class ProveedorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
