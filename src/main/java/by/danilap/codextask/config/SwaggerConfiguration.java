package by.danilap.codextask.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("by.danilap.codextask"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }


    private ApiInfo metaData() {
        return new ApiInfo(
                "Codex Task",
                "Simple shopping REST API",
                "1.0",
                "Terms of service",
                new Contact("Danila Paramonov", "https://github.com/danikk812", "danilaparamonau@gmail.com"),
                "",
                "",
                new ArrayList<>());
    }
}
