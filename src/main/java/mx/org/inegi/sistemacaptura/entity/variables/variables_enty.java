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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "variables", schema = "seleccion")
public class variables_enty {

    @Id
    @Column(name = "id_a", nullable = false)
    private String idA;

    @Column(name = "id_s", nullable = false)
    private String idS;

    @Column(name = "id_fuente", nullable = false)
    private String idFuente;

    @Column(name = "acronimo", nullable = false)
    private String acronimo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "definicion", nullable = false)
    private String definicion;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "comentario_s", nullable = false)
    private String comentarioS;

    @Column(name = "mdea", nullable = false)
    private Boolean mdea;

    @Column(name = "ods", nullable = false)
    private Boolean ods;

    @Column(name = "prioridad")
    private Integer prioridad;

    @Column(name = "revisada", nullable = false)
    private Boolean revisada;

    @Column(name = "fecha_revision")
    private LocalDateTime fechaRevision;

    @Column(name = "responsable_revision")
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
    public void setFechaRevision(LocalDateTime fechaRevision) { this.fechaRevision = fechaRevision; }

    public Integer getResponsableRevision() { return responsableRevision; }
    public void setResponsableRevision(Integer responsableRevision) {
        this.responsableRevision = responsableRevision;
    }
}