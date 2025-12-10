package br.com.leonardomattioli.invetory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InvetoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvetoryServiceApplication.class, args);
	}

}
