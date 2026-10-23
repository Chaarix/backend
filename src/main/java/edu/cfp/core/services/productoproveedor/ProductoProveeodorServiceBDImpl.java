package edu.cfp.core.services.productoproveedor;

import edu.cfp.core.controladores.dominio.ProductoProveedor;
import edu.cfp.core.persistence.entities.ProductoProveedorEntity;
import edu.cfp.core.persistence.repositories.ProductoProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoProveeodorServiceBDImpl implements ProductoProveedorService {

    @Autowired
    private ProductoProveedorRepository productoProveedorRepository;

    @Override
    public List<ProductoProveedor> getProductosPorProveedor(Integer idProveedor) {
        // 1. Buscamos todas las entidades físicas de la tabla "productos"
        List<ProductoProveedorEntity> entidades = productoProveedorRepository.findAll();
        List<ProductoProveedor> listaDominio = new ArrayList<>();

        // 2. Filtramos por el idProveedor usando los atributos directos de tu Entity
        for (ProductoProveedorEntity entity : entidades) {
            if (entity.getIdProveedor() != null && entity.getIdProveedor().equals(idProveedor)) {
                // 3. Mapeamos directamente al objeto de Dominio
                ProductoProveedor pp = new ProductoProveedor();
                pp.setIdProducto(entity.getIdProducto());
                pp.setIdProveedor(entity.getIdProveedor());
                pp.setPrecioCosto(entity.getPrecioCosto());
                listaDominio.add(pp);
            }
        }
        return listaDominio;
    }

    @Override
    public ProductoProveedor saveAsociacion(ProductoProveedor productoProveedor) {
        // 1. Creamos la entidad física usando los atributos planos de tu Entity
        ProductoProveedorEntity entity = new ProductoProveedorEntity();

        // NOTA: No seteamos idProducto si dejas el GenerationType.IDENTITY,
        // ya que Postgres lo generará automáticamente al insertar.
        entity.setIdProveedor(productoProveedor.getIdProveedor());
        entity.setPrecioCosto(productoProveedor.getPrecioCosto());

        // 2. Persistimos la entidad en la base de datos
        ProductoProveedorEntity entityGuardada = productoProveedorRepository.save(entity);

        // 3. Seteamos el ID generado de vuelta en nuestro objeto de dominio si lo necesitas
        productoProveedor.setIdProducto(entityGuardada.getIdProducto());

        return productoProveedor;
    }

    @Override
    public void desasociar(Integer idProducto, Integer idProveedor) {
        // Al usar tu Entity con idProducto como @Id, la eliminación directa de JPA se hace por ese ID
        if (productoProveedorRepository.existsById(idProducto)) {
            productoProveedorRepository.deleteById(idProducto);
        }
    }
}
