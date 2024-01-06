package com.davidegg.noticias.controladores;

import com.davidegg.noticias.entidades.Usuario;
import com.davidegg.noticias.enumeracion.Rol;
import com.davidegg.noticias.excepciones.MiException;
import com.davidegg.noticias.servicios.NoticiaServicio;
import com.davidegg.noticias.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        //model.addAttribute("autor", new Periodista());
        return "user_form"; // Nombre del archivo HTML (sin extensión y sin prefijo de carpeta)
    }

    @GetMapping("/lista")
    public String mostrarListaAutores(ModelMap modelo
    ) {
        List<Usuario> usuarios = usuarioServicio.listaUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "user_lista"; // Nombre del archivo HTML (sin extensión y sin prefijo de carpeta)
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        try {
            modelo.put("usuario", usuarioServicio.getOne(id));
            return "usuario_modificar";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "usuario_modificar";

        }

    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioServicio.eliminarUsuario(id);
            modelo.put("exito", "El usuario ha sido eliminado correctamente");
            System.out.println("El usuario ha sido eliminado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:../lista";
    }

    @PostMapping("/registro")
    public String registrarPeriodista(@RequestParam(required = false) String nombreUsuario,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) Integer sueldoMensual, ModelMap modelo
    ) {

        //no me gusta nada que no pueda elegir el rol y que sea tan hardtypeado todo
        try {
            usuarioServicio.crearUsuario(nombreUsuario, password, Rol.USER, activo);
            modelo.put("exito", "El periodista ha sido ingresado correctamente");
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "user_form";
        }
        return "user_form";
    }

    @PostMapping("/modificar")
    public String modificarUsuario(@RequestParam(required = false) String id,
            @RequestParam(required = false) String nombreUsuario,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Boolean activo, ModelMap modelo
    ) {

        //no me gusta nada que no pueda elegir el rol y que sea tan hardtypeado todo
        try {
            usuarioServicio.modificarUsuario(id, nombreUsuario, password, activo);
            modelo.put("exito", "El periodista ha sido modificado correctamente");
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "user_form";
        }
        return "user_form";
    }

}
