package com.janwarlen.config;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(buildApiInfo()).select().apis(RequestHandlerSelectors
                .any()).paths(PathSelectors.regex("/.*")).build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().title("common").description("common").termsOfServiceUrl("No terms of service")
                .version("1.0.0").build();
    }

}
