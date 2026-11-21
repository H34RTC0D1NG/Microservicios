Create Table Usuario (

    ID               Bigint       Not Null Auto_Increment,
    Nombre_Completo  Varchar(150) Not Null,
    Correo           Varchar(150) Not Null,
    Contraseña       Varchar(255) Not Null,
    Rol              Varchar (20) Not Null Default 'Usuario',
    Fecha_Registro   DateTime( 6) Not Null,

        Primary Key (ID),
        Unique (Correo)

);