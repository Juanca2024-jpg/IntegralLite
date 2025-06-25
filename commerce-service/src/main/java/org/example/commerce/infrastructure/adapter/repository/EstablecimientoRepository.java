package org.example.commerce.infrastructure.adapter.repository;


import org.example.commerce.infrastructure.adapter.repository.entity.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {


    @Query(
            "SELECT COALESCE(SUM(e.ingresos), 0), " +
                    "       COALESCE(SUM(e.empleados), 0) " +
                    "FROM   Establecimiento e " +
                    "WHERE  e.comerciante.id = :idComerciante"
    )
    Optional<Object> sumIngresosAndEmpleados(@Param("idComerciante") Long idComerciante);


}