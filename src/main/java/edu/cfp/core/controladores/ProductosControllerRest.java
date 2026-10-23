package edu.cfp.core.controladores;

import edu.cfp.core.configuraciones.ConfigurationParameters;
import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.persistence.entities.ProductoEntity; // Ajusta el paquete de tus entidades si varía
import edu.cfp.core.exceptions.ResourceNotFoundException;
import edu.cfp.core.persistence.repositories.ProductosRepository;
import edu.cfp.core.services.productos.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductosControllerRest {

    @Autowired
    @Lazy
    private ProductoService productosService;

    @Autowired
    private ProductosRepository productoRepository;

    @Autowired
    private ConfigurationParameters configurationParameters;

    // =========================================================================
    // GET: Obtener todos los productos desde la Base de Datos física
    // =========================================================================
    @GetMapping
    public ResponseEntity<?> getProductos() {
        // 1. Buscamos todas las entidades físicas en la base de datos
        List<ProductoEntity> entidades = productoRepository.findAll();
        List<Producto> productosDominio = new ArrayList<>();

        // 2. Mapeamos de ProductoEntity al objeto de dominio Producto
        for (ProductoEntity entity : entidades) {
            Producto p = new Producto();
            p.setId(entity.getId());
            p.setNombre(entity.getNombre());
            p.setPrecio(entity.getPrecio());
            p.setCantidad(entity.getCantidad());
            p.setCodigoBarra(entity.getCodigoBarra());
            productosDominio.add(p);
        }

        return ResponseEntity.ok(productosDominio);
    }

    // =========================================================================
    // POST: Alta de Producto utilizando tu estructura de Servicio intacta
    // =========================================================================
    @PostMapping
    public ResponseEntity<?> altaProducto(@RequestBody Producto producto) {
        // 1. Guardar y capturar el objeto persistido con su ID real de la BD
        Producto productoGuardado = productosService.saveProductos(producto);

        // 2. Construir la URI usando {id} de forma dinámica
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productoGuardado.getId())
                .toUri();

        // 3. Retornar el código 201 Created con el objeto persistido
        return ResponseEntity.created(location).body(productoGuardado);
    }

    // =========================================================================
    // PUT: Modificación de un Producto existente en Base de Datos por ID
    // =========================================================================
    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<?> modificacionProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        // 1. Buscamos la entidad usando el 'id' proveniente de la URL
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // 2. Actualizamos los campos de la entidad con los datos recibidos del cliente
        entity.setNombre(producto.getNombre());
        entity.setPrecio(producto.getPrecio());
        entity.setCantidad(producto.getCantidad());
        entity.setCodigoBarra(producto.getCodigoBarra());

        // 3. Guardamos los cambios de manera directa en la BD física
        productoRepository.save(entity);

        return ResponseEntity.ok(producto);
    }

    // =========================================================================
    // DELETE: Eliminación directa por ID numérico en la Base de Datos
    // =========================================================================
    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarProducto(@PathVariable Integer id) {
        // 1. Verificamos si existe el ID en la base de datos antes de borrar
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }

        // 2. Eliminación física directa por ID en PostgreSQL / MySQL
        productoRepository.deleteById(id);

        // 3. Retornamos un 204 No Content (Éxito estándar para DELETE)
        return ResponseEntity.noContent().build();
    }



}
