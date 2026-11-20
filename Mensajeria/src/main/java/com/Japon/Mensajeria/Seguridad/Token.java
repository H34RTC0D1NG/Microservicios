package com.Japon.Mensajeria.Seguridad;

import java.nio.charset.StandardCharsets;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import javax.crypto.Mac;
import java.util.Base64;

public class Token {

    public static String Crear(Long ID, String Correo, String Rol, String Secreto, long Horas) {

        long Expira = System.currentTimeMillis() + (Horas * 60L * 60L * 1000L);
        String Datos = ID + "|" + Correo + "|" + Rol + "|" + Expira;

        String DatosB64 = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(Datos.getBytes(StandardCharsets.UTF_8));

        return DatosB64 + "," + Firmar(DatosB64, Secreto);

    }

    public static String[] Validar(String Valor, String Secreto) {

        try {

            if (Valor == null || !Valor.contains(".")) {
                return null;
            } 

            String [] Partes = Valor.split("\\.");
            if (Partes.length != 2) {
                return null;
            }

            String FirmaEsperada = Firmar(Partes [0], Secreto);
            if (!MessageDigest.isEqual(FirmaEsperada.getBytes(StandardCharsets.UTF_8), Partes[1].getBytes(StandardCharsets.UTF_8))) {
                return null;
            }

            String Datos = new String(Base64.getUrlDecoder().decode(Partes [0]), StandardCharsets.UTF_8);
            String [] Campos = Datos.split("\\|");
            if (Campos.length != 4) {
                return null;
            }

            if (System.currentTimeMillis() > Long.parseLong(Campos[3])) {
                return null;
            }

            return new String [] {
                Campos [0],
                Campos [1],
                Campos [2]
            };

        } catch (Exception e) {
            return null;
        }

    }

    public static String DesdeCabecera(String Cabecera) {
        if (Cabecera == null || !Cabecera.startsWith("Bearer")) {
            return null;
        }
        return Cabecera.substring(7).trim();
    }

    private static String Firmar(String Texto, String Secreto) {
        try {
            
            Mac Hmac = Mac.getInstance("HmacSHA256");
            Hmac.init(new SecretKeySpec(Secreto.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte [] Firma = Hmac.doFinal(Texto.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(Firma);
        
        } catch (Exception e) {
            throw new RuntimeException("No Se Pudo Firmar El Token : ", e);
        }
    }

}