package edu.cfp.core.services.productos;

import edu.cfp.core.controladores.dominio.Producto;

import java.util.List;


public interface ProductoService {
    public List<Producto> getProductos();

    public Producto saveProductos(Producto producto);
}
