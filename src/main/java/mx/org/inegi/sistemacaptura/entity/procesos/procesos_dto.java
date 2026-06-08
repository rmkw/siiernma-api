/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.procesos;

/**
 *
 * @author LUIS.CASTANEDAL
 */
public class procesos_dto {
    private String acronimo;
    private String proceso;
    private String metodo;
    private String objetivo;
    private String pobjeto;
    private String uobservacion;
    private String unidad;
    private String periodicidad;
    private String iin;
    private String estatus;
    private Boolean ipi;
    private String inicio;
    private String conclusion;
    private Long totalVariables;

    public procesos_dto(String acronimo, String proceso, String metodo,
            String objetivo, String pobjeto, String uobservacion,
            String unidad, String periodicidad, String iin, String estatus,
            Boolean ipi, String inicio, String conclusion, Long totalVariables) {
        this.acronimo = acronimo;
        this.proceso = proceso;
        this.metodo = metodo;
        this.objetivo = objetivo;
        this.pobjeto = pobjeto;
        this.uobservacion = uobservacion;
        this.unidad = unidad;
        this.periodicidad = periodicidad;
        this.iin = iin;
        this.estatus = estatus;
        this.ipi = ipi;
        this.inicio = inicio;
        this.conclusion = conclusion;
        this.totalVariables = totalVariables;
    }

    public String getAcronimo() { return acronimo; }
    public String getProceso() { return proceso; }
    public String getMetodo() { return metodo; }
    public String getObjetivo() { return objetivo; }
    public String getPobjeto() { return pobjeto; }
    public String getUobservacion() { return uobservacion; }
    public String getUnidad() { return unidad; }
    public String getPeriodicidad() { return periodicidad; }
    public String getIin() { return iin; }
    public String getEstatus() { return estatus; }
    public Boolean getIpi() { return ipi; }
    public String getInicio() { return inicio; }
    public String getConclusion() { return conclusion; }
    public Long getTotalVariables() { return totalVariables; }
}
