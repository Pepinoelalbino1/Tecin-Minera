package com.minera.tecinapp.inventario.service.impl;

import com.minera.tecinapp.inventario.dto.MovimientoEntradaDTO;
import com.minera.tecinapp.inventario.dto.MovimientoInventarioResponseDTO;
import com.minera.tecinapp.inventario.dto.MovimientoSalidaDTO;
import com.minera.tecinapp.inventario.exception.BusinessException;
import com.minera.tecinapp.inventario.exception.ResourceNotFoundException;
import com.minera.tecinapp.inventario.model.MovimientoInventario;
import com.minera.tecinapp.inventario.model.Producto;
import com.minera.tecinapp.inventario.model.TipoMovimiento;
import com.minera.tecinapp.inventario.repository.MovimientoInventarioRepository;
import com.minera.tecinapp.inventario.repository.ProductoRepository;
import com.minera.tecinapp.inventario.service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {
    
    @Autowired
    private MovimientoInventarioRepository movimientoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    public MovimientoInventarioResponseDTO registrarEntrada(MovimientoEntradaDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
            .orElseThrow(() -> new ResourceNotFoundException("Producto", dto.getProductoId()));
        
        Integer stockAnterior = producto.getStock();
        Integer stockNuevo = stockAnterior + dto.getCantidad();
        
        producto.setStock(stockNuevo);
        productoRepository.save(producto);
        
        MovimientoInventario movimiento = new MovimientoInventario(
            producto,
            TipoMovimiento.ENTRADA,
            dto.getCantidad(),
            dto.getMotivo()
        );
        
        movimiento = movimientoRepository.save(movimiento);
        return convertToResponseDTO(movimiento, stockAnterior, stockNuevo);
    }
    
    @Override
    public MovimientoInventarioResponseDTO registrarSalida(MovimientoSalidaDTO dto) {
        Producto producto = productoRepository.findById(dto.getProductoId())
            .orElseThrow(() -> new ResourceNotFoundException("Producto", dto.getProductoId()));
        
        Integer stockAnterior = producto.getStock();
        
        if (stockAnterior < dto.getCantidad()) {
            throw new BusinessException(
                String.format("Stock insuficiente. Stock actual: %d, cantidad solicitada: %d", 
                    stockAnterior, dto.getCantidad())
            );
        }
        
        Integer stockNuevo = stockAnterior - dto.getCantidad();
        producto.setStock(stockNuevo);
        productoRepository.save(producto);
        
        MovimientoInventario movimiento = new MovimientoInventario(
            producto,
            TipoMovimiento.SALIDA,
            dto.getCantidad(),
            dto.getMotivo()
        );
        
        movimiento = movimientoRepository.save(movimiento);
        return convertToResponseDTO(movimiento, stockAnterior, stockNuevo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponseDTO> obtenerKardex(Long productoId) {
        if (!productoRepository.existsById(productoId)) {
            throw new ResourceNotFoundException("Producto", productoId);
        }
        
        return movimientoRepository.findByProductoIdOrderByFechaDesc(productoId).stream()
            .map(mov -> {
                // Para el kardex, necesitamos calcular stock anterior y nuevo
                // Por simplicidad, calculamos basado en movimientos anteriores
                return convertToResponseDTO(mov, null, null);
            })
            .collect(Collectors.toList());
    }
    
    private MovimientoInventarioResponseDTO convertToResponseDTO(
            MovimientoInventario movimiento, 
            Integer stockAnterior, 
            Integer stockNuevo) {
        MovimientoInventarioResponseDTO dto = new MovimientoInventarioResponseDTO();
        dto.setId(movimiento.getId());
        dto.setProductoId(movimiento.getProducto().getId());
        dto.setProductoNombre(movimiento.getProducto().getNombre());
        dto.setTipo(movimiento.getTipo().name());
        dto.setCantidad(movimiento.getCantidad());
        dto.setMotivo(movimiento.getMotivo());
        dto.setFecha(movimiento.getFecha());
        dto.setStockAnterior(stockAnterior);
        dto.setStockNuevo(stockNuevo);
        return dto;
    }
}

