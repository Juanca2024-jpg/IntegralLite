
-- Usuario lógico para la app
CREATE USER comercio IDENTIFIED BY comercio_pwd
  DEFAULT TABLESPACE users
  QUOTA UNLIMITED ON users;
GRANT CONNECT, RESOURCE TO comercio;

ALTER SESSION SET CURRENT_SCHEMA = comercio;

-- ---------------------------
-- TABLA USUARIO
-- ---------------------------
CREATE TABLE usuario (
    id_usuario        NUMBER        PRIMARY KEY,
    nombre            VARCHAR2(100) NOT NULL,
    correo            VARCHAR2(150) NOT NULL UNIQUE,
    contrasena        VARCHAR2(200) NOT NULL,
    rol               VARCHAR2(20)  NOT NULL CHECK (rol IN ('ADMINISTRADOR','AUXILIAR'))
);

-- ---------------------------
-- TABLA COMERCIANTE
-- ---------------------------
CREATE TABLE comerciante (
    id_comerciante       NUMBER         PRIMARY KEY,
    nombre_razon_social  VARCHAR2(150)  NOT NULL,
    municipio            VARCHAR2(100)  NOT NULL,
    telefono             VARCHAR2(20),
    correo               VARCHAR2(150),
    fecha_registro       DATE           NOT NULL,
    estado               CHAR(1)        NOT NULL CHECK (estado IN ('A','I')),
    fecha_actualizacion  DATE,
    usuario_actualizacion NUMBER,
    CONSTRAINT fk_comer_usr FOREIGN KEY (usuario_actualizacion) REFERENCES usuario(id_usuario)
);

-- ---------------------------
-- TABLA ESTABLECIMIENTO
-- ---------------------------
CREATE TABLE establecimiento (
    id_establecimiento    NUMBER        PRIMARY KEY,
    id_comerciante        NUMBER        NOT NULL,
    nombre_establecimiento VARCHAR2(150) NOT NULL,
    ingresos              NUMBER(14,2)  DEFAULT 0,
    numero_empleados      NUMBER(5)     DEFAULT 0,
    fecha_actualizacion   DATE,
    usuario_actualizacion NUMBER,
    CONSTRAINT fk_estab_comer FOREIGN KEY (id_comerciante) REFERENCES comerciante(id_comerciante),
    CONSTRAINT fk_estab_usr   FOREIGN KEY (usuario_actualizacion) REFERENCES usuario(id_usuario)
);

-- Índices para rendimiento
CREATE INDEX idx_estab_comer ON establecimiento(id_comerciante);
CREATE INDEX idx_comer_estado ON comerciante(estado);
