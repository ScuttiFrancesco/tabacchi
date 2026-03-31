package it.tabacchi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TabacchiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TabacchiApplication.class, args);
	}

}
