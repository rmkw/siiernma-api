/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.procesos;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "procesos_s", schema = "public")
public class procesos_enty {
    @Id
    @Column(name = "acronimo", nullable = false)
    private String acronimo;

    @Column(name = "proceso")
    private String proceso;

    @Column(name = "metodo")
    private String metodo;

    @Column(name = "objetivo")
    private String objetivo;

    @Column(name = "pobjeto")
    private String pobjeto;

    @Column(name = "uobservacion")
    private String uobservacion;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "periodicidad")
    private String periodicidad;

    @Column(name = "iin", length = 2)
    private String iin;

    @Column(name = "estatus")
    private String estatus;

    @Column(name = "ipi")
    private Boolean ipi;

    @Column(name = "inicio")
    private String inicio;

    @Column(name = "conclusion")
    private String conclusion;

    public String getAcronimo() { return acronimo; }
    public void setAcronimo(String acronimo) { this.acronimo = acronimo; }

    public String getProceso() { return proceso; }
    public void setProceso(String proceso) { this.proceso = proceso; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public String getPobjeto() { return pobjeto; }
    public void setPobjeto(String pobjeto) { this.pobjeto = pobjeto; }

    public String getUobservacion() { return uobservacion; }
    public void setUobservacion(String uobservacion) { this.uobservacion = uobservacion; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public String getPeriodicidad() { return periodicidad; }
    public void setPeriodicidad(String periodicidad) { this.periodicidad = periodicidad; }

    public String getIin() { return iin; }
    public void setIin(String iin) { this.iin = iin; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }

    public Boolean getIpi() { return ipi; }
    public void setIpi(Boolean ipi) { this.ipi = ipi; }

    public String getInicio() { return inicio; }
    public void setInicio(String inicio) { this.inicio = inicio; }

    public String getConclusion() { return conclusion; }
    public void setConclusion(String conclusion) { this.conclusion = conclusion; }
}
