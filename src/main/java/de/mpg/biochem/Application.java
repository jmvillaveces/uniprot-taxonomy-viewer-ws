package de.mpg.biochem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ComponentScan
@EnableAutoConfiguration
@Configuration
@ImportResource("classpath:META-INF/spring/app-context.xml")
public class Application {
    
	public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

}