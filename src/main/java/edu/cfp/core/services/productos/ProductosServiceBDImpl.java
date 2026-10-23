package edu.cfp.core.services.productos;

import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.persistence.entities.ProductoEntity;
import edu.cfp.core.persistence.repositories.ProductosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Lazy
@Service("PRODUCTO_DATABASE")
@ConditionalOnProperty(
        value = "productos.estrategia",
        havingValue = "EN_BD")
public class ProductosServiceBDImpl implements ProductoService {

    public ProductosServiceBDImpl() {
        log.info("Se esta construyendo un obj de la clase ProductosServiceImpl");
    }

    @Autowired
    private ProductosRepository productosRepository;

    // =========================================================================
    // GET: Mapeo corregido leyendo de 'productoEntity'
    // =========================================================================
    public List<Producto> getProductos() {
        return productosRepository.findAll().stream().map(productoEntity -> {
            Producto p = new Producto();
            // CORRECCIÓN: Leemos de 'productoEntity' (el origen), no de 'p' (el destino)
            p.setId(productoEntity.getId());
            p.setNombre(productoEntity.getNombre());
            p.setPrecio(productoEntity.getPrecio());
            p.setCantidad(productoEntity.getCantidad());
            p.setCodigoBarra(productoEntity.getCodigoBarra());
            return p;
        }).collect(Collectors.toList());
    }

    // =========================================================================
    // POST (saveProductos): Corregido agregando la persistencia real (.save)
    // =========================================================================
    @Override
    public Producto saveProductos(Producto producto) {
        ProductoEntity productoEntity = new ProductoEntity();
        productoEntity.setNombre(producto.getNombre());
        productoEntity.setPrecio(producto.getPrecio());
        productoEntity.setCantidad(producto.getCantidad());
        productoEntity.setCodigoBarra(producto.getCodigoBarra());

        // CORRECCIÓN 1: Guardar físicamente en PostgreSQL / MySQL
        ProductoEntity entityGuardada = productosRepository.save(productoEntity);

        // CORRECCIÓN 2: Asignar al objeto de dominio el ID real generado por la base de datos
        producto.setId(entityGuardada.getId());

        return producto;
    }

    // =========================================================================
    // Método auxiliar corregido por consistencia
    // =========================================================================
    public void saveProducto(Producto producto) {
        ProductoEntity p = new ProductoEntity();
        // CORRECCIÓN: Extraemos los valores del parámetro entrante 'producto'
        p.setNombre(producto.getNombre());
        p.setPrecio(producto.getPrecio());
        p.setCantidad(producto.getCantidad());
        p.setCodigoBarra(producto.getCodigoBarra());

        productosRepository.save(p);
    }
}
