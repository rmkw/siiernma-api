/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.entity.microdatos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

public class microdatos_armo_dto {

    private Integer idUnique;
    private String idA;
    private String urlAcceso;
    private String descriptor;
    private String urlDescriptor;
    private String tabla;
    private String campo;
    private String comentarioA;

    public microdatos_armo_dto() {
    }

    public microdatos_armo_dto(Integer idUnique, String idA,
            String urlAcceso, String descriptor, String urlDescriptor,
            String tabla, String campo, String comentarioA) {
        this.idUnique = idUnique;
        this.idA = idA;
        this.urlAcceso = urlAcceso;
        this.descriptor = descriptor;
        this.urlDescriptor = urlDescriptor;
        this.tabla = tabla;
        this.campo = campo;
        this.comentarioA = comentarioA;
    }

    public Integer getIdUnique() {
        return idUnique;
    }

    public void setIdUnique(Integer idUnique) {
        this.idUnique = idUnique;
    }

    public String getIdA() {
        return idA;
    }

    public void setIdA(String idA) {
        this.idA = idA;
    }

    public String getUrlAcceso() {
        return urlAcceso;
    }

    public void setUrlAcceso(String urlAcceso) {
        this.urlAcceso = urlAcceso;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getUrlDescriptor() {
        return urlDescriptor;
    }

    public void setUrlDescriptor(String urlDescriptor) {
        this.urlDescriptor = urlDescriptor;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getComentarioA() {
        return comentarioA;
    }

    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }
}
