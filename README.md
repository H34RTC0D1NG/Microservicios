# Microservicios

> Actualización de que contiene cada microservicio : 

``` JSON

{
    "Microservicios" : [
        {
            "Usuarios" : [
                {
                    "Carpeta" : "Auth",
                    "Puerto" : 8083,
                    "Base_Datos" : "MySQL - Usuarios",
                    "Ruta_Base" : "/Api/Usuarios",
                    "Descripcion" : "Maneja el registro, inicio de sesion y perfil de los usuarios. Genera el token que usan los otros microservicios.",
                    "Tabla" : {
                        "Nombre" : "Usuario",
                        "Columnas" : ["ID", "Nombre_Completo", "Correo", "Contraseña", "Rol", "Fecha_Registro"]
                    },
                    "Endpoints" : [
                        { "Metodo" : "POST", "Ruta" : "/Registro", "Hace" : "Crea una cuenta nueva con Nombre_Completo, Correo y Pass (rol Usuario)" },
                        { "Metodo" : "POST", "Ruta" : "/Login", "Hace" : "Valida correo y contraseña y devuelve el Token y los datos del usuario" },
                        { "Metodo" : "GET", "Ruta" : "/Perfil", "Hace" : "Devuelve los datos del usuario segun el Token (header Authorization)" }
                    ],
                    "Clases" : {
                        "Controlador" : "UsuarioControlador",
                        "Servicio" : ["UsuarioServicio", "AdminInicial", "UsuarioException"],
                        "Modelo" : "Usuario",
                        "Repositorio" : "UsuarioRepositorio",
                        "Seguridad" : ["Token", "ClaveToken", "ConfigSeguridad", "CuentaAdmin"]
                    },
                    "Detalles" : [
                        "Contraseñas encriptadas con BCrypt",
                        "Token propio firmado con HmacSHA256, dura 8 horas",
                        "Al iniciar crea o actualiza la cuenta Admin automaticamente (AdminInicial)",
                        "Valida correo con regex y contraseña de minimo 6 caracteres",
                        "Migracion Flyway : V1__Crear_Tabla_Usuarios.sql"
                    ]
                }
            ]
        },
        {
            "Mensajeria" : [
                {
                    "Carpeta" : "Mensajeria",
                    "Puerto" : 8082,
                    "Base_Datos" : "MySQL - mensajeria",
                    "Ruta_Base" : "/Api/Contactos",
                    "Descripcion" : "Guarda los mensajes del formulario de contacto y permite que el Admin los lea, responda o elimine.",
                    "Tabla" : {
                        "Nombre" : "Contacto",
                        "Columnas" : ["ID", "Nombre_Completo", "Correo", "Mensaje", "Fecha_Creacion", "Estado", "Respuesta", "Fecha_Respuesta"]
                    },
                    "Endpoints" : [
                        { "Metodo" : "POST", "Ruta" : "/", "Hace" : "Guarda un mensaje nuevo con estado Pendiente (publico)" },
                        { "Metodo" : "GET", "Ruta" : "/", "Hace" : "Lista todos los mensajes (solo Admin)" },
                        { "Metodo" : "PUT", "Ruta" : "/{ID}/Responder", "Hace" : "Agrega la respuesta y cambia el estado a Respondido (solo Admin)" },
                        { "Metodo" : "DELETE", "Ruta" : "/{ID}", "Hace" : "Elimina un mensaje (solo Admin)" }
                    ],
                    "Clases" : {
                        "Controlador" : "MensajeriaControlador",
                        "Servicio" : "MensajeriaServicio",
                        "Modelo" : "Mensajeria",
                        "Repositorio" : "MensajeriaRepositorio",
                        "Seguridad" : ["Token", "ClaveToken", "AdminInterceptor", "WebConfig"]
                    },
                    "Detalles" : [
                        "AdminInterceptor deja pasar POST y OPTIONS, lo demas exige Token con rol Admin",
                        "Usa la misma clave secreta que Auth para validar el Token",
                        "Migraciones Flyway : V1 crea Contacto, V2 agrega Estado, V3 agrega Respuesta y Fecha_Respuesta"
                    ]
                }
            ]
        },
        {
            "Vuelos" : [
                {
                    "Carpeta" : "Vuelo",
                    "Puerto" : 8084,
                    "Base_Datos" : "MySQL - Vuelos",
                    "Ruta_Base" : "/Api/Vuelos",
                    "Descripcion" : "Permite que un usuario logueado guarde, vea y quite pasajes de vuelo a Japon.",
                    "Tabla" : {
                        "Nombre" : "Vuelo_Guardado",
                        "Columnas" : ["ID", "Usuario_ID", "Pasaje_ID", "Ciudad", "Kanji", "Codigo", "Vuelo", "Fecha", "Hora", "Puerta", "Asiento", "Clase", "Precio", "Color", "Fecha_Guardado"]
                    },
                    "Endpoints" : [
                        { "Metodo" : "GET", "Ruta" : "/", "Hace" : "Lista los vuelos guardados del usuario del Token" },
                        { "Metodo" : "POST", "Ruta" : "/", "Hace" : "Guarda un pasaje, si ya estaba guardado devuelve el mismo" },
                        { "Metodo" : "DELETE", "Ruta" : "/{PasajeID}", "Hace" : "Quita un pasaje guardado del usuario" }
                    ],
                    "Clases" : {
                        "Controlador" : "VueloControlador",
                        "Servicio" : ["VueloServicio", "VueloException"],
                        "Modelo" : "VueloGuardado",
                        "Repositorio" : "VueloRepositorio",
                        "Seguridad" : ["Token", "ClaveToken"]
                    },
                    "Detalles" : [
                        "Todos los endpoints piden Token, saca el ID del usuario desde ahi",
                        "Un usuario no puede guardar el mismo pasaje dos veces (Unique Usuario_ID + Pasaje_ID)",
                        "Migraciones Flyway : V1 crea Vuelo_Guardado, V2 agrega Puerta"
                    ]
                }
            ]
        }
    ],

    "Comun" : {
        "Tecnologias" : ["Java 21", "Spring Boot 4.1.2", "Spring Data JPA", "Flyway", "MySQL", "Lombok"],
        "Frontend_Permitido" : "http://localhost:5173",
        "Estructura" : "Controlador -> Servicio -> Repositorio -> Modelo"
    }
}

```
