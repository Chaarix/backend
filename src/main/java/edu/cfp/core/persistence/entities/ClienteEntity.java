package edu.cfp.core.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="cliente")
@Data
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
