
package com.davidegg.noticias.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
public class Periodista extends Usuario{
    @OneToMany(mappedBy = "autor")
    private List<Noticia> misNoticias;
    private Integer sueldoMensual;
}
