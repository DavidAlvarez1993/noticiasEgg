package com.davidegg.noticias.servicios;

import com.davidegg.noticias.entidades.Noticia;
import com.davidegg.noticias.entidades.Periodista;
import com.davidegg.noticias.excepciones.MiException;
import com.davidegg.noticias.repositorios.AdministradorRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.davidegg.noticias.repositorios.NoticiaRepositorio;
import com.davidegg.noticias.repositorios.PeriodistaRepositorio;
import com.davidegg.noticias.repositorios.UsuarioRepositorio;
import java.time.LocalDate;

@Service
public class NoticiaServicio  {

    @Autowired
    private NoticiaRepositorio noticiaRepo;
    @Autowired
    private UsuarioRepositorio usuarioRepo;
    @Autowired
    private PeriodistaRepositorio periodistaRepo;
    @Autowired
    private AdministradorRepositorio administradorRepo;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo, String idAutor) throws MiException {

        //solo la primera vez verificar si hay autores en la base de datos
        //if(periodistaRepo.count()> 0){}else{};
        LocalDate fechaSubida = LocalDate.now();

        validar(titulo, cuerpo, idAutor);

        Optional<Periodista> optionalAutor = periodistaRepo.findById(idAutor);

        if (optionalAutor.isPresent()) {
            Periodista autor = (Periodista) optionalAutor.get();

            Noticia noticia = new Noticia();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setFechaSubida(fechaSubida);

            //primero actualizo la lista de notcias del autor
            autor.getMisNoticias().add(noticia);

            //luego agrego el autor actualizado a la noticias
            noticia.setAutor(autor);

            usuarioRepo.save(autor);
            //periodistaRepo.save(autor); chat gpt dice que no es necesario

            noticiaRepo.save(noticia);
        } else {
            throw new MiException("No se encontró el periodista con ID: " + idAutor);
        }

    }

    public List<Noticia> listaNoticias() {
        List<Noticia> noticias = new ArrayList<>();

        noticias = noticiaRepo.findAll();
        return noticias;
    }

    @Transactional
    public void modificarNoticia(String id, String titulo, String cuerpo, String autor) throws MiException {

        if (id == null) {
            throw new MiException("El id no puede ser nulo");
        }

//Aqui las validaciones de los atributos comunes a todos los metodos
        validar(titulo, cuerpo, autor);

        Optional<Noticia> respuesta = noticiaRepo.findById(id); //Puede o no contener un valor no nulo, 

        if (respuesta.isPresent()) {                              //respuesta.isPresent() da true si  respuestacontiene un valor no nulo     

            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);

            noticiaRepo.save(noticia);
        }

    }

    public void eliminarNoticia(String id) throws MiException {
        if (id == null) {
            throw new MiException("El ID no puede ser nulo");
        }

        Optional<Noticia> noticia = noticiaRepo.findById(id);

        if (noticia.isPresent()) {
            noticiaRepo.delete(noticia.get());
        } else {
            throw new MiException("No se encontró la noticia con ID: " + id);
        }
    }

    //Para las validaciones que son comunes a todos los metodos
    private void validar(String titulo, String cuerpo, String autor) throws MiException { //, LocalDate fechaSubida
        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El título no puede ser nulo ni estar vacío");
        }

        if (cuerpo == null || cuerpo.isEmpty()) {
            throw new MiException("El cuerpo no puede ser nulo ni estar vacío");
        }

        if (autor == null || autor.isEmpty()) {
            throw new MiException("El autor no puede ser nulo ni estar vacío");
        }

    }


    @Transactional
    public Noticia getOne(String id) throws MiException {

        if (id == null) {
            throw new MiException("El id no puede ser nulo");
        } else {
            return noticiaRepo.getOne(id);
        }

    }

}
