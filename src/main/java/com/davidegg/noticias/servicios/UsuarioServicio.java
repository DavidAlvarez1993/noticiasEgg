package com.davidegg.noticias.servicios;

import com.davidegg.noticias.entidades.Noticia;
import com.davidegg.noticias.entidades.Usuario;
import com.davidegg.noticias.enumeracion.Rol;
import com.davidegg.noticias.excepciones.MiException;
import com.davidegg.noticias.repositorios.AdministradorRepositorio;
import com.davidegg.noticias.repositorios.NoticiaRepositorio;
import com.davidegg.noticias.repositorios.PeriodistaRepositorio;
import com.davidegg.noticias.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    @Autowired
    protected NoticiaRepositorio noticiaRepo;
    @Autowired
    protected UsuarioRepositorio usuarioRepo;
    @Autowired
    protected PeriodistaRepositorio periodistaRepo;
    @Autowired
    protected AdministradorRepositorio administradorRepo;

    @Transactional
    public void crearUsuario(String nombreUsuario, String password, Rol rol, Boolean activo) throws MiException {

        //validar(nombreUsuario, password, rol, activo);
        LocalDate fechaAlta = LocalDate.now();
        List<Noticia> misNoticias = new ArrayList<>();

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(password);
        usuario.setFechaAlta(fechaAlta);
        usuario.setRol(rol);
        usuario.setActivo(activo);
        usuarioRepo.save(usuario);

    }

    public void eliminarUsuario(String id) throws MiException {
        if (id == null) {
            throw new MiException("El ID no puede ser nulo");
        }

        Optional<Usuario> usuario = usuarioRepo.findById(id);

        if (usuario.isPresent()) {
            usuarioRepo.delete(usuario.get());
        } else {
            throw new MiException("No se encontró la usuario con ID: " + id);
        }
    }

    @Transactional
    public void modificarUsuario(String id, String nombreUsuario, String password, Boolean activo) throws MiException {

        if (id == null) {
            throw new MiException("El id no puede ser nulo");
        }

//Aqui las validaciones de los atributos comunes a todos los metodos
        validar(nombreUsuario, password);

        Optional<Usuario> respuesta = usuarioRepo.findById(id); //Puede o no contener un valor no nulo, 

        if (respuesta.isPresent()) {                              //respuesta.isPresent() da true si  respuestacontiene un valor no nulo     

            Usuario usuario = respuesta.get();

            usuario.setNombreUsuario(nombreUsuario);
            usuario.setPassword(password);
            usuario.setActivo(activo);

            usuarioRepo.save(usuario);
        }

    }

    public List<Usuario> listaUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        usuarios = usuarioRepo.findAll();
        return usuarios;
    }

    private void validar(String nombreUsuario, String password) throws MiException {
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new MiException("El nombre del usuario no puede ser nulo ni estar vacío");
        }

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("La contraseña no puede ser nulo ni estar vacío ni tener menos de 6 caracteres");
        }
    }

    @Transactional
    public Usuario getOne(String id) throws MiException {

        if (id == null) {
            throw new MiException("El id no puede ser nulo");
        } else {
            return usuarioRepo.getOne(id);
        }

    }


}
