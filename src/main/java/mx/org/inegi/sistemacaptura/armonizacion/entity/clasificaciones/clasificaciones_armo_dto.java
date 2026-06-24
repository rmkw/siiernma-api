/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.entity.clasificaciones;

/**
 *
 * @author LUIS.CASTANEDAL
 */

public class clasificaciones_armo_dto {

    private Integer idUnique;
    private String idA;
    private String clase;
    private String comentarioA;

    public clasificaciones_armo_dto() {
    }

    public clasificaciones_armo_dto(Integer idUnique, String idA, String clase,
            String comentarioA) {
        this.idUnique = idUnique;
        this.idA = idA;
        this.clase = clase;
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

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getComentarioA() {
        return comentarioA;
    }

    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }
}
