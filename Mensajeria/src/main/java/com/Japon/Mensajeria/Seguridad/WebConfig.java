package com.Japon.Mensajeria.Seguridad;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminInterceptor Interceptor;

    public WebConfig(AdminInterceptor Interceptor) {
        this.Interceptor = Interceptor;
    }

    @Override 
    public void addInterceptors(InterceptorRegistry Registro) {
        Registro.addInterceptor(Interceptor)
            .addPathPatterns("/Api/Contactos", "/Api/Contactos/**");
    }

}