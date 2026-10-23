package edu.cfp.core.services.factura;


import edu.cfp.core.controladores.dominio.Factura;
import edu.cfp.core.persistence.entities.FacturaEntity;
import edu.cfp.core.persistence.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("FACTURA_BD")
@ConditionalOnProperty(
        value = "productos.estrategia",
        havingValue = "EN_BD")
public class FacturaServiceBDImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    // 1. OBTENER TODAS LAS FACTURAS DESDE POSTGRESQL
    @Override
    public List<Factura> getFactura() {
        return facturaRepository.findAll().stream()
                .map(facturaEntity -> {
                    Factura factura = new Factura();

                    // CORREGIDO: Usamos .getId() que es la variable real de tu FacturaEntity
                    factura.setId(facturaEntity.getIdFactura());
                    factura.setProducto(facturaEntity.getProducto());
                    factura.setPrecioFinal(facturaEntity.getPrecioUnitario() * facturaEntity.getCantidad());
                    factura.setPrecioUnitario(facturaEntity.getPrecioUnitario());
                    factura.setCantidad(facturaEntity.getCantidad());
                    factura.setMetodoDePago(facturaEntity.getMetodoDePago());
                    factura.setCliente(facturaEntity.getCliente());
                    factura.setFechaEmison(facturaEntity.getFechaEmison());
                    factura.setNumeroDeFactura(facturaEntity.getNumeroDeFactura());
                    factura.setDni(facturaEntity.getDNI());

                    return factura;
                }).collect(Collectors.toList());
    }

    // 2. GUARDAR FACTURA REAL EN POSTGRESQL
    @Override
    public Factura saveFactura(Factura factura) {
        FacturaEntity facturaEntity = new FacturaEntity();

        facturaEntity.setProducto(factura.getProducto());
        facturaEntity.setPrecioUnitario(factura.getPrecioUnitario());
        facturaEntity.setCantidad(factura.getCantidad());
        facturaEntity.setPrecioFinal(factura.getPrecioFinal());
        facturaEntity.setMetodoDePago(factura.getMetodoDePago());
        facturaEntity.setCliente(factura.getCliente());
        facturaEntity.setFechaEmison(factura.getFechaEmison());
        facturaEntity.setNumeroDeFactura(factura.getNumeroDeFactura());
        facturaEntity.setDNI(factura.getDni());

        // Guardamos en la base de datos
        FacturaEntity entityGuardada = facturaRepository.save(facturaEntity);

        // CORREGIDO: Extraemos el ID numérico real mediante .getId()
        factura.setId(entityGuardada.getIdFactura());
        return factura;
    }

    // 3. ELIMINAR FACTURA DE POSTGRESQL POR ID
    @Override
    public void deleteFacturaById(Integer idFactura) {
        if (!facturaRepository.existsById(idFactura)) {
            throw new RuntimeException("La factura con ID " + idFactura + " no existe en la base de datos.");
        }
        facturaRepository.deleteById(idFactura);
    }
}
