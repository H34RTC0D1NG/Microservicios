package com.Japon.Mensajeria.Seguridad;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Value;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

@Component 
public class AdminInterceptor implements HandlerInterceptor {

    @Value("${Token.Secreto}")
    private String Secreto;

    @Override
    public boolean preHandle(HttpServletRequest Peticion, HttpServletResponse Respuesta, Object Handler) throws Exception {

        String Metodo = Peticion.getMethod();

        if (Metodo.equals("Options") || Metodo.equals("POST")) {
            return true;
        }

        String TokenRecibido = Token.DesdeCabecera(Peticion.getHeader("Authorization"));
        String [] Datos =  Token.Validar(TokenRecibido, Secreto);

        if (Datos != null && Datos [2].equals("Admin")) {
            return true;
        }

        Respuesta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Respuesta.setContentType("application/json");
        Respuesta.setCharacterEncoding("UTF-8");
        Respuesta.getWriter().write("{\"Error\" : \"Solo El Administrador Puede Hacer Esto\"}");

        return false;

    }

}