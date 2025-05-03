package com.kata.boats;

import org.springframework.boot.SpringApplication;

public class TestBuoyancyOperatedAquaticTransportSocietyApplication {

	public static void main(String[] args) {
		SpringApplication.from(BuoyancyOperatedAquaticTransportSocietyApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
