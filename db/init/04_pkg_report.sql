ALTER SESSION SET CURRENT_SCHEMA = comercio;

CREATE OR REPLACE PACKAGE comercio_pkg AS
  TYPE t_cursor IS REF CURSOR;
  FUNCTION fn_comerciantes_activos RETURN t_cursor;
END comercio_pkg;
/

CREATE OR REPLACE PACKAGE BODY comercio_pkg AS
  FUNCTION fn_comerciantes_activos RETURN t_cursor IS
    rc t_cursor;
  BEGIN
    OPEN rc FOR
      SELECT
        c.nombre_razon_social,
        c.municipio,
        c.telefono,
        c.correo,
        c.fecha_registro,
        CASE c.estado WHEN 'A' THEN 'Activo' ELSE 'Inactivo' END AS estado,
        COUNT(e.id_establecimiento)                                    AS cantidad_establecimientos,
        NVL(SUM(e.ingresos),0)                                         AS total_ingresos,
        NVL(SUM(e.numero_empleados),0)                                 AS cantidad_empleados
      FROM comerciante c
      LEFT JOIN establecimiento e ON e.id_comerciante = c.id_comerciante
      WHERE c.estado = 'A'
      GROUP BY c.id_comerciante, c.nombre_razon_social, c.municipio,
               c.telefono, c.correo, c.fecha_registro, c.estado
      ORDER BY cantidad_establecimientos DESC;
    RETURN rc;
  END;
END comercio_pkg;
/
