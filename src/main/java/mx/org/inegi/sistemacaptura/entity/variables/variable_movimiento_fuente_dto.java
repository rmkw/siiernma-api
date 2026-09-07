package mx.org.inegi.sistemacaptura.entity.variables;

import java.util.List;

public class variable_movimiento_fuente_dto {

    private String idFuenteOrigen;
    private String idFuenteDestino;
    private List<String> idsVariables;

    public String getIdFuenteOrigen() { return idFuenteOrigen; }
    public void setIdFuenteOrigen(String idFuenteOrigen) {
        this.idFuenteOrigen = idFuenteOrigen;
    }

    public String getIdFuenteDestino() { return idFuenteDestino; }
    public void setIdFuenteDestino(String idFuenteDestino) {
        this.idFuenteDestino = idFuenteDestino;
    }

    public List<String> getIdsVariables() { return idsVariables; }
    public void setIdsVariables(List<String> idsVariables) {
        this.idsVariables = idsVariables;
    }
}
