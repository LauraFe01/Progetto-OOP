package com.ConfrontoSpeedVento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe che avvia l'applicazione di Spring.
 * @author Edoardo Cecchini
 * @author Laura Ferretti
 */

@SpringBootApplication(scanBasePackages={"com.ConfrontoSpeedVento.controller", "com.ConfrontoSpeedVento.services","com.ConfrontoSpeedVento.model"})
public class ConfrontoSpeedVentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfrontoSpeedVentoApplication.class, args);
	}

}
