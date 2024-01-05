package com.davidegg.noticias;

import com.davidegg.noticias.excepciones.MiException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "repositorios")
@ComponentScan(basePackages = "com.davidegg.noticias")
public class NoticiasApplication {

    public static void main(String[] args) throws MiException {
        SpringApplication.run(NoticiasApplication.class, args);
        System.out.println("Hola mundo");
        
        
    }

}

//netstat -ano | findstr "8080"
//taskkill /F /PID 6020