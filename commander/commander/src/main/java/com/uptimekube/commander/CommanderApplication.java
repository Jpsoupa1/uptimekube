package com.uptimekube.commander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CommanderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommanderApplication.class, args);
	}

}
