ALTER SESSION SET CURRENT_SCHEMA = comercio;

-- ==== USUARIOS ====
INSERT INTO usuario(nombre, correo, contrasena, rol)
VALUES ('Admin Principal','admin@demo.com','admin123','ADMINISTRADOR');

INSERT INTO usuario(nombre, correo, contrasena, rol)
VALUES ('Auxiliar Registro','aux@demo.com','aux123','AUXILIAR');

-- ==== COMERCIANTES ====
INSERT INTO comerciante(nombre_razon_social, municipio, telefono, correo,
                         fecha_registro, estado, usuario_actualizacion)
VALUES
 ('Tech World SAS','Medellín','6044440000','info@techworld.com', SYSDATE-30,'A',1);

INSERT INTO comerciante(nombre_razon_social, municipio, telefono, correo,
                         fecha_registro, estado, usuario_actualizacion)
VALUES
 ('Moda Viva LTDA','Bogotá','6015551111','ventas@modaviva.com', SYSDATE-20,'A',1);

INSERT INTO comerciante(nombre_razon_social, municipio, telefono, correo,
                         fecha_registro, estado, usuario_actualizacion)
VALUES
 ('Café Andino','Manizales',NULL,NULL, SYSDATE-10,'A',1);

INSERT INTO comerciante(nombre_razon_social, municipio, telefono, correo,
                         fecha_registro, estado, usuario_actualizacion)
VALUES
 ('Ferretería El Tornillo','Cali','6023332222','contacto@tornillo.co', SYSDATE-15,'A',1);

INSERT INTO comerciante(nombre_razon_social, municipio, telefono, correo,
                         fecha_registro, estado, usuario_actualizacion)
VALUES
 ('Green Market','Barranquilla','6057778888','green@market.co', SYSDATE-5,'A',1);

-- ==== ESTABLECIMIENTOS (10 aleatorios) ====
-- Se asume que los IDs de comerciantes quedaron 1..5 por el trigger PK
INSERT ALL
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (1,'Tech World Centro', 25000000.50, 12)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (1,'Tech World Online', 18000000.00,  8)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (2,'Moda Viva Zona T', 15000000.75, 15)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (2,'Moda Viva Factory',  9000000.00,  5)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (3,'Café Andino Principal',  6000000.30, 10)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (3,'Café Andino Laureles',  4500000.40,  7)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (4,'Tornillo Norte', 8000000.00,  6)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (4,'Tornillo Sur',  5000000.00,  4)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (5,'Green Market Unicentro',  7000000.99,  9)
 INTO establecimiento(id_comerciante,nombre_establecimiento,ingresos,numero_empleados)
     VALUES (5,'Green Market Palmas',  6500000.25,  6)
SELECT * FROM dual;
COMMIT;
