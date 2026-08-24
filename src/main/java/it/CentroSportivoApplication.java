package it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CentroSportivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentroSportivoApplication.class, args);
	}

}
