package com.minera.tecinapp.inventario.service;

import com.minera.tecinapp.inventario.dto.CategoriaDTO;
import java.util.List;

public interface CategoriaService {
    List<CategoriaDTO> findAll();
    CategoriaDTO findById(Long id);
    CategoriaDTO create(CategoriaDTO categoriaDTO);
    CategoriaDTO update(Long id, CategoriaDTO categoriaDTO);
    void delete(Long id);
}

