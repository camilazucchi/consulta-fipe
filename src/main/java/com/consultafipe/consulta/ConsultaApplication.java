package com.consultafipe.consulta;

import com.consultafipe.consulta.principal.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Menu menu = new Menu();
		menu.mostraMenu();
	}
}
