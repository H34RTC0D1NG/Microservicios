package com.Japon.Mensajeria.Seguridad;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

@Component 
public class AdminInterceptor implements HandlerInterceptor {

   @Override 
   public boolean preHandle(HttpServletRequest Peticion, HttpServletResponse Respuesta, Object Handler) throws Exception {
        String Metodo = Peticion.getMethod();
        if (Metodo.equalsIgnoreCase("OPTIONS") || Metodo.equals("POST")) {
            return true;
        }

        String TokenRecibido = Token.DesdeCabecera(Peticion.getHeader("Authorization"));
        String [] Datos = Token.Validar(TokenRecibido, ClaveToken.SECRETO);

        if (Datos != null && Datos [2].equalsIgnoreCase("ADMIN")) {
            return true;
        }

        Respuesta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Respuesta.setContentType("application/json");
        Respuesta.setCharacterEncoding("UTF-8");
        Respuesta.getWriter().write("{\"Error\" : \"Solo El ADMIN Puede Hacer Esto\"}");

        return false;

   }

}