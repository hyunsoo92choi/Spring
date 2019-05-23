package com.eBayJP.kuromoji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class KuromojiRestServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KuromojiRestServerApplication.class, args);
	}

}
