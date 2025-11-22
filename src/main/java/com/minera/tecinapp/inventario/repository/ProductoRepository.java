package com.minera.tecinapp.inventario.repository;

import com.minera.tecinapp.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEstado(com.minera.tecinapp.inventario.model.EstadoProducto estado);
}

