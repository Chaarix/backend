package edu.cfp.core.controladores;

import edu.cfp.core.controladores.dominio.ProductoProveedor;
import edu.cfp.core.persistence.entities.ProductoProveedorEntity;
import edu.cfp.core.persistence.repositories.ProductoProveedorRepository;
import edu.cfp.core.exceptions.BadRequestException;
import edu.cfp.core.exceptions.ResourceNotFoundException;
import edu.cfp.core.services.productoproveedor.ProductoProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos-proveedores")
public class ProductoProveedorControllerRest {

    @Autowired
    private ProductoProveedorService productoProveedorService;

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    // 1. LISTAR TODOS LOS PRODUCTOS DE UN PROVEEDOR ESPECÍFICO
    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<?> getProductosPorProveedor(@PathVariable Integer idProveedor) {
        List<ProductoProveedorEntity> entidades = productoProveedorRepository.findAll();
        List<ProductoProveedor> listaDominio = new ArrayList<>();

        // Filtramos usando directamente el atributo plano de tu Entity
        for (ProductoProveedorEntity entity : entidades) {
            if (entity.getIdProveedor() != null && entity.getIdProveedor().equals(idProveedor)) {
                ProductoProveedor pp = new ProductoProveedor();
                pp.setIdProducto(entity.getIdProducto());
                pp.setIdProveedor(entity.getIdProveedor());
                pp.setPrecioCosto(entity.getPrecioCosto());
                listaDominio.add(pp);
            }
        }

        return ResponseEntity.ok(listaDominio);
    }
//asdasd
    // 2. OBTENER UN VÍNCULO ESPECÍFICO POR ID DE PRODUCTO
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<?> getProductoProveedor(@PathVariable Integer idProducto) {
        // En tu Entity, idProducto es el @Id primario, por lo que buscamos directamente por él
        return productoProveedorRepository.findById(idProducto)
                .map(entity -> {
                    ProductoProveedor pp = new ProductoProveedor();
                    pp.setIdProducto(entity.getIdProducto());
                    pp.setIdProveedor(entity.getIdProveedor());
                    pp.setPrecioCosto(entity.getPrecioCosto());
                    return ResponseEntity.ok(pp);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Relación Producto-Proveedor no encontrada"));
    }

    // 3. ASOCIAR NUEVO PRODUCTO A PROVEEDOR (ALTA)
    @PostMapping
    public ResponseEntity<?> asociarProductoProveedor(@RequestBody ProductoProveedor dominio) {
        if (dominio.getPrecioCosto() == null || dominio.getPrecioCosto() < 0) {
            throw new BadRequestException("El precio de costo no es válido");
        }

        // Delegamos el guardado al Service corregido
        ProductoProveedor guardado = productoProveedorService.saveAsociacion(dominio);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/producto/{idProducto}")
                .buildAndExpand(guardado.getIdProducto())
                .toUri();

        return ResponseEntity.created(location).body(guardado);
    }

    // 4. MODIFICAR PRECIO DE COSTO (ACTUALIZACIÓN)
    @PutMapping
    public ResponseEntity<?> actualizarPrecioCosto(@RequestBody ProductoProveedor dominio) {
        // Buscamos la entidad utilizando idProducto como la clave primaria directa
        ProductoProveedorEntity entity = productoProveedorRepository.findById(dominio.getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("No existe la relación para actualizar"));

        // Seteamos los campos directos de la entidad
        entity.setIdProveedor(dominio.getIdProveedor());
        entity.setPrecioCosto(dominio.getPrecioCosto());

        productoProveedorRepository.save(entity);
        return ResponseEntity.ok(dominio);
    }

    // 5. ELIMINAR RELACIÓN
    @DeleteMapping("/producto/{idProducto}")
    public ResponseEntity<?> desasociarProductoProveedor(@PathVariable Integer idProducto) {
        // Buscamos y borramos usando la PK directa de tu entidad
        ProductoProveedorEntity entityEncontrada = productoProveedorRepository.findById(idProducto)
                .orElseThrow(() -> new ResourceNotFoundException("Relación no encontrada para eliminar"));

        productoProveedorRepository.delete(entityEncontrada);
        return ResponseEntity.noContent().build();
    }
}
