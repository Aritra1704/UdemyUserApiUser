package io.arpaul.UdemyUserApiUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
public class UdemyUserApiUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdemyUserApiUserApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bcyBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
