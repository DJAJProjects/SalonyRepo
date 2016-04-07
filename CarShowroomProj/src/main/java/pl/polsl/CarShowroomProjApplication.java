package pl.polsl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
@EnableScheduling
public class CarShowroomProjApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
			System.out.println("SpringApplicationBuilder configure");
			return application.sources(CarShowroomProjApplication.class);
		}
	    public static void main(String[] args) {
			System.setProperty("spring.thymeleaf.cache","false");
			SpringApplication.run(CarShowroomProjApplication.class, args);
			System.out.println("END");
		}

}
