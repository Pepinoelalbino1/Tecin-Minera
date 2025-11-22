package com.minera.tecinapp.inventario.service;

import com.minera.tecinapp.inventario.dto.ProductoDTO;
import java.util.List;

public interface ProductoService {
    List<ProductoDTO> findAll();
    ProductoDTO findById(Long id);
    ProductoDTO create(ProductoDTO productoDTO);
    ProductoDTO update(Long id, ProductoDTO productoDTO);
    ProductoDTO updateEstado(Long id, String estado);
}

