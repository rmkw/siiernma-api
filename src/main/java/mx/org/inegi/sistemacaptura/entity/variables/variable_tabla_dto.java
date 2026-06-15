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

public class variable_tabla_dto {

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
    private Integer prioridad;
    private Boolean revisada;
    private LocalDateTime fechaRevision;
    private Integer responsableRevision;

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
}
