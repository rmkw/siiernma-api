package mx.org.inegi.sistemacaptura.armonizacion.entity.desagregaciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "desagregacion", schema = "armonizacion")
public class desagregaciones_armo_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    @Column(name = "id_tabulado", nullable = false)
    private String idTabulado;

    @Column(name = "cobertura_desagregacion", nullable = false)
    private String coberturaDesagregacion;

    @Column(name = "comentario_a", nullable = false)
    private String comentarioA;

    public desagregaciones_armo_enty() {
    }

    public desagregaciones_armo_enty(Integer idUnique, String idTabulado,
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
