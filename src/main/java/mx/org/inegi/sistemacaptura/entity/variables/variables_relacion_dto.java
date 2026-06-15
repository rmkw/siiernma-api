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
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_enty;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_enty;
import mx.org.inegi.sistemacaptura.entity.pertinencias.pertinencia_enty;

public class variables_relacion_dto {

    private String idA;
    private String idS;
    private String idFuente;
    private String acronimo;
    private String nombre;
    private String definicion;
    private String url;
    private String comentarioS;
    private Boolean mdea;
    private Boolean ods;
    private Integer responsableRegister;
    private Integer responsableActualizacion;
    private Integer prioridad;
    private Boolean revisada;
    private LocalDateTime fechaRevision;
    private Integer responsableRevision;
    private List<mdea_enty> mdeas;
    private List<ods_enty> odsList;
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

    public String getDefinicion() { return definicion; }
    public void setDefinicion(String definicion) { this.definicion = definicion; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getComentarioS() { return comentarioS; }
    public void setComentarioS(String comentarioS) { this.comentarioS = comentarioS; }

    public Boolean getMdea() { return mdea; }
    public void setMdea(Boolean mdea) { this.mdea = mdea; }

    public Boolean getOds() { return ods; }
    public void setOds(Boolean ods) { this.ods = ods; }

    public Integer getResponsableRegister() { return responsableRegister; }
    public void setResponsableRegister(Integer responsableRegister) {
        this.responsableRegister = responsableRegister;
    }

    public Integer getResponsableActualizacion() { return responsableActualizacion; }
    public void setResponsableActualizacion(Integer responsableActualizacion) {
        this.responsableActualizacion = responsableActualizacion;
    }

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

    public List<mdea_enty> getMdeas() { return mdeas; }
    public void setMdeas(List<mdea_enty> mdeas) { this.mdeas = mdeas; }

    public List<ods_enty> getOdsList() { return odsList; }
    public void setOdsList(List<ods_enty> odsList) { this.odsList = odsList; }

    public pertinencia_enty getPertinencia() { return pertinencia; }
    public void setPertinencia(pertinencia_enty pertinencia) {
        this.pertinencia = pertinencia;
    }
}
