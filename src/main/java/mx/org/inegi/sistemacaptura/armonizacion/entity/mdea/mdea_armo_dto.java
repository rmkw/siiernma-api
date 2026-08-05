package mx.org.inegi.sistemacaptura.armonizacion.entity.mdea;

public class mdea_armo_dto {

    private Integer idUnique;
    private String idA;
    private String componente;
    private String subcomponente;
    private String tema;
    private String estadistica1;
    private String estadistica2;
    private String contribucion;
    private String comentarioS;

    public Integer getIdUnique() { return idUnique; }
    public void setIdUnique(Integer idUnique) { this.idUnique = idUnique; }
    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }
    public String getComponente() { return componente; }
    public void setComponente(String componente) { this.componente = componente; }
    public String getSubcomponente() { return subcomponente; }
    public void setSubcomponente(String subcomponente) { this.subcomponente = subcomponente; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getEstadistica1() { return estadistica1; }
    public void setEstadistica1(String estadistica1) { this.estadistica1 = estadistica1; }
    public String getEstadistica2() { return estadistica2; }
    public void setEstadistica2(String estadistica2) { this.estadistica2 = estadistica2; }
    public String getContribucion() { return contribucion; }
    public void setContribucion(String contribucion) { this.contribucion = contribucion; }
    public String getComentarioS() { return comentarioS; }
    public void setComentarioS(String comentarioS) { this.comentarioS = comentarioS; }
}
