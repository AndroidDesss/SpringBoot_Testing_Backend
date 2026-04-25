package com.desss.zcms.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI zmartCmsOpenAPI() {

        // JWT Bearer security scheme
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
            .info(new Info()
                .title("zmartCMS API")
                .description("""
                    ## zmartCMS v2 — REST API Documentation
                    
                    A headless CMS backend providing content management for websites.
                    
                    ### Authentication
                    1. Call `POST /api/auth/login` with your credentials.
                    2. Copy the `accessToken` from the response.
                    3. Click **Authorize** (🔒) at the top and paste: `Bearer <your_token>`
                    4. All protected endpoints will now work.
                    
                    ### Roles
                    - **ADMIN** — Can manage all CMS content for their assigned website.
                    - **SUPER_ADMIN** — Full access including mail config and all websites.
                    
                    ### Rate Limits
                    - Auth endpoints: **5 requests / minute / IP**
                    - Public endpoints: **60 requests / minute / IP**
                    """)
                .version("2.0.0")
                .contact(new Contact()
                    .name("Desss Inc.")
                    .email("support@desss.com"))
                .license(new License()
                    .name("Proprietary")
                    .url("https://desss.com")))

            .servers(List.of(
                new Server()
                    .url("http://localhost:" + serverPort)
                    .description("Local Development Server")
            ))

            // Register JWT bearer scheme
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Enter your JWT access token. Obtain it from POST /api/auth/login")));
    }
}
