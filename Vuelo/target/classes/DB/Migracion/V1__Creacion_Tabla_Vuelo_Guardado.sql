Create Table Vuelo_Guardado (

    ID              Bigint      Not Null Auto_Increment,
    Usuario_ID      Bigint      Not Null,
    Pasaje_ID       Int         Not Null,
    Ciudad          Varchar(80) Not Null,
    Kanji           Varchar(20) Not Null,
    Codigo          Varchar(10) Not Null,
    Vuelo           Varchar(20) Not Null,
    Fecha           Varchar(20) Not Null,
    Hora            Varchar(10) Not Null,
    Asiento         Varchar(10) Not Null,
    Clase           Varchar(30) Not Null,
    Precio          Varchar(30) Not Null,
    Color           Varchar(20) Not Null,
    Fecha_Guardado  DateTime(6) Not Null,

        Primary Key (ID),
        Unique (
            Usuario_ID, Pasaje_ID
        )

);