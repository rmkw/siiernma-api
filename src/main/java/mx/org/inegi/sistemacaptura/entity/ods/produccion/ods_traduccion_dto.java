/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.ods.produccion;

/**
 *
 * @author LUIS.CASTANEDAL
 */

public class ods_traduccion_dto {

    private Integer idUnique;
    private String idA;
    private String idS;

    private String objetivo;
    private String objetivoNombre;

    private String meta;
    private String metaNombre;

    private String indicador;
    private String indicadorNombre;

    private String contribucion;
    private String comentarioS;

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

    public String getIdS() {
        return idS;
    }

    public void setIdS(String idS) {
        this.idS = idS;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getObjetivoNombre() {
        return objetivoNombre;
    }

    public void setObjetivoNombre(String objetivoNombre) {
        this.objetivoNombre = objetivoNombre;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getMetaNombre() {
        return metaNombre;
    }

    public void setMetaNombre(String metaNombre) {
        this.metaNombre = metaNombre;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getIndicadorNombre() {
        return indicadorNombre;
    }

    public void setIndicadorNombre(String indicadorNombre) {
        this.indicadorNombre = indicadorNombre;
    }

    public String getContribucion() {
        return contribucion;
    }

    public void setContribucion(String contribucion) {
        this.contribucion = contribucion;
    }

    public String getComentarioS() {
        return comentarioS;
    }

    public void setComentarioS(String comentarioS) {
        this.comentarioS = comentarioS;
    }
}
