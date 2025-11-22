package com.minera.tecinapp.inventario.service;

import com.minera.tecinapp.inventario.dto.GuiaRemisionDTO;
import java.io.ByteArrayOutputStream;

public interface PdfService {
    ByteArrayOutputStream generarPdfGuiaRemision(GuiaRemisionDTO guia);
}

