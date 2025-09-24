-- Drop database if exists DBAhorcado;
Create database DBAhorcado;
Use DBAhorcado;

-- tabla de usuarios
create table usuarios (
    codigo_usuario int auto_increment,
    nombre_usuario varchar(50) not null,
    apellido_usuario varchar(50) not null,
    correo_usuario varchar(100) not null unique,
    contraseña_usuario varchar(100) not null,
    fecha_registro datetime not null,
    primary key (codigo_usuario)
);

-- tabla de palabras
create table palabras (
    codigo_palabra int auto_increment,
    palabra varchar(100) not null,
    pista varchar(200) not null,
    categoria varchar(50) not null,
    primary key (codigo_palabra)
);

-- agregar usuario
delimiter //
create procedure sp_agregarusuario(
    in _nombre_usuario varchar(50),
    in _apellido_usuario varchar(50),
    in _correo_usuario varchar(100),
    in _contraseña_usuario varchar(100)
)
begin
    insert into usuarios(nombre_usuario, apellido_usuario, correo_usuario, contraseña_usuario, fecha_registro)
    values(_nombre_usuario, _apellido_usuario, _correo_usuario, _contraseña_usuario, now());
end //
delimiter ;
call sp_agregarusuario('Juan', 'Pérez', '1@gmail.com', '1');
call sp_agregarusuario('María', 'García', 'maria.garcia@gmail.com', 'abc123');
call sp_agregarusuario('Carlos', 'López', 'carlos.lopez@gmail.com', 'qwerty');
call sp_agregarusuario('Ana', 'Martínez', 'ana.martinez@gmail.com', 'pass123');
call sp_agregarusuario('Luis', 'Rodríguez', 'luis.rodriguez@gmail.com', 'secure456');

-- agregar palabra
delimiter //
create procedure sp_agregarpalabra(
    in _palabra varchar(100),
    in _pista varchar(200),
    in _categoria varchar(50)
)
begin
    insert into palabras(palabra, pista, categoria)
    values(_palabra, _pista, _categoria);
end //
delimiter ;
call sp_agregarpalabra('SPRINGBOOT', 'FRAMEWORK DE JAVA QUE FACILITA EL DESARROLLO DE APLICACIONES WEB Y MICROSERVICIOS', 'framework');
call sp_agregarpalabra('THYMELEAF', 'MOTOR DE PLANTILLAS PARA APLICACIONES JAVA QUE PERMITE CREAR VISTAS HTML', 'template');
call sp_agregarpalabra('POSTGRES', 'SISTEMA DE GESTIÓN DE BASE DE DATOS RELACIONAL DE CÓDIGO ABIERTO', 'database');
call sp_agregarpalabra('CONEXION', 'PROCESO QUE PERMITE LA COMUNICACIÓN ENTRE LA APLICACIÓN Y LA BASE DE DATOS', 'concepto');
call sp_agregarpalabra('RESTFULL', 'ESTILO DE ARQUITECTURA DE SOFTWARE PARA SISTEMAS DISTRIBUIDOS', 'arquitectura');
call sp_agregarpalabra('FRONTEND', 'PARTE DE UNA APLICACIÓN QUE INTERACTÚA DIRECTAMENTE CON LOS USUARIOS', 'desarrollo');
call sp_agregarpalabra('BACKENDO', 'PARTE DE LA APLICACIÓN QUE PROCESA LA ENTRADA DESDE EL FRONTEND', 'desarrollo');
call sp_agregarpalabra('SERVICIOS', 'COMPONENTES QUE PROPORCIONAN LÓGICA DE NEGOCIO REUTILIZABLE EN LA APLICACIÓN', 'arquitectura');
call sp_agregarpalabra('CONTROLADOR', 'COMPONENTE QUE MANEJA LAS SOLICITUDES HTTP Y DEFINE LOS ENDPOINTS', 'estructura');
call sp_agregarpalabra('ENTITIES', 'CLASES QUE REPRESENTAN LAS TABLAS DE LA BASE DE DATOS EN EL CÓDIGO', 'modelo');
call sp_agregarpalabra('REPOSITORY', 'INTERFAZ QUE PROPORCIONA OPERACIONES DE ACCESO A LA BASE DE DATOS', 'patron');
call sp_agregarpalabra('INYECCION', 'PATRÓN DE DISEÑO QUE IMPLEMENTA LA INVERSIÓN DE CONTROL', 'concepto');
call sp_agregarpalabra('ENDPOINTS', 'PUNTOS DE ACCESO DE LA API PARA INTERACTUAR CON LA APLICACIÓN', 'api');
call sp_agregarpalabra('DESPLIEGUE', 'PROCESO DE PONER UNA APLICACIÓN EN UN ENTORNO DE PRODUCCIÓN', 'operacion');
call sp_agregarpalabra('PROTOCOLS', 'CONJUNTO DE REGLAS QUE PERMITEN LA COMUNICACIÓN EN LA RED', 'networking');
call sp_agregarpalabra('BOOTSTRAP', 'FRAMEWORK PARA DISEÑO DE SITIOS Y APLICACIONES WEB RESPONSIVE', 'frontend');
call sp_agregarpalabra('HIBERNATE', 'FRAMEWORK DE PERSISTENCIA QUE FACILITA EL MAPEO OBJETO-RELACIONAL', 'persistencia');
call sp_agregarpalabra('VARIABLES', 'CONTENEDORES DE INFORMACIÓN QUE PUEDEN CAMBIAR DURANTE LA EJECUCIÓN', 'programacion');
call sp_agregarpalabra('METODOLOGIA', 'CONJUNTO DE MÉTODOS Y TÉCNICAS PARA GESTIONAR UN PROYECTO DE SOFTWARE', 'gestion');
call sp_agregarpalabra('DEPLOYMENT', 'PROCESO DE IMPLEMENTACIÓN Y CONFIGURACIÓN DE SOFTWARE EN SERVIDORES', 'operaciones');

