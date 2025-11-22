package com.minera.tecinapp.inventario.repository;

import com.minera.tecinapp.inventario.model.GuiaRemision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuiaRemisionRepository extends JpaRepository<GuiaRemision, Long> {
    Optional<GuiaRemision> findByNumeroGuia(String numeroGuia);
    
    @Query(value = "SELECT MAX(CAST(SUBSTRING(numero_guia, 4) AS UNSIGNED)) FROM guias_remision WHERE numero_guia LIKE 'GR-%'", nativeQuery = true)
    Integer findMaxNumeroGuia();
}

