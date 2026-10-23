package edu.cfp.core.services.productoproveedor;

import edu.cfp.core.controladores.dominio.ProductoProveedor;
import java.util.List;

public interface ProductoProveedorService {

    // Para el botón Listar: Trae todos los productos asociados a un proveedor
    List<ProductoProveedor> getProductosPorProveedor(Integer idProveedor);

    // Para el botón Agregar: Inserta la nueva relación en la base de datos
    ProductoProveedor saveAsociacion(ProductoProveedor productoProveedor);

    // Para el botón Eliminar: Rompe el vínculo sin borrar las entidades reales
    void desasociar(Integer idProducto, Integer idProveedor);
}
