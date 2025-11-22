package com.minera.tecinapp.inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    
    @GetMapping("/")
    public String index() {
        return "redirect:/productos";
    }
    
    @GetMapping("/categorias")
    public String categorias(Model model) {
        model.addAttribute("title", "Categorías");
        return "categorias/index";
    }
    
    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("title", "Productos");
        return "productos/index";
    }
    
    @GetMapping("/movimientos")
    public String movimientos(Model model) {
        model.addAttribute("title", "Movimientos de Inventario");
        return "movimientos/index";
    }
    
    @GetMapping("/guias")
    public String guias(Model model) {
        model.addAttribute("title", "Guías de Remisión");
        return "guias/index";
    }
}

