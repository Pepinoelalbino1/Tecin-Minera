package com.minera.tecinapp.inventario.controller;

import com.minera.tecinapp.inventario.dto.DetalleGuiaDTO;
import com.minera.tecinapp.inventario.dto.GuiaRemisionDTO;
import com.minera.tecinapp.inventario.service.GuiaRemisionService;
import com.minera.tecinapp.inventario.service.PdfService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guias")
public class GuiaRemisionController {
    
    @Autowired
    private GuiaRemisionService guiaService;
    
    @Autowired
    private PdfService pdfService;
    
    @GetMapping
    public ResponseEntity<List<GuiaRemisionDTO>> findAll() {
        List<GuiaRemisionDTO> guias = guiaService.findAll();
        return ResponseEntity.ok(guias);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GuiaRemisionDTO> findById(@PathVariable Long id) {
        GuiaRemisionDTO guia = guiaService.findById(id);
        return ResponseEntity.ok(guia);
    }
    
    @PostMapping
    public ResponseEntity<GuiaRemisionDTO> create(@Valid @RequestBody GuiaRemisionDTO guiaDTO) {
        GuiaRemisionDTO created = guiaService.create(guiaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PostMapping("/{id}/detalle")
    public ResponseEntity<GuiaRemisionDTO> addDetalle(
            @PathVariable Long id,
            @Valid @RequestBody DetalleGuiaDTO detalleDTO) {
        GuiaRemisionDTO updated = guiaService.addDetalle(id, detalleDTO);
        return ResponseEntity.ok(updated);
    }
    
    @PutMapping("/{id}/emitir")
    public ResponseEntity<GuiaRemisionDTO> emitir(@PathVariable Long id) {
        GuiaRemisionDTO emitted = guiaService.emitir(id);
        return ResponseEntity.ok(emitted);
    }
    
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {
        GuiaRemisionDTO guia = guiaService.findById(id);
        byte[] pdfBytes = pdfService.generarPdfGuiaRemision(guia).toByteArray();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "guia-remision-" + guia.getNumeroGuia() + ".pdf");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}

