package mx.org.inegi.sistemacaptura.armonizacion.entity.variables_tabulados;

public class variables_tabulados_armo_dto {

    private Integer idUnique;
    private String idA;
    private String idTabulado;
    private String comentarioA;
    private String variableA;
    private String variableS;

    public variables_tabulados_armo_dto() {
    }

    public variables_tabulados_armo_dto(Integer idUnique, String idA,
            String idTabulado, String comentarioA, String variableA,
            String variableS) {
        this.idUnique = idUnique;
        this.idA = idA;
        this.idTabulado = idTabulado;
        this.comentarioA = comentarioA;
        this.variableA = variableA;
        this.variableS = variableS;
    }

    public Integer getIdUnique() { return idUnique; }
    public void setIdUnique(Integer idUnique) { this.idUnique = idUnique; }
    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }
    public String getIdTabulado() { return idTabulado; }
    public void setIdTabulado(String idTabulado) {
        this.idTabulado = idTabulado;
    }
    public String getComentarioA() { return comentarioA; }
    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }
    public String getVariableA() { return variableA; }
    public void setVariableA(String variableA) { this.variableA = variableA; }
    public String getVariableS() { return variableS; }
    public void setVariableS(String variableS) { this.variableS = variableS; }
}
