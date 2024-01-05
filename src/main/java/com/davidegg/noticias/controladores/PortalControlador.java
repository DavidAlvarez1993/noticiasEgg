package com.davidegg.noticias.controladores;


import com.davidegg.noticias.enumeracion.Rol;
import com.davidegg.noticias.excepciones.MiException;
import com.davidegg.noticias.servicios.PeriodistaServicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PortalControlador {


    @GetMapping("/")
    public String home() throws MiException {
//        PeriodistaServicio ps = new PeriodistaServicio();
//        ps.crearPeriodista("nombreUsuario", "password", Rol.PERIODISTA, Boolean.TRUE, 500);

        return "home";
    }

  
}