-- eliminar usuario
delimiter //
create procedure sp_eliminarusuario(
    in _codigo_usuario int
)
begin
    delete from usuarios where codigo_usuario = _codigo_usuario;
    select row_count() as filaseliminadas;
end //
delimiter ;

-- eliminar palabra
delimiter //
create procedure sp_eliminarpalabra(
    in _codigo_palabra int
)
begin
    delete from palabras where codigo_palabra = _codigo_palabra;
    select row_count() as filaseliminadas;
end //
delimiter ;

-- buscar usuario
delimiter //
create procedure sp_buscarusuario(
    in _codigo_usuario int
)
begin
    select * from usuarios where codigo_usuario = _codigo_usuario;
end //
delimiter ;

-- buscar palabra
delimiter //
create procedure sp_buscarpalabra(
    in _codigo_palabra int
)
begin
    select * from palabras where codigo_palabra = _codigo_palabra;
end //
delimiter ;

-- editar usuario
delimiter //
create procedure sp_editarusuario(
    in _codigo_usuario int,
    in _nombre_usuario varchar(50),
    in _apellido_usuario varchar(50),
    in _correo_usuario varchar(100),
    in _contraseña_usuario varchar(100)
)
begin
    update usuarios
    set nombre_usuario = _nombre_usuario,
        apellido_usuario = _apellido_usuario,
        correo_usuario = _correo_usuario,
        contraseña_usuario = _contraseña_usuario
    where codigo_usuario = _codigo_usuario;
end //
delimiter ;

-- editar palabra
delimiter //
create procedure sp_editarpalabra(
    in _codigo_palabra int,
    in _palabra varchar(100),
    in _pista varchar(200),
    in _categoria varchar(50)
)
begin
    update palabras
    set palabra = _palabra,
        pista = _pista,
        categoria = _categoria
    where codigo_palabra = _codigo_palabra;
end //
delimiter ;

-- listar usuarios 
delimiter //
create procedure sp_listarusuarios()
begin
    select * from usuarios;
end //
delimiter ;
call sp_listarusuarios();

-- listar palabras 
delimiter //
create procedure sp_listarpalabras()
begin
    select * from palabras;
end //
delimiter ;
call sp_listarpalabras();

-- obtener palabra random 
delimiter //
create procedure sp_obtenerpalabrarandom()
begin
    select * from palabras order by rand() limit 1;
end //
delimiter ;

