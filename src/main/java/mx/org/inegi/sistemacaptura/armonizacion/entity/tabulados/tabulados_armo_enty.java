package mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tabulados", schema = "armonizacion")
public class tabulados_armo_enty {

    @Id
    @Column(name = "id_tabulado")
    private String idTabulado;

    @Column(name = "tabulado", nullable = false)
    private String tabulado;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "hoja", nullable = false)
    private String hoja;

    @Column(name = "url_acceso", nullable = false)
    private String urlAcceso;

    @Column(name = "url_descarga", nullable = false)
    private String urlDescarga;

    @Column(name = "comentario_a", nullable = false)
    private String comentarioA;

    public tabulados_armo_enty() {
    }

    public tabulados_armo_enty(String idTabulado, String tabulado,
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
