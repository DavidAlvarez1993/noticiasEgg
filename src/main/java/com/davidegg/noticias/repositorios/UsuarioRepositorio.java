/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.davidegg.noticias.repositorios;

import com.davidegg.noticias.entidades.Usuario;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository//("UsuarioRepositorio")
//@Primary
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    
}
