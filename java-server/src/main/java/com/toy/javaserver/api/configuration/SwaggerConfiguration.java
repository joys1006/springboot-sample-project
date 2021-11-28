package com.toy.javaserver.api.configuration;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AuthorizationScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(
                        Arrays.asList(
                                new ParameterBuilder()
                                    .name("X-AUTH-TOKEN")
                                    .description("ACCESS-TOKEN")
                                    .modelRef(new ModelRef("string"))
                                    .parameterType("header")
                                    .defaultValue("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MSIsImlhdCI6MTYzODExMTI0OCwiZXhwIjoxNjM4MTE0ODQ4fQ.RsBralAefu_O21t3jfzzi8INAm-xVpO7EkKq5cSDlrg")
                                    .required(true)
                                    .build()
                        )
                )
                .securitySchemes(Lists.newArrayList(
                        new ApiKey("JWT", "X-AUTH-TOKEN", "header")
                ))
                .securityContexts(Lists.newArrayList(securityContext()))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }

    @Bean
    public SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private ArrayList<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Toy - JavaServer",
                "Toy - JavaServer",
                "1.0.0",
                "Terms of service",
                new Contact("Has", "dd", "dd"),
                "License of API", "API license URL", Collections.emptyList());
    }

}
