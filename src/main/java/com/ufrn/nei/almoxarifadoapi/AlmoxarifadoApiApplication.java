package com.ufrn.nei.almoxarifadoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AlmoxarifadoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlmoxarifadoApiApplication.class, args);
	}

}
