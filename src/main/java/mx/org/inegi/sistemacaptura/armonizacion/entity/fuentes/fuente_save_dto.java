/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */

public class fuente_save_dto {

    private String idFuenteSeleccion;
    private String acronimo;
    private String fuente;
    private String url;
    private String edicion;
    private String comentarioS;
    private String comentarioA;

    public String getIdFuenteSeleccion() {
        return idFuenteSeleccion;
    }

    public void setIdFuenteSeleccion(String idFuenteSeleccion) {
        this.idFuenteSeleccion = idFuenteSeleccion;
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
}
