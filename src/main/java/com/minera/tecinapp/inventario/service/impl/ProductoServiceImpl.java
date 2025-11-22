package com.minera.tecinapp.inventario.service.impl;

import com.minera.tecinapp.inventario.dto.ProductoDTO;
import com.minera.tecinapp.inventario.exception.BusinessException;
import com.minera.tecinapp.inventario.exception.ResourceNotFoundException;
import com.minera.tecinapp.inventario.model.Categoria;
import com.minera.tecinapp.inventario.model.EstadoProducto;
import com.minera.tecinapp.inventario.model.Producto;
import com.minera.tecinapp.inventario.repository.CategoriaRepository;
import com.minera.tecinapp.inventario.repository.ProductoRepository;
import com.minera.tecinapp.inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        return convertToDTO(producto);
    }
    
    @Override
    public ProductoDTO create(ProductoDTO productoDTO) {
        validateProducto(productoDTO);
        
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", productoDTO.getCategoriaId()));
        
        Producto producto = convertToEntity(productoDTO, categoria);
        producto = productoRepository.save(producto);
        return convertToDTO(producto);
    }
    
    @Override
    public ProductoDTO update(Long id, ProductoDTO productoDTO) {
        validateProducto(productoDTO);
        
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", productoDTO.getCategoriaId()));
        
        producto.setNombre(productoDTO.getNombre());
        producto.setCategoria(categoria);
        producto.setStock(productoDTO.getStock());
        producto.setUnidadMedida(productoDTO.getUnidadMedida());
        producto.setPrecio(productoDTO.getPrecio());
        
        producto = productoRepository.save(producto);
        return convertToDTO(producto);
    }
    
    @Override
    public ProductoDTO updateEstado(Long id, String estado) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        
        try {
            EstadoProducto nuevoEstado = EstadoProducto.valueOf(estado.toUpperCase());
            producto.setEstado(nuevoEstado);
            producto = productoRepository.save(producto);
            return convertToDTO(producto);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Estado inválido: " + estado);
        }
    }
    
    private void validateProducto(ProductoDTO productoDTO) {
        if (productoDTO.getStock() < 0) {
            throw new BusinessException("El stock no puede ser negativo");
        }
        if (productoDTO.getPrecio().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El precio debe ser mayor a cero");
        }
        if (productoDTO.getNombre() == null || productoDTO.getNombre().trim().isEmpty()) {
            throw new BusinessException("El nombre del producto es obligatorio");
        }
    }
    
    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setCategoriaId(producto.getCategoria().getId());
        dto.setCategoriaNombre(producto.getCategoria().getNombre());
        dto.setStock(producto.getStock());
        dto.setUnidadMedida(producto.getUnidadMedida());
        dto.setPrecio(producto.getPrecio());
        dto.setEstado(producto.getEstado().name());
        return dto;
    }
    
    private Producto convertToEntity(ProductoDTO dto, Categoria categoria) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setCategoria(categoria);
        producto.setStock(dto.getStock());
        producto.setUnidadMedida(dto.getUnidadMedida());
        producto.setPrecio(dto.getPrecio());
        producto.setEstado(EstadoProducto.ACTIVO);
        return producto;
    }
}

