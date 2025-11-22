package com.minera.tecinapp.inventario.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuiaRemisionDTO {
    
    private Long id;
    private String numeroGuia;
    
    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fechaEmision;
    
    @NotBlank(message = "El punto de partida es obligatorio")
    @Size(max = 200, message = "El punto de partida no puede exceder 200 caracteres")
    private String puntoPartida;
    
    @NotBlank(message = "El punto de llegada es obligatorio")
    @Size(max = 200, message = "El punto de llegada no puede exceder 200 caracteres")
    private String puntoLlegada;
    
    @NotBlank(message = "El motivo de traslado es obligatorio")
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    private String motivoTraslado;
    
    @NotBlank(message = "El transportista es obligatorio")
    @Size(max = 200, message = "El transportista no puede exceder 200 caracteres")
    private String transportista;
    
    @NotBlank(message = "El vehículo es obligatorio")
    @Size(max = 100, message = "El vehículo no puede exceder 100 caracteres")
    private String vehiculo;
    
    private String estado;
    
    private List<DetalleGuiaDTO> detalles;
    
    public GuiaRemisionDTO() {
        this.detalles = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroGuia() {
        return numeroGuia;
    }
    
    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }
    
    public LocalDate getFechaEmision() {
        return fechaEmision;
    }
    
    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public String getPuntoPartida() {
        return puntoPartida;
    }
    
    public void setPuntoPartida(String puntoPartida) {
        this.puntoPartida = puntoPartida;
    }
    
    public String getPuntoLlegada() {
        return puntoLlegada;
    }
    
    public void setPuntoLlegada(String puntoLlegada) {
        this.puntoLlegada = puntoLlegada;
    }
    
    public String getMotivoTraslado() {
        return motivoTraslado;
    }
    
    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
    }
    
    public String getTransportista() {
        return transportista;
    }
    
    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }
    
    public String getVehiculo() {
        return vehiculo;
    }
    
    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public List<DetalleGuiaDTO> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<DetalleGuiaDTO> detalles) {
        this.detalles = detalles;
    }
}

