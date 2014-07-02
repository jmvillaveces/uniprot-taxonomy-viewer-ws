package de.mpg.biochem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import de.mpg.biochem.model.TreeManager;

@ComponentScan
@EnableAutoConfiguration
@Configuration
@ImportResource("classpath:META-INF/spring/app-context.xml")
public class Application {
    
	public static void main(String[] args) {
		
		if(args.length >0) {
			ApplicationContext ctx = SpringApplication.run(Application.class, args);
			
			TreeManager man = (TreeManager) ctx.getBean("taxonomyMan");
			man.setFilePath(args[0]);
			try {
				man.createTree();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
    }

}