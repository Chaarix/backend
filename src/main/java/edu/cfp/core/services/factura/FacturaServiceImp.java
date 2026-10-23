package edu.cfp.core.services.factura;

import edu.cfp.core.controladores.dominio.Factura;
import edu.cfp.core.persistence.repositories.FacturaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Lazy
@Slf4j
@Service
@ConditionalOnProperty(
        value="productos.estrategia",
        havingValue = "MEMORY")

public class FacturaServiceImp implements FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

//
//    private List<Factura> facturas = new ArrayList<>(Arrays.asList(
//            new Factura(1, "Smart tv", 10000.0, 7.0,2.0,"wf", "juan","3546","123","20393"),
//            new Factura(2, "Notebook", 20000.0,9.0,4.0,"MP","Pablo","22","1234","3455"),
//            new Factura(3, "Pan", 2500.0,9.0,4.0,"MP","Carlos","22","12345","3433")
//    ));


    public FacturaServiceImp() {
        log.info("se esta construyendo un objeto de la clase ProductosServiceImp");
    }

    @Override
    public List<Factura> getFactura() {
        return List.of();
    }

    @Override
    public Factura saveFactura(Factura factura) {

        return factura;
    }

    @Override
    public void deleteFacturaById(Integer idFactura) {

    }
}
