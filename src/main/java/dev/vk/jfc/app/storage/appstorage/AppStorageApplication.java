package dev.vk.jfc.app.storage.appstorage;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppStorageApplication.class, args);
	}

}
