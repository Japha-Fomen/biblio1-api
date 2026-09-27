package com.example.biblio1_api;

import com.example.biblio1_api.Config.ProprietesBiblio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ProprietesBiblio.class)
public class Biblio1ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Biblio1ApiApplication.class, args);
	}

}
