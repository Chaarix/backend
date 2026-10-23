package edu.cfp.core.configuraciones;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class ConfigurationParameters {

    private String nombre;
    private String pais;
    private String author;
    private String lenguaje;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public ConfigurationParameters(){
    }

    @Override
    public String toString() {
        return "ConfigurationParameters{" +
                "nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                ", autor='" + author + '\'' +
                ", lenguaje='" + lenguaje + '\'' +
                '}';
    }
}
