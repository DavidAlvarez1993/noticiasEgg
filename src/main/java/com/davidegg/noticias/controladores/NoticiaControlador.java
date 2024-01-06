package com.davidegg.noticias.controladores;

import com.davidegg.noticias.entidades.Noticia;
import com.davidegg.noticias.entidades.Periodista;
import com.davidegg.noticias.excepciones.MiException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.davidegg.noticias.servicios.NoticiaServicio;
import com.davidegg.noticias.servicios.PeriodistaServicio;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    @Autowired
    private PeriodistaServicio periodistaServicio;
    

    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
        List<Periodista> autores = periodistaServicio.listaPeriodistas();
        modelo.addAttribute("autores", autores);

        return "noticia_form";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Noticia> noticias = noticiaServicio.listaNoticias();
        modelo.addAttribute("noticias", noticias);

        return "noticia_lista";

    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        List<Periodista> autores = periodistaServicio.listaPeriodistas();
        modelo.addAttribute("autores", autores);

        try {
            modelo.put("noticia", noticiaServicio.getOne(id));
            return "noticia_modificar";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "noticia_modificar";

        }

//        List<Noticia> noticias = noticiaServicio.listaNoticias();
//        modelo.addAttribute("noticias", noticias);
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarNoticia(@PathVariable String id, ModelMap modelo) {
        try {
            noticiaServicio.eliminarNoticia(id);
            modelo.put("exito", "La noticia ha sido eliminada correctamente");
            System.out.println("La noticia ha sido eliminada correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:../lista";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String titulo,
            @RequestParam(required = false) String cuerpo, @RequestParam(required = false) String autor,
            ModelMap modelo
    ) {

        try {
//            System.out.println("Título: " + titulo);
//            System.out.println("Cuerpo: " + cuerpo);

            noticiaServicio.crearNoticia(titulo, cuerpo, autor);

            modelo.put("exito", "La noticia ha sido subida correctamente");
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "noticia_form";
        }
        return "noticia_form";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String titulo, String cuerpo, String autor, ModelMap modelo) {

        try {
//            System.out.println("Título: " + titulo);
//            System.out.println("Cuerpo: " + cuerpo);

            //TO DO pasar fechaSubida como un parametro hidden desde lista.html
            noticiaServicio.modificarNoticia(id, titulo, cuerpo, autor);
            modelo.put("exito", "La noticia ha sido subida correctamente");
            return "redirect:../lista";

            //modelo.put("exito", "La noticia ha sido subida correctamente");
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:../lista";
        }

    }

}

//netstat -ano | findstr "8080"
//taskkill /F /PID 6020
//Título: River se fue a la B
//Cuerpo: El equipo de Nuñez descendió patéticamente a la segunda categoría del fútbol argentino.
//Título: Venden corsa modelo 98
//Cuerpo: Los ciudadanos de Gualeguaychú no pueden perderse esta increible oportunidad.
//TODO llenar el put()
//        modelo.put("noticia",);
//        
//        List<Noticia> noticias = noticiaServicio.listaNoticias();
//        modelo.addAttribute("noticias", noticias);
