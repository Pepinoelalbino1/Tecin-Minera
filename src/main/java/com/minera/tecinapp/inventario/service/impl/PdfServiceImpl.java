package com.minera.tecinapp.inventario.service.impl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.minera.tecinapp.inventario.dto.DetalleGuiaDTO;
import com.minera.tecinapp.inventario.dto.GuiaRemisionDTO;
import com.minera.tecinapp.inventario.service.PdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfServiceImpl implements PdfService {
    
    @Override
    public ByteArrayOutputStream generarPdfGuiaRemision(GuiaRemisionDTO guia) {
        try {
            String html = generarHtmlGuia(guia);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            
            ConverterProperties properties = new ConverterProperties();
            HtmlConverter.convertToPdf(html, outputStream, properties);
            
            return outputStream;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF de guía de remisión", e);
        }
    }
    
    private String generarHtmlGuia(GuiaRemisionDTO guia) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>");
        html.append("<html>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        html.append(".header { text-align: center; margin-bottom: 30px; }");
        html.append(".header h1 { color: #2c3e50; margin: 0; }");
        html.append(".header h2 { color: #34495e; margin: 5px 0; }");
        html.append(".info-section { margin: 20px 0; }");
        html.append(".info-row { display: flex; margin: 10px 0; }");
        html.append(".info-label { font-weight: bold; width: 200px; }");
        html.append(".info-value { flex: 1; }");
        html.append("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
        html.append("table th, table td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
        html.append("table th { background-color: #3498db; color: white; }");
        html.append("table tr:nth-child(even) { background-color: #f2f2f2; }");
        html.append(".footer { margin-top: 40px; text-align: center; font-size: 12px; color: #7f8c8d; }");
        html.append(".estado { display: inline-block; padding: 5px 15px; border-radius: 5px; font-weight: bold; }");
        html.append(".estado.EMITIDA { background-color: #27ae60; color: white; }");
        html.append(".estado.BORRADOR { background-color: #e74c3c; color: white; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        
        // Header
        html.append("<div class='header'>");
        html.append("<h1>TECIN MINERA</h1>");
        html.append("<h2>GUÍA DE REMISIÓN</h2>");
        html.append("</div>");
        
        // Información de la guía
        html.append("<div class='info-section'>");
        html.append("<div class='info-row'>");
        html.append("<span class='info-label'>Número de Guía:</span>");
        html.append("<span class='info-value'>").append(guia.getNumeroGuia()).append("</span>");
        html.append("</div>");
        html.append("<div class='info-row'>");
        html.append("<span class='info-label'>Fecha de Emisión:</span>");
        html.append("<span class='info-value'>").append(guia.getFechaEmision().format(formatter)).append("</span>");
        html.append("</div>");
        html.append("<div class='info-row'>");
        html.append("<span class='info-label'>Estado:</span>");
        html.append("<span class='info-value'>");
        html.append("<span class='estado ").append(guia.getEstado()).append("'>");
        html.append(guia.getEstado());
        html.append("</span>");
        html.append("</span>");
        html.append("</div>");
        html.append("</div>");
        
        // Información de traslado
        html.append("<div class='info-section'>");
        html.append("<h3>Información de Traslado</h3>");
        html.append("<div class='info-row'>");
        html.append("<span class='info-label'>Punto de Partida:</span>");
        html.append("<span class='info-value'>").append(guia.getPuntoPartida()).append("</span>");
        html.append("</div>");
        html.append("<div class='info-row'>");
        html.append("<span class='info-label'>Punto de Llegada:</span>");
        html.append("<span class='info-value'>").append(guia.getPuntoLlegada()).append("</span>");
        html.append("</div>");
        html.append("<div class='info-row'>");
        html.append("<span class='info-label'>Motivo de Traslado:</span>");
        html.append("<span class='info-value'>").append(guia.getMotivoTraslado()).append("</span>");
        html.append("</div>");
        html.append("<div class='info-row'>");
        html.append("<span class='info-label'>Transportista:</span>");
        html.append("<span class='info-value'>").append(guia.getTransportista()).append("</span>");
        html.append("</div>");
        html.append("<div class='info-row'>");
        html.append("<span class='info-label'>Vehículo:</span>");
        html.append("<span class='info-value'>").append(guia.getVehiculo()).append("</span>");
        html.append("</div>");
        html.append("</div>");
        
        // Detalles de productos
        html.append("<div class='info-section'>");
        html.append("<h3>Detalle de Productos</h3>");
        html.append("<table>");
        html.append("<thead>");
        html.append("<tr>");
        html.append("<th>#</th>");
        html.append("<th>Producto</th>");
        html.append("<th>Cantidad</th>");
        html.append("</tr>");
        html.append("</thead>");
        html.append("<tbody>");
        
        int contador = 1;
        for (DetalleGuiaDTO detalle : guia.getDetalles()) {
            html.append("<tr>");
            html.append("<td>").append(contador++).append("</td>");
            html.append("<td>").append(detalle.getProductoNombre()).append("</td>");
            html.append("<td>").append(detalle.getCantidad()).append("</td>");
            html.append("</tr>");
        }
        
        html.append("</tbody>");
        html.append("</table>");
        html.append("</div>");
        
        // Footer
        html.append("<div class='footer'>");
        html.append("<p>Documento generado automáticamente por el Sistema de Gestión de Inventario - Tecin Minera</p>");
        html.append("</div>");
        
        html.append("</body>");
        html.append("</html>");
        
        return html.toString();
    }
}

