package com.ies.blossom;

import com.ies.blossom.encoders.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlossomApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlossomApplication.class, args);
	}

//	@Bean
//	public PasswordEncoder encoder() {
//		return new BCryptPasswordEncoder();
//	}

}
