package edu.cfp.core.controladores;

import edu.cfp.core.configuraciones.ConfigurationParameters;

import edu.cfp.core.controladores.dominio.Factura;
import edu.cfp.core.controladores.dominio.Producto;
import edu.cfp.core.services.factura.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
@CrossOrigin(origins = "*")
public class FacturaRestController {

    @Autowired
    @Lazy
    private FacturaService facturaService;

    @Autowired
    private ConfigurationParameters configuracionParameters;


    @GetMapping
    public ResponseEntity<?> getFactura() {
        System.out.println("params: " + configuracionParameters.toString());
        List<Factura> facturas = facturaService.getFactura();
        return ResponseEntity.ok(facturas);
    }

    @PostMapping
    public ResponseEntity<?> altaFactura(@RequestBody Factura factura) {
        try {
            // 1. Validamos que nos manden productos
            if (factura.getProductos() == null || factura.getProductos().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: La lista de productos no puede estar vacía.");
            }

            // 2. Calculamos el precio total sumando (precio * cantidad) de cada producto de la lista
            double totalCalculado = factura.getProductos().stream()
                    .mapToDouble(p -> (p.getPrecio() != null ? p.getPrecio() : 0.0) * (p.getCantidad() != null ? p.getCantidad() : 1.0))
                    .sum();
            factura.setPrecioFinal(totalCalculado);

            // 3. Juntamos los nombres de los productos en un solo String separado por comas para guardarlo en la BD
            String nombresUnidos = factura.getProductos().stream()
                    .map(Producto::getNombre)
                    .collect(java.util.stream.Collectors.joining(", "));
            factura.setProducto(nombresUnidos);

            // 4. Seteamos valores genéricos o acumulados para los campos individuales obligatorios de tu BD
            factura.setCantidad((double) factura.getProductos().size()); // Cantidad de ítems diferentes
            factura.setPrecioUnitario(totalCalculado);

            // 5. Guardamos físicamente en PostgreSQL
            Factura facturaGuardada = facturaService.saveFactura(factura);

            return ResponseEntity.status(HttpStatus.CREATED).body(facturaGuardada);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la factura: " + e.getMessage());
        }
    }



    // 3. ELIMINAR FACTURA DE POSTGRESQL POR ID
    @DeleteMapping("/{idFactura}")
    public ResponseEntity<?> deleteFactura(@PathVariable Integer idFactura) {
        try {
            // Se envía el ID directamente al servicio para que lo borre de Postgres
            facturaService.deleteFacturaById(idFactura);

            System.out.println("¡Factura eliminada con éxito de la BD!: ID " + idFactura);
            return ResponseEntity.noContent().build(); // Retorna Código 204 (Éxito, sin contenido)
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se pudo eliminar la factura: " + e.getMessage()); // Código 404
        }
    }
}
