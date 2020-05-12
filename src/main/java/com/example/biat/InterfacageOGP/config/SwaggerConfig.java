package com.example.biat.InterfacageOGP.config;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@Configuration
public class SwaggerConfig {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Collection.class, Instant.class),

						typeResolver.resolve(Collection.class, Map.class)))
				.select().
				apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")

				// .paths(regex("/biat.*"))
			      .apiInfo(metaData());

	}

	@Autowired
	private TypeResolver typeResolver;

	private ApiInfo metaData() {

		Contact contact = new Contact("Direction Méthodes et outils", "http://172.28.70.74:8090/OVTOOLS/login.do",
				"aymen.braiek@biat.com.tn");

		return new ApiInfo("Interfacage OV_OGP", "Rest API : OGP", "1.0", "", contact, "Apache License Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0", new ArrayList<>());
	}

}
