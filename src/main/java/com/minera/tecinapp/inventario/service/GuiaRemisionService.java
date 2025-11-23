package com.minera.tecinapp.inventario.service;

import com.minera.tecinapp.inventario.dto.DetalleGuiaDTO;
import com.minera.tecinapp.inventario.dto.GuiaRemisionDTO;
import java.util.List;

public interface GuiaRemisionService {
    List<GuiaRemisionDTO> findAll();
    GuiaRemisionDTO findById(Long id);
    GuiaRemisionDTO create(GuiaRemisionDTO guiaDTO);
    GuiaRemisionDTO addDetalle(Long guiaId, DetalleGuiaDTO detalleDTO);
    GuiaRemisionDTO removeDetalle(Long guiaId, Long detalleId);
    GuiaRemisionDTO emitir(Long id);
}

