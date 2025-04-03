package com.brasiltec23.tinnova;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Desafio TINNOVA", version = "1.0"))
@ComponentScan("com.brasiltec23.tinnova")
public class TesteDaTinnovaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteDaTinnovaApplication.class, args);
	}

}
