package com.gmf.attusapiRest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("RESTful API para gerenciamento de pessoas.")
				.version("v1")
				.description("API RESTful criada para teste técnico para vaga de desenvolvedor.")
				.termsOfService("https://attusapi.com.br/teste-api")
				.license(new License()
					.name("Apache 2.0")
					.url("https://attusapi.com.br/teste-api")
					)
				);
	}
}
