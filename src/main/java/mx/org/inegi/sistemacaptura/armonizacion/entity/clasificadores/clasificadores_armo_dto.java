package mx.org.inegi.sistemacaptura.armonizacion.entity.clasificadores;

public class clasificadores_armo_dto {

    private Integer idUnique;

    private String idA;

    private String clasificador;

    private String version;

    private String url;

    private String comentariosA;

    public clasificadores_armo_dto() {
    }

    public clasificadores_armo_dto(Integer idUnique, String idA, String clasificador,
            String version, String url, String comentariosA) {
        this.idUnique = idUnique;
        this.idA = idA;
        this.clasificador = clasificador;
        this.version = version;
        this.url = url;
        this.comentariosA = comentariosA;
    }

    public Integer getIdUnique() {
        return idUnique;
    }

    public void setIdUnique(Integer idUnique) {
        this.idUnique = idUnique;
    }

    public String getIdA() {
        return idA;
    }

    public void setIdA(String idA) {
        this.idA = idA;
    }

    public String getClasificador() {
        return clasificador;
    }

    public void setClasificador(String clasificador) {
        this.clasificador = clasificador;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComentariosA() {
        return comentariosA;
    }

    public void setComentariosA(String comentariosA) {
        this.comentariosA = comentariosA;
    }
}
