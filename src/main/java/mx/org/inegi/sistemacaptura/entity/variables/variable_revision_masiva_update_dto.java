/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;

public class variable_revision_masiva_update_dto {

    private List<String> idsA;
    private Integer prioridad;
    private Boolean revisada;
    private Integer responsableRevision;

    public List<String> getIdsA() { return idsA; }
    public void setIdsA(List<String> idsA) { this.idsA = idsA; }

    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }

    public Boolean getRevisada() { return revisada; }
    public void setRevisada(Boolean revisada) { this.revisada = revisada; }

    public Integer getResponsableRevision() { return responsableRevision; }
    public void setResponsableRevision(Integer responsableRevision) {
        this.responsableRevision = responsableRevision;
    }
}
