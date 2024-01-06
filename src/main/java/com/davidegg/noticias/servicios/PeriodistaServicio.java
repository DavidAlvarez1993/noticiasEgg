/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davidegg.noticias.servicios;

import com.davidegg.noticias.entidades.Noticia;
import com.davidegg.noticias.entidades.Periodista;
import com.davidegg.noticias.enumeracion.Rol;
import com.davidegg.noticias.excepciones.MiException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PeriodistaServicio extends UsuarioServicio {

//    @Autowired
//    private UsuarioRepositorio usuarioRepo;
//    @Autowired
//    private NoticiaRepositorio noticiaRepo;
//    @Autowired
//    //@Qualifier("PeriodistaRepositorio")
//    private PeriodistaRepositorio periodistaRepo;
//    @Autowired
//    private AdministradorRepositorio administradorRepo;

    @Transactional
    public void crearPeriodista(String nombreUsuario, String password, Rol rol, Boolean activo, Integer sueldoMensual) throws MiException {

        LocalDate fechaAlta = LocalDate.now();
        List<Noticia> misNoticias = new ArrayList<>();

        validar(nombreUsuario, password, sueldoMensual);

        Periodista periodista = new Periodista();
        periodista.setNombreUsuario(nombreUsuario);
        periodista.setPassword(password);
        periodista.setFechaAlta(fechaAlta);
        periodista.setRol(rol);
        periodista.setActivo(activo);
        periodista.setSueldoMensual(sueldoMensual);
        periodista.setMisNoticias((ArrayList<Noticia>) misNoticias);

        //usuarioRepo.save(periodista);

        periodistaRepo.save(periodista);
    }

    @Transactional
    public void modificarPeriodista(String id, String nombreUsuario, String password, Boolean activo, Integer sueldoMensual) throws MiException {

        if (id == null) {
            throw new MiException("El id no puede ser nulo");
        }

//Aqui las validaciones de los atributos comunes a todos los metodos
        validar(nombreUsuario, password, sueldoMensual);

        Optional<Periodista> respuesta = periodistaRepo.findById(id); //Puede o no contener un valor no nulo, 

        if (respuesta.isPresent()) {                              //respuesta.isPresent() da true si  respuestacontiene un valor no nulo     

            Periodista periodista = respuesta.get();

            periodista.setNombreUsuario(nombreUsuario);
            periodista.setPassword(password);
            periodista.setActivo(activo);
            //estar atento a que pasa si no ingreso nada como sueldoMensual
            periodista.setSueldoMensual(sueldoMensual);

            //usuarioRepo.save(periodista);

            periodistaRepo.save(periodista);
        }

    }

    private void validar(String nombreUsuario, String password, Integer sueldoMensual) throws MiException {
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new MiException("El nombre del periodista no puede ser nulo ni estar vacío");
        }

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("La contraseña no puede ser nulo ni estar vacío ni tener menos de 6 caracteres");
        }

        if (sueldoMensual == null) {
            throw new MiException("El saldo mensual no puede ser nulo");
        }
    }

    public List<Periodista> listaPeriodistas() {
        List<Periodista> periodistas = new ArrayList<>();

        periodistas = periodistaRepo.findAll();
        return periodistas;
    }
    
    @Transactional
    public Periodista getOne(String id) throws MiException {

        if (id == null) {
            throw new MiException("El id no puede ser nulo");
        } else {
            return periodistaRepo.getOne(id);
        }

    }

    public void TODO() {

    }
}
