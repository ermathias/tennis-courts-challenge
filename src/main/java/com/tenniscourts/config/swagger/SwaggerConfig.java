package com.tenniscourts.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.ArrayList;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tenniscourt"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }
    private ApiInfo metaInfo(){
        ApiInfo apiInfo = new ApiInfo(
                "API REST",
                "API REST for schedule Tenis Courts",
                "1.0",
                "Terms of Service",
                new Contact("Ricardo Machado", "https://github.com/wolwerr",
                        "ricardo@dtmm.com.br"),
                        "Apache License Version 2.0",
                        "https://www.apache.org/license.html", new ArrayList<>()
        );
        return apiInfo;
    }
}
