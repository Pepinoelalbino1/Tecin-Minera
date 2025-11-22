package com.minera.tecinapp.inventario.util;

public class InventarioUtil {
    
    private InventarioUtil() {
        // Clase de utilidad, no instanciable
    }
    
    /**
     * Valida que un valor numérico sea positivo
     */
    public static boolean esPositivo(Number valor) {
        return valor != null && valor.doubleValue() > 0;
    }
    
    /**
     * Valida que un valor numérico sea no negativo
     */
    public static boolean esNoNegativo(Number valor) {
        return valor != null && valor.doubleValue() >= 0;
    }
}

