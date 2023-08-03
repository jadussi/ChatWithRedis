package com.redis.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30) // open api spec 3.0
        		.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.redis.chat.controller"))	// basic-error-controller 표시 안하기 위해 controller 패키지 경로 지정
                .paths(PathSelectors.any())
                .build();
    }
	
	public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ChatWithRedis API Documentation")
                .description("ChatWithRedis api 문서.")
                .version("0.1")
                .build();
    }
	
	@Bean
	public InternalResourceViewResolver defaultViewResolver() {
	    return new InternalResourceViewResolver();
	    // 리액트 연동 후 Swagger 문서 리다이렉트시 redirect:/swagger-ui/index.html라는 서블릿이 존재하지 않아 추가
	}

}
