package br.com.leonardomattioli.ecommerce.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UsersService {

	public static void main(String[] args) {
		SpringApplication.run(UsersService.class, args);
	}

}
