package edu.cfp.core.services.clientes;

import edu.cfp.core.controladores.dominio.Cliente;
import edu.cfp.core.persistence.entities.ClienteEntity;
import edu.cfp.core.persistence.repositories.ClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Slf4j
@Lazy
@Service("CLIENTE_DATABASE_AUX")
@ConditionalOnProperty(
        value = "clientes.estrategia",
        havingValue = "EN_BD")
public class ClienteServiceBDImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteServiceBDImpl() {
        log.info("Se esta construyendo un obj de la clase ClienteServiceBdImpl (Conexion a PostgreSQL)");
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        log.info("Guardando cliente en PostgreSQL: {}", cliente.getNombreEntidad());

        // Mapeo de Objeto de Dominio a Entidad JPA
        ClienteEntity entity = new ClienteEntity();
        entity.setNombreEntidad(cliente.getNombreEntidad());
        entity.setTipoIdentificacion(cliente.getTipoIdentificacion());
        entity.setTelefonoEntidad(cliente.getTelefonoEntidad());
        entity.setCuitcuilEntidad(cliente.getCuitcuilEntidad());
        entity.setCondicionIva(cliente.getCondicionIva());
        entity.setDniEntidad(cliente.getDniEntidad());
        entity.setPais(cliente.getPais());
        entity.setEmailEntidad(cliente.getEmailEntidad());
        entity.setCiudad(cliente.getCiudad());
        entity.setProvincia(cliente.getProvincia());

        // Guardado físico en PostgreSQL
        ClienteEntity entityGuardada = clienteRepository.save(entity);

        // Retornamos el objeto con su ID asignado por la BD
        cliente.setId(entityGuardada.getId());
        return cliente;
    }

}
