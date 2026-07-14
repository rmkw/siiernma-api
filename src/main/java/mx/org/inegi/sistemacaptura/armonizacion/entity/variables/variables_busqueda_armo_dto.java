package mx.org.inegi.sistemacaptura.armonizacion.entity.variables;

public class variables_busqueda_armo_dto {

    private String idA;
    private String variableA;
    private String variableS;

    public variables_busqueda_armo_dto() {
    }

    public variables_busqueda_armo_dto(
            String idA, String variableA, String variableS) {
        this.idA = idA;
        this.variableA = variableA;
        this.variableS = variableS;
    }

    public String getIdA() {
        return idA;
    }

    public void setIdA(String idA) {
        this.idA = idA;
    }

    public String getVariableA() {
        return variableA;
    }

    public void setVariableA(String variableA) {
        this.variableA = variableA;
    }

    public String getVariableS() {
        return variableS;
    }

    public void setVariableS(String variableS) {
        this.variableS = variableS;
    }
}
