ALTER SESSION SET CURRENT_SCHEMA = comercio;

-- Secuencias
CREATE SEQUENCE seq_usuario      START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_comerciante  START WITH 1 INCREMENT BY 1 NOCACHE;
CREATE SEQUENCE seq_establecimiento START WITH 1 INCREMENT BY 1 NOCACHE;

-- Triggers de PK
CREATE OR REPLACE TRIGGER trg_usuario_pk
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
  IF :new.id_usuario IS NULL THEN
    :new.id_usuario := seq_usuario.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_comerciante_pk
BEFORE INSERT ON comerciante
FOR EACH ROW
BEGIN
  IF :new.id_comerciante IS NULL THEN
    :new.id_comerciante := seq_comerciante.NEXTVAL;
  END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_establecimiento_pk
BEFORE INSERT ON establecimiento
FOR EACH ROW
BEGIN
  IF :new.id_establecimiento IS NULL THEN
    :new.id_establecimiento := seq_establecimiento.NEXTVAL;
  END IF;
END;
/

-- Triggers de auditoría (insert / update)
CREATE OR REPLACE TRIGGER trg_comerciante_audit
BEFORE INSERT OR UPDATE ON comerciante
FOR EACH ROW
BEGIN
  :new.fecha_actualizacion := SYSDATE;
  -- Si no se envía usuario_actualizacion, usa 1 (Admin) por defecto
  IF :new.usuario_actualizacion IS NULL THEN :new.usuario_actualizacion := 1; END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_establecimiento_audit
BEFORE INSERT OR UPDATE ON establecimiento
FOR EACH ROW
BEGIN
  :new.fecha_actualizacion := SYSDATE;
  IF :new.usuario_actualizacion IS NULL THEN :new.usuario_actualizacion := 1; END IF;
END;
/
