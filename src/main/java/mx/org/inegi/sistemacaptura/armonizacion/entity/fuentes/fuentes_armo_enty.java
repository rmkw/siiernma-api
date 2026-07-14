/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "fuentes", schema = "armonizacion")
public class fuentes_armo_enty {

    @Id
    @Column(name = "id_fuente_seleccion", nullable = false)
    private String idFuenteSeleccion;

    @Column(name = "id_fuente", insertable = false, updatable = false)
    private String idFuente;

    @Column(name = "acronimo", nullable = false)
    private String acronimo;

    @Column(name = "fuente", nullable = false)
    private String fuente;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "edicion", nullable = false)
    private String edicion;

    @Column(name = "comentario_s")
    private String comentarioS;

    @Column(name = "comentario_a")
    private String comentarioA;

    @Transient
    private boolean reutilizada;

    public String getIdFuenteSeleccion() {
        return idFuenteSeleccion;
    }

    public void setIdFuenteSeleccion(String idFuenteSeleccion) {
        this.idFuenteSeleccion = idFuenteSeleccion;
    }

    public String getIdFuente() {
        return idFuente;
    }

    public void setIdFuente(String idFuente) {
        this.idFuente = idFuente;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getComentarioS() {
        return comentarioS;
    }

    public void setComentarioS(String comentarioS) {
        this.comentarioS = comentarioS;
    }

    public String getComentarioA() {
        return comentarioA;
    }

    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }

    public boolean isReutilizada() {
        return reutilizada;
    }

    public void setReutilizada(boolean reutilizada) {
        this.reutilizada = reutilizada;
    }
}
