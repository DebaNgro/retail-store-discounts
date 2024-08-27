package com.org.retailstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Value("${retailstore.openapi.url}")
    private String url;

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl(url);
        server.setDescription("Server URL in Development environment");

        Contact myContact = new Contact();
        myContact.setName("Debasish Biswas");
        myContact.setEmail("debasish.biswas@nagarro.com");

        Info information = new Info()
                .title("Retail Store Discounts System API")
                .version("1.0")
                .description("This API provides endpoints to apply discounts to products based on the type of customer.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}