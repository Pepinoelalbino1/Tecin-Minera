package com.minera.tecinapp.inventario.controller;

import com.minera.tecinapp.inventario.dto.ProductoDTO;
import com.minera.tecinapp.inventario.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> findAll() {
        List<ProductoDTO> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable Long id) {
        ProductoDTO producto = productoService.findById(id);
        return ResponseEntity.ok(producto);
    }
    
    @PostMapping
    public ResponseEntity<ProductoDTO> create(@Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO created = productoService.create(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductoDTO productoDTO) {
        ProductoDTO updated = productoService.update(id, productoDTO);
        return ResponseEntity.ok(updated);
    }
    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ProductoDTO> updateEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        ProductoDTO updated = productoService.updateEstado(id, estado);
        return ResponseEntity.ok(updated);
    }
}

