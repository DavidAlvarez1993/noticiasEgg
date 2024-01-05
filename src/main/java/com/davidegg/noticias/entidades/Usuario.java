
package com.davidegg.noticias.entidades;

import com.davidegg.noticias.enumeracion.Rol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter @Setter
@ToString
@NoArgsConstructor @AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String nombreUsuario;
    private String password;
    private LocalDate fechaAlta;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    private Boolean activo;
}
