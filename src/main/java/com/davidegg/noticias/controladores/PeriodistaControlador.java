/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davidegg.noticias.controladores;

import com.davidegg.noticias.entidades.Periodista;
import com.davidegg.noticias.enumeracion.Rol;
import com.davidegg.noticias.excepciones.MiException;
import com.davidegg.noticias.servicios.NoticiaServicio;
import com.davidegg.noticias.servicios.PeriodistaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/periodista")
public class PeriodistaControlador {
    
    @Autowired
    private NoticiaServicio noticiaServicio;
    @Autowired
    private PeriodistaServicio periodistaServicio;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        //model.addAttribute("autor", new Periodista());
        return "autor_form"; // Nombre del archivo HTML (sin extensión y sin prefijo de carpeta)
    }

    @PostMapping("/registro")
    public String registrarPeriodista(@RequestParam(required = false) String nombreUsuario, 
                @RequestParam(required = false) String password,
                @RequestParam(required = false) Boolean activo,
                @RequestParam(required = false) Integer sueldoMensual, ModelMap modelo) {
        
        
        //no me gusta nada que no pueda elegir el rol y que sea tan hardtypeado todo
        
        try {
            periodistaServicio.crearPeriodista(nombreUsuario, password, Rol.PERIODISTA, activo, sueldoMensual);
            modelo.put("exito", "El periodista ha sido ingresado correctamente");
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());

            return "autor_form";
        }
        return "autor_form";
    }
    
    @PostMapping("/modificar")
    public String modificarPeriodista(@RequestParam(required = false) String id,
                @RequestParam(required = false) String nombreUsuario, 
                @RequestParam(required = false) String password,
                @RequestParam(required = false) Boolean activo,
                @RequestParam(required = false) Integer sueldoMensual, ModelMap modelo) {
        
        
        //no me gusta nada que no pueda elegir el rol y que sea tan hardtypeado todo
        
        try {
            periodistaServicio.modificarPeriodista(id, nombreUsuario, password, activo, sueldoMensual);
            modelo.put("exito", "El periodista ha sido modificado correctamente");
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());

            return "autor_form";
        }
        return "autor_form";
    }

    @GetMapping("/lista")
    public String mostrarListaAutores(ModelMap modelo) {
        List<Periodista> periodistas = periodistaServicio.listaPeriodistas();
        modelo.addAttribute("periodistas", periodistas);
        return "autor_lista"; // Nombre del archivo HTML (sin extensión y sin prefijo de carpeta)
    }
  
    
}
