package com.Japon.Vuelo.Configuracion;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration 
public class ConfiguracionSwagger {

    @Bean
    public OpenAPI ApiVuelos() {
        return new OpenAPI()
            .info(new Info()
                .title("Microservicio Vuelos - JWL")
                .version("1.0")
                .description("Busqueda, listado y reserva de vuelos a Japon")
            ) .addSecurityItem(new SecurityRequirement().addList("Token"))
            .components(new Components()
                .addSecuritySchemes("Token", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
            )
        );
    }
}