package mx.org.inegi.sistemacaptura.armonizacion.entity.ods;

public class ods_armo_dto {

    private Integer idUnique;
    private String idA;
    private String objetivo;
    private String meta;
    private String indicador;
    private String contribucion;
    private String comentarioS;

    public Integer getIdUnique() { return idUnique; }
    public void setIdUnique(Integer idUnique) { this.idUnique = idUnique; }
    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public String getMeta() { return meta; }
    public void setMeta(String meta) { this.meta = meta; }
    public String getIndicador() { return indicador; }
    public void setIndicador(String indicador) { this.indicador = indicador; }
    public String getContribucion() { return contribucion; }
    public void setContribucion(String contribucion) { this.contribucion = contribucion; }
    public String getComentarioS() { return comentarioS; }
    public void setComentarioS(String comentarioS) { this.comentarioS = comentarioS; }
}
