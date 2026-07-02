package mx.org.inegi.sistemacaptura.armonizacion.entity.desgloses;

public class desgloses_armo_dto {

    private Integer idUnique;
    private String idTabulado;
    private String desglose;
    private String comentarioA;

    public desgloses_armo_dto() {
    }

    public desgloses_armo_dto(Integer idUnique, String idTabulado,
            String desglose, String comentarioA) {
        this.idUnique = idUnique;
        this.idTabulado = idTabulado;
        this.desglose = desglose;
        this.comentarioA = comentarioA;
    }

    public Integer getIdUnique() {
        return idUnique;
    }

    public void setIdUnique(Integer idUnique) {
        this.idUnique = idUnique;
    }

    public String getIdTabulado() {
        return idTabulado;
    }

    public void setIdTabulado(String idTabulado) {
        this.idTabulado = idTabulado;
    }

    public String getDesglose() {
        return desglose;
    }

    public void setDesglose(String desglose) {
        this.desglose = desglose;
    }

    public String getComentarioA() {
        return comentarioA;
    }

    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }
}
