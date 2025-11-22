package com.minera.tecinapp.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guias_remision")
public class GuiaRemision {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 20)
    private String numeroGuia;
    
    @NotNull(message = "La fecha de emisión es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaEmision;
    
    @NotBlank(message = "El punto de partida es obligatorio")
    @Size(max = 200, message = "El punto de partida no puede exceder 200 caracteres")
    @Column(nullable = false, length = 200)
    private String puntoPartida;
    
    @NotBlank(message = "El punto de llegada es obligatorio")
    @Size(max = 200, message = "El punto de llegada no puede exceder 200 caracteres")
    @Column(nullable = false, length = 200)
    private String puntoLlegada;
    
    @NotBlank(message = "El motivo de traslado es obligatorio")
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    @Column(nullable = false, length = 500)
    private String motivoTraslado;
    
    @NotBlank(message = "El transportista es obligatorio")
    @Size(max = 200, message = "El transportista no puede exceder 200 caracteres")
    @Column(nullable = false, length = 200)
    private String transportista;
    
    @NotBlank(message = "El vehículo es obligatorio")
    @Size(max = 100, message = "El vehículo no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String vehiculo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoGuia estado;
    
    @OneToMany(mappedBy = "guia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleGuia> detalles;
    
    public GuiaRemision() {
        this.estado = EstadoGuia.BORRADOR;
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
    
    public EstadoGuia getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoGuia estado) {
        this.estado = estado;
    }
    
    public List<DetalleGuia> getDetalles() {
        return detalles;
    }
    
    public void setDetalles(List<DetalleGuia> detalles) {
        this.detalles = detalles;
    }
    
    public void addDetalle(DetalleGuia detalle) {
        detalles.add(detalle);
        detalle.setGuia(this);
    }
    
    public void removeDetalle(DetalleGuia detalle) {
        detalles.remove(detalle);
        detalle.setGuia(null);
    }
}

