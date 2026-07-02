package mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados;

public class tabulados_armo_dto {

    private String idTabulado;
    private String tabulado;
    private String tipo;
    private String hoja;
    private String urlAcceso;
    private String urlDescarga;
    private String comentarioA;

    public tabulados_armo_dto() {
    }

    public tabulados_armo_dto(String idTabulado, String tabulado,
            String tipo, String hoja, String urlAcceso,
            String urlDescarga, String comentarioA) {
        this.idTabulado = idTabulado;
        this.tabulado = tabulado;
        this.tipo = tipo;
        this.hoja = hoja;
        this.urlAcceso = urlAcceso;
        this.urlDescarga = urlDescarga;
        this.comentarioA = comentarioA;
    }

    public String getIdTabulado() {
        return idTabulado;
    }

    public void setIdTabulado(String idTabulado) {
        this.idTabulado = idTabulado;
    }

    public String getTabulado() {
        return tabulado;
    }

    public void setTabulado(String tabulado) {
        this.tabulado = tabulado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHoja() {
        return hoja;
    }

    public void setHoja(String hoja) {
        this.hoja = hoja;
    }

    public String getUrlAcceso() {
        return urlAcceso;
    }

    public void setUrlAcceso(String urlAcceso) {
        this.urlAcceso = urlAcceso;
    }

    public String getUrlDescarga() {
        return urlDescarga;
    }

    public void setUrlDescarga(String urlDescarga) {
        this.urlDescarga = urlDescarga;
    }

    public String getComentarioA() {
        return comentarioA;
    }

    public void setComentarioA(String comentarioA) {
        this.comentarioA = comentarioA;
    }
}
