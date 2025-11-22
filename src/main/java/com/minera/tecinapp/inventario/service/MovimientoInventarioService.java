package com.minera.tecinapp.inventario.service;

import com.minera.tecinapp.inventario.dto.MovimientoEntradaDTO;
import com.minera.tecinapp.inventario.dto.MovimientoInventarioResponseDTO;
import com.minera.tecinapp.inventario.dto.MovimientoSalidaDTO;
import java.util.List;

public interface MovimientoInventarioService {
    MovimientoInventarioResponseDTO registrarEntrada(MovimientoEntradaDTO dto);
    MovimientoInventarioResponseDTO registrarSalida(MovimientoSalidaDTO dto);
    List<MovimientoInventarioResponseDTO> obtenerKardex(Long productoId);
}

