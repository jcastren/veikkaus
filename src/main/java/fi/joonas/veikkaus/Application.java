package fi.joonas.veikkaus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

import fi.joonas.veikkaus.config.VeikkausServerProperties;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties(VeikkausServerProperties.class)
public class Application extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		logger.info("Start application");
		SpringApplication.run(Application.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
