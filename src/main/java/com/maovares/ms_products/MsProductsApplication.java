package com.maovares.ms_products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsProductsApplication {

	public static void main(String[] args) {
		String mongoUri = System.getenv("MONGO_URI");
		System.out.println("DIAG MONGO_URI present=" + (mongoUri != null) + " length=" + (mongoUri == null ? 0 : mongoUri.length()));
		SpringApplication.run(MsProductsApplication.class, args);
	}

}
