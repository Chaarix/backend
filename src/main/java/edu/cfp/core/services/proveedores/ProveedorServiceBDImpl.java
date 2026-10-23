package edu.cfp.core.services.proveedores;

import edu.cfp.core.controladores.dominio.Proveedor;
import edu.cfp.core.persistence.entities.ProveedorEntity; // Asegúrate de tener esta entidad
import edu.cfp.core.persistence.repositories.ProveedorRepository; // Asegúrate de tener este repositorio
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Slf4j
@Lazy
@Service("PROVEEDOR_DATABASE")
@ConditionalOnProperty(
        value = "proveedores.estrategia",
        havingValue = "EN_BD")
public class ProveedorServiceBDImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public ProveedorServiceBDImpl() {
        log.info("Se esta construyendo un obj de la clase ProveedorServiceBDImpl (Conexion a PostgreSQL)");
    }

    @Override
    public Proveedor saveProveedor(Proveedor proveedor) {
        log.info("Guardando proveedor en PostgreSQL: {}", proveedor.getNombreEntidad());

        // Mapeo de Objeto de Dominio (Proveedor) a Entidad JPA (ProveedorEntity)
        ProveedorEntity entity = new ProveedorEntity();
        entity.setNombreEntidad(proveedor.getNombreEntidad());
        entity.setTipoIdentificacion(proveedor.getTipoIdentificacion());
        entity.setTelefonoEntidad(proveedor.getTelefonoEntidad());
        entity.setCuitcuilEntidad(proveedor.getCuitcuilEntidad());
        entity.setCondicionIva(proveedor.getCondicionIva());
        entity.setDniEntidad(proveedor.getDniEntidad());
        entity.setPais(proveedor.getPais());
        entity.setEmailEntidad(proveedor.getEmailEntidad());
        entity.setCiudad(proveedor.getCiudad());
        entity.setProvincia(proveedor.getProvincia());

        // Guardado físico en PostgreSQL
        ProveedorEntity entityGuardada = proveedorRepository.save(entity);

        // Retornamos el objeto de dominio con su ID asignado por la BD
        proveedor.setId(entityGuardada.getId());
        return proveedor;
    }
}
