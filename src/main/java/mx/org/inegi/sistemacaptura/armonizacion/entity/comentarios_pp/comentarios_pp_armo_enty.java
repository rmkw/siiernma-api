/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.entity.comentarios_pp;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comentarios_pp_a", schema = "public")
public class comentarios_pp_armo_enty {

    @Id
    @Column(name = "acronimo", nullable = false, length = 50)
    private String acronimo;

    @Column(name = "comentario_s", columnDefinition = "TEXT")
    private String comentarioS;

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getComentarioS() {
        return comentarioS;
    }

    public void setComentarioS(String comentarioS) {
        this.comentarioS = comentarioS;
    }
}