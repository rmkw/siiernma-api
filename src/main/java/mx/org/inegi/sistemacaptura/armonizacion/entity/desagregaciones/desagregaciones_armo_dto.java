package mx.org.inegi.sistemacaptura.armonizacion.entity.desagregaciones;

public class desagregaciones_armo_dto {

    private Integer idUnique;
    private String idTabulado;
    private String coberturaDesagregacion;
    private String comentarioA;

    public desagregaciones_armo_dto() {
    }

    public desagregaciones_armo_dto(Integer idUnique, String idTabulado,
            String coberturaDesagregacion, String comentarioA) {
        this.idUnique = idUnique;
        this.idTabulado = idTabulado;
        this.coberturaDesagregacion = coberturaDesagregacion;
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

    public String getCoberturaDesagregacion() {
        return coberturaDesagregacion;
    }

    public void setCoberturaDesagregacion(String coberturaDesagregacion) {
        this.coberturaDesagregacion = coberturaDesagregacion;
    }

    public String getComentarioA() {
        return comentarioA;
    }

    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }
}
