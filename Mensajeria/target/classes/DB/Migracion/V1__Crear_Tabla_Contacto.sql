Create Table Contacto (

    ID                  bigint       Not Null Auto_Increment,
    Nombre_Completo     Varchar(150) Not Null,
    Correo              Varchar(150) Not Null,
    Mensaje             Text         Not Null,
    Fecha_Creacion      DateTime(6)  Not Null,
    
        Primary Key (ID)

);