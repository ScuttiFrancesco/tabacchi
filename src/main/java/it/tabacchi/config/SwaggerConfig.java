package it.tabacchi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	//http://localhost:8080/swagger-ui/index.html
    @Bean
	public OpenAPI getSwagger() {

		return new OpenAPI()
				
				.info(new Info()
						.title("REST API TABACCHI")
						.description("Api Rest per gestione attività TABACCHI")
						.version("1.0")
						.termsOfService("http://www......")
				
				.contact(new io.swagger.v3.oas.models.info.Contact()
						.name("Support Api")
						.email("scufranc@yahoo.com"))
				
				.license(new License()
						.name("Licence Api 1.0")
						.url("http://www......")));

	}

}
