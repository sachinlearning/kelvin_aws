package com.heatmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;


@SpringBootApplication
@EnableScheduling
public class ExcelConsolidatorSmallAppApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ExcelConsolidatorSmallAppApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(ExcelConsolidatorSmallAppApplication.class, args);
	}
	
	@Bean
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {

        RequestMappingHandlerAdapter rmha = new RequestMappingHandlerAdapter();
        rmha.setCacheSeconds(0);
        return rmha;
    }

}
