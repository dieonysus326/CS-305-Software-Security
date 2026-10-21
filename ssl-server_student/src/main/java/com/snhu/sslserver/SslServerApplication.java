package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Artemis Financial SSL server.
 *
 * The server is configured for HTTPS only in application.properties. The
 * checksum route lives in {@link ServerController} and the hashing logic lives
 * in {@link ChecksumGenerator} so each class has a single responsibility.
 */
@SpringBootApplication
public class SslServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SslServerApplication.class, args);
	}

}
