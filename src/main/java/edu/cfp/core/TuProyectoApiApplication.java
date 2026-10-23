package edu.cfp.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;


import java.util.TimeZone;


@SpringBootApplication
public class TuProyectoApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));

		SpringApplication.run(TuProyectoApiApplication.class, args);

	}

}