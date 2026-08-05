package mx.org.inegi.sistemacaptura.armonizacion.entity.pertinencias;

public class pertinencia_armo_dto {

    private Integer idUnique;
    private String idA;
    private String pertinencia;
    private String contribucion;
    private String viabilidad;
    private String propuesta;
    private String comentarioS;

    public Integer getIdUnique() { return idUnique; }
    public void setIdUnique(Integer idUnique) { this.idUnique = idUnique; }
    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }
    public String getPertinencia() { return pertinencia; }
    public void setPertinencia(String pertinencia) { this.pertinencia = pertinencia; }
    public String getContribucion() { return contribucion; }
    public void setContribucion(String contribucion) { this.contribucion = contribucion; }
    public String getViabilidad() { return viabilidad; }
    public void setViabilidad(String viabilidad) { this.viabilidad = viabilidad; }
    public String getPropuesta() { return propuesta; }
    public void setPropuesta(String propuesta) { this.propuesta = propuesta; }
    public String getComentarioS() { return comentarioS; }
    public void setComentarioS(String comentarioS) { this.comentarioS = comentarioS; }
}
