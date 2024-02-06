package com.school.sba.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {

    Contact contact(){
        return new Contact()
        .email("thusharkulal8@gmail.com")
        .url("https://www.linkedin.com/in/thusharp/")
        .name("Thushar P");
    }
    
    Info info(){
        return new Info()
        .title("School Management System")
        .version("v1.0")
        .description("An API used to manage the school functionalities efficiently and effectively with minimal code.")
        .contact(contact());
    }
    
    @Bean
    OpenAPI openAPI(){
        return new OpenAPI().info(info());
    }
    
}
