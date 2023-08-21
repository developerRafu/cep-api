package br.com.eai.recruiting.livecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LiveCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveCodeApplication.class, args);
	}

}
