package com.Japon.Auth.Seguridad;

import java.nio.charset.StandardCharsets;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import javax.crypto.Mac;
import java.util.Base64;

public class Token {

    public static String Firmar(String Texto, String Secreto) {
        try {
            Mac Hmac = Mac.getInstance("HmacSHA256");
            Hmac.init(new SecretKeySpec(Secreto.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte [] Firma = Hmac.doFinal(Texto.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(Firma);
        } catch (Exception e) {
            throw new RuntimeException("[+] No Se Pudo Firmar El Token", e);
        }
    }

    public static String Crear(Long ID, String Correo, String Rol, String Secreto, long Horas) {
        long Expira = System.currentTimeMillis() + (Horas * 60L * 60L * 1000L);
        String Datos = ID + "|" + Correo + "|" + Rol + "|" + Expira;

        String DatosB64 = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(Datos.getBytes(StandardCharsets.UTF_8));

        return DatosB64 + "." + Firmar(DatosB64, Secreto);
    }

    public static String [] Validar(String Valor, String Secreto) {
        try {
            System.out.println("[DEBUG] Token recibido: " + Valor);

            if (Valor == null || !Valor.contains(".")) {
                System.out.println("[DEBUG] Token nulo o sin punto");
                return null;
            }

            String [] Partes = Valor.split("\\.");
            if (Partes.length != 2) {
                System.out.println("[DEBUG] Partes: " + Partes.length);
                return null;
            }

            String FirmaEsperada = Firmar(Partes [0], Secreto);
            if (!MessageDigest.isEqual(FirmaEsperada.getBytes(StandardCharsets.UTF_8), Partes [1].getBytes(StandardCharsets.UTF_8))) {
                System.out.println("[DEBUG] Firma no coincide");
                return null;
            }

            String Datos = new String(Base64.getUrlDecoder().decode(Partes [0]), StandardCharsets.UTF_8);
            String [] Campos = Datos.split("\\|");
            if (Campos.length != 4) {
                System.out.println("[DEBUG] Campos: " + Campos.length);
                return null;
            }

            if (System.currentTimeMillis() > Long.parseLong(Campos [3])) {
                System.out.println("[DEBUG] Token vencido");
                return null;
            }

            return new String [] {
                Campos [0], Campos [1], Campos [2]
            };

        } catch (Exception e) {
            System.out.println("[DEBUG] Error: " + e.getMessage());
            return null;
        }
    }

    public static String DesdeCabecera(String Cabecera) {
        if (Cabecera == null || !Cabecera.startsWith("Bearer ")) {
            return null;
        }
        return Cabecera.substring(7).trim();
    }

}