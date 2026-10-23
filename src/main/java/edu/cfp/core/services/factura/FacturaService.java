package edu.cfp.core.services.factura;

import edu.cfp.core.controladores.dominio.Factura;

import java.util.List;

public interface FacturaService {

        public List<Factura>getFactura();


        public Factura saveFactura(Factura factura);

     void deleteFacturaById(Integer idFactura);

    }


