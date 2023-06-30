package com.devnatao.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
		System.out.println("Development password: " + new BCryptPasswordEncoder().encode("2001"));
		System.out.println("2nd Development password: " + new BCryptPasswordEncoder().encode("2013"));
	}

}
