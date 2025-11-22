package com.minera.tecinapp.inventario.service.impl;

import com.minera.tecinapp.inventario.dto.DetalleGuiaDTO;
import com.minera.tecinapp.inventario.dto.GuiaRemisionDTO;
import com.minera.tecinapp.inventario.exception.BusinessException;
import com.minera.tecinapp.inventario.exception.ResourceNotFoundException;
import com.minera.tecinapp.inventario.model.*;
import com.minera.tecinapp.inventario.repository.GuiaRemisionRepository;
import com.minera.tecinapp.inventario.repository.ProductoRepository;
import com.minera.tecinapp.inventario.service.GuiaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GuiaRemisionServiceImpl implements GuiaRemisionService {
    
    @Autowired
    private GuiaRemisionRepository guiaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<GuiaRemisionDTO> findAll() {
        return guiaRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public GuiaRemisionDTO findById(Long id) {
        GuiaRemision guia = guiaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Guía de Remisión", id));
        return convertToDTO(guia);
    }
    
    @Override
    public GuiaRemisionDTO create(GuiaRemisionDTO guiaDTO) {
        GuiaRemision guia = convertToEntity(guiaDTO);
        
        // Generar número de guía correlativo
        String numeroGuia = generarNumeroGuia();
        guia.setNumeroGuia(numeroGuia);
        
        guia = guiaRepository.save(guia);
        return convertToDTO(guia);
    }
    
    @Override
    public GuiaRemisionDTO addDetalle(Long guiaId, DetalleGuiaDTO detalleDTO) {
        GuiaRemision guia = guiaRepository.findById(guiaId)
            .orElseThrow(() -> new ResourceNotFoundException("Guía de Remisión", guiaId));
        
        if (guia.getEstado() == EstadoGuia.EMITIDA) {
            throw new BusinessException("No se puede modificar una guía ya emitida");
        }
        
        Producto producto = productoRepository.findById(detalleDTO.getProductoId())
            .orElseThrow(() -> new ResourceNotFoundException("Producto", detalleDTO.getProductoId()));
        
        DetalleGuia detalle = new DetalleGuia(guia, producto, detalleDTO.getCantidad());
        guia.addDetalle(detalle);
        
        guia = guiaRepository.save(guia);
        return convertToDTO(guia);
    }
    
    @Override
    public GuiaRemisionDTO emitir(Long id) {
        GuiaRemision guia = guiaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Guía de Remisión", id));
        
        if (guia.getEstado() == EstadoGuia.EMITIDA) {
            throw new BusinessException("La guía ya está emitida");
        }
        
        if (guia.getDetalles().isEmpty()) {
            throw new BusinessException("No se puede emitir una guía sin detalles");
        }
        
        // Descontar stock de productos
        for (DetalleGuia detalle : guia.getDetalles()) {
            Producto producto = detalle.getProducto();
            Integer stockActual = producto.getStock();
            Integer cantidad = detalle.getCantidad();
            
            if (stockActual < cantidad) {
                throw new BusinessException(
                    String.format("Stock insuficiente para el producto %s. Stock actual: %d, cantidad requerida: %d",
                        producto.getNombre(), stockActual, cantidad)
                );
            }
            
            producto.setStock(stockActual - cantidad);
            productoRepository.save(producto);
        }
        
        guia.setEstado(EstadoGuia.EMITIDA);
        guia = guiaRepository.save(guia);
        
        return convertToDTO(guia);
    }
    
    private String generarNumeroGuia() {
        Integer maxNumero = guiaRepository.findMaxNumeroGuia();
        int siguienteNumero = (maxNumero == null) ? 1 : maxNumero + 1;
        return String.format("GR-%05d", siguienteNumero);
    }
    
    private GuiaRemisionDTO convertToDTO(GuiaRemision guia) {
        GuiaRemisionDTO dto = new GuiaRemisionDTO();
        dto.setId(guia.getId());
        dto.setNumeroGuia(guia.getNumeroGuia());
        dto.setFechaEmision(guia.getFechaEmision());
        dto.setPuntoPartida(guia.getPuntoPartida());
        dto.setPuntoLlegada(guia.getPuntoLlegada());
        dto.setMotivoTraslado(guia.getMotivoTraslado());
        dto.setTransportista(guia.getTransportista());
        dto.setVehiculo(guia.getVehiculo());
        dto.setEstado(guia.getEstado().name());
        
        dto.setDetalles(guia.getDetalles().stream()
            .map(detalle -> {
                DetalleGuiaDTO detalleDTO = new DetalleGuiaDTO();
                detalleDTO.setId(detalle.getId());
                detalleDTO.setProductoId(detalle.getProducto().getId());
                detalleDTO.setProductoNombre(detalle.getProducto().getNombre());
                detalleDTO.setCantidad(detalle.getCantidad());
                return detalleDTO;
            })
            .collect(Collectors.toList()));
        
        return dto;
    }
    
    private GuiaRemision convertToEntity(GuiaRemisionDTO dto) {
        GuiaRemision guia = new GuiaRemision();
        guia.setFechaEmision(dto.getFechaEmision());
        guia.setPuntoPartida(dto.getPuntoPartida());
        guia.setPuntoLlegada(dto.getPuntoLlegada());
        guia.setMotivoTraslado(dto.getMotivoTraslado());
        guia.setTransportista(dto.getTransportista());
        guia.setVehiculo(dto.getVehiculo());
        guia.setEstado(EstadoGuia.BORRADOR);
        return guia;
    }
}

