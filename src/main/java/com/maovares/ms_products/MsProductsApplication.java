package com.maovares.ms_products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MsProductsApplication {

	public static void main(String[] args) {
		String mongoUri = System.getenv("MONGO_URI");
		System.out.println("DIAG MONGO_URI present=" + (mongoUri != null) + " length=" + (mongoUri == null ? 0 : mongoUri.length()));
		ConfigurableApplicationContext ctx = SpringApplication.run(MsProductsApplication.class, args);
		String resolvedUri = ctx.getEnvironment().getProperty("spring.data.mongodb.uri");
		String resolvedDb = ctx.getEnvironment().getProperty("spring.data.mongodb.database");
		String resolvedHost = ctx.getEnvironment().getProperty("spring.data.mongodb.host");
		String resolvedPort = ctx.getEnvironment().getProperty("spring.data.mongodb.port");
		System.out.println("DIAG spring.data.mongodb.uri length=" + (resolvedUri == null ? -1 : resolvedUri.length())
				+ " startsWith=" + (resolvedUri == null ? "null" : resolvedUri.substring(0, Math.min(12, resolvedUri.length()))));
		System.out.println("DIAG spring.data.mongodb.database=" + resolvedDb);
		System.out.println("DIAG spring.data.mongodb.host=" + resolvedHost + " port=" + resolvedPort);
		try {
			com.mongodb.ConnectionString cs = new com.mongodb.ConnectionString(resolvedUri);
			System.out.println("DIAG ConnectionString.isSrvProtocol=" + cs.isSrvProtocol()
					+ " hosts=" + cs.getHosts());
		} catch (Exception e) {
			System.out.println("DIAG ConnectionString parse failed: " + e);
		}
	}

}
