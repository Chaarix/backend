package edu.cfp.core.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoRestController {
    @GetMapping("/hola/{nombreUsu}")
    public String saludo(@PathVariable String nombreUsu){
        System.out.println("Ejecutando saludo");
        return "Hola " + nombreUsu;
    }
}
