package com.minera.tecinapp.inventario.controller;

import com.minera.tecinapp.inventario.dto.MovimientoEntradaDTO;
import com.minera.tecinapp.inventario.dto.MovimientoInventarioResponseDTO;
import com.minera.tecinapp.inventario.dto.MovimientoSalidaDTO;
import com.minera.tecinapp.inventario.service.MovimientoInventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoInventarioController {
    
    @Autowired
    private MovimientoInventarioService movimientoService;
    
    @PostMapping("/entrada")
    public ResponseEntity<MovimientoInventarioResponseDTO> registrarEntrada(
            @Valid @RequestBody MovimientoEntradaDTO dto) {
        MovimientoInventarioResponseDTO movimiento = movimientoService.registrarEntrada(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimiento);
    }
    
    @PostMapping("/salida")
    public ResponseEntity<MovimientoInventarioResponseDTO> registrarSalida(
            @Valid @RequestBody MovimientoSalidaDTO dto) {
        MovimientoInventarioResponseDTO movimiento = movimientoService.registrarSalida(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimiento);
    }
    
    @GetMapping("/kardex/{productoId}")
    public ResponseEntity<List<MovimientoInventarioResponseDTO>> obtenerKardex(
            @PathVariable Long productoId) {
        List<MovimientoInventarioResponseDTO> kardex = movimientoService.obtenerKardex(productoId);
        return ResponseEntity.ok(kardex);
    }
}

