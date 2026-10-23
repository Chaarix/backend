package edu.cfp.core.controladores;

import edu.cfp.core.controladores.dominio.Proveedor;
import edu.cfp.core.persistence.entities.ProveedorEntity;
import edu.cfp.core.persistence.repositories.ProveedorRepository; // Tu repositorio
import edu.cfp.core.exceptions.BadRequestException;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import edu.cfp.core.services.proveedores.ProveedorService; // Tu interfaz de servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorRestController {

    @Autowired
    private ProveedorService proveedorService; // Usado para el alta (POST)

    @Autowired
    private ProveedorRepository proveedorRepository; // Usado para consultas directas a la BD

    @GetMapping
    public ResponseEntity<?> getProveedor() {
        // 1. Buscamos todas las entidades físicas de PostgreSQL
        List<ProveedorEntity> entidades = proveedorRepository.findAll();
        List<Proveedor> listaDominio = new ArrayList<>();

        // 2. Mapeamos las entidades de la BD de vuelta a objetos de tu dominio Proveedor
        for (ProveedorEntity entity : entidades) {
            Proveedor p = new Proveedor();
            p.setId(entity.getId().intValue());
            p.setNombreEntidad(entity.getNombreEntidad());
            p.setTipoIdentificacion(entity.getTipoIdentificacion());
            p.setTelefonoEntidad(entity.getTelefonoEntidad());
            p.setCuitcuilEntidad(entity.getCuitcuilEntidad());
            p.setCondicionIva(entity.getCondicionIva());
            p.setDniEntidad(entity.getDniEntidad());
            p.setPais(entity.getPais());
            p.setEmailEntidad(entity.getEmailEntidad());
            p.setCiudad(entity.getCiudad());
            p.setProvincia(entity.getProvincia());
            listaDominio.add(p);
        }
        return ResponseEntity.ok(listaDominio);
    }

    @GetMapping("/{proveedor}")
    public ResponseEntity<?> getProveedor(@PathVariable String proveedor) {
        if (proveedor.length() != 3) {
            throw new BadRequestException("El usuario no es valido");
        }

        // Buscamos directamente consultando a PostgreSQL
        return proveedorRepository.findAll().stream()
                .filter(entity -> entity.getNombreEntidad().equalsIgnoreCase(proveedor))
                .findFirst()
                .map(entity -> {
                    Proveedor p = new Proveedor();
                    p.setId(entity.getId().intValue());
                    p.setNombreEntidad(entity.getNombreEntidad());
                    p.setTipoIdentificacion(entity.getTipoIdentificacion());
                    p.setTelefonoEntidad(entity.getTelefonoEntidad());
                    p.setCuitcuilEntidad(entity.getCuitcuilEntidad());
                    p.setCondicionIva(entity.getCondicionIva());
                    p.setDniEntidad(entity.getDniEntidad());
                    p.setPais(entity.getPais());
                    p.setEmailEntidad(entity.getEmailEntidad());
                    p.setCiudad(entity.getCiudad());
                    p.setProvincia(entity.getProvincia());
                    return ResponseEntity.ok(p);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor No encontrado"));
    }

    @PostMapping
    public ResponseEntity<?> altaProveedor(@RequestBody Proveedor proveedor) {
        // 1. Guardar en PostgreSQL usando la clase ProveedorServiceImpl que armamos recién
        Proveedor proveedorGuardado = proveedorService.saveProveedor(proveedor);

        // 2. Construir la URI usando el ID autogenerado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(proveedorGuardado.getId())
                .toUri();

        // 3. Retornar 201 Created con el objeto persistido
        return ResponseEntity.created(location).body(proveedorGuardado);
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> modificacionProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        // 1. Buscamos la entidad en PostgreSQL usando el ID que viene de la URL
        ProveedorEntity entity = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));

        // 2. Sincronizamos los cambios con los datos que vienen en el cuerpo (RequestBody)
        entity.setNombreEntidad(proveedor.getNombreEntidad());
        entity.setTelefonoEntidad(proveedor.getTelefonoEntidad());
        entity.setCuitcuilEntidad(proveedor.getCuitcuilEntidad());
        entity.setEmailEntidad(proveedor.getEmailEntidad());
        entity.setPais(proveedor.getPais());
        entity.setProvincia(proveedor.getProvincia());
        entity.setCiudad(proveedor.getCiudad());

        proveedorRepository.save(entity);

        return ResponseEntity.ok(proveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProveedor(@PathVariable Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Proveedor no encontrado");
        }
        proveedorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
