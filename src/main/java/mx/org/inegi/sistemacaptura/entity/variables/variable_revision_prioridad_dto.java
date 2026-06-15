/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.time.LocalDateTime;
import java.util.List;
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_traduccion_dto;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_traduccion_dto;
import mx.org.inegi.sistemacaptura.entity.pertinencias.pertinencia_enty;

public class variable_revision_prioridad_dto {

    private String idA;
    private String idS;
    private String idFuente;
    private String acronimo;
    private String nombre;
    private String url;
    private String definicion;
    private Integer prioridad;
    private Boolean revisada;
    private LocalDateTime fechaRevision;
    private Integer responsableRevision;
    private Boolean mdea;
    private Boolean ods;
    private List<mdea_traduccion_dto> mdeas;
    private List<ods_traduccion_dto> odsList;
    private pertinencia_enty pertinencia;

    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }

    public String getIdS() { return idS; }
    public void setIdS(String idS) { this.idS = idS; }

    public String getIdFuente() { return idFuente; }
    public void setIdFuente(String idFuente) { this.idFuente = idFuente; }

    public String getAcronimo() { return acronimo; }
    public void setAcronimo(String acronimo) { this.acronimo = acronimo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getDefinicion() { return definicion; }
    public void setDefinicion(String definicion) { this.definicion = definicion; }

    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }

    public Boolean getRevisada() { return revisada; }
    public void setRevisada(Boolean revisada) { this.revisada = revisada; }

    public LocalDateTime getFechaRevision() { return fechaRevision; }
    public void setFechaRevision(LocalDateTime fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Integer getResponsableRevision() { return responsableRevision; }
    public void setResponsableRevision(Integer responsableRevision) {
        this.responsableRevision = responsableRevision;
    }

    public Boolean getMdea() { return mdea; }
    public void setMdea(Boolean mdea) { this.mdea = mdea; }

    public Boolean getOds() { return ods; }
    public void setOds(Boolean ods) { this.ods = ods; }

    public List<mdea_traduccion_dto> getMdeas() { return mdeas; }
    public void setMdeas(List<mdea_traduccion_dto> mdeas) { this.mdeas = mdeas; }

    public List<ods_traduccion_dto> getOdsList() { return odsList; }
    public void setOdsList(List<ods_traduccion_dto> odsList) {
        this.odsList = odsList;
    }

    public pertinencia_enty getPertinencia() { return pertinencia; }
    public void setPertinencia(pertinencia_enty pertinencia) {
        this.pertinencia = pertinencia;
    }
}
