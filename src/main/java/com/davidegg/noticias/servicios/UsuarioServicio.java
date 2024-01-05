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
    //@Qualifier("usuarioRepositorio")
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

//        switch (rol) {
//
//            case USER:
//
//                
//
//                
//                break;
//
//            case PERIODISTA:
//                
//                Periodista periodista = (Periodista) usuario;
//                periodista.set
//                periodistaRepo.save(periodista);
//
//                break;
//            case ADMIN:
//
//                break;
//            default:
//        }
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

}
