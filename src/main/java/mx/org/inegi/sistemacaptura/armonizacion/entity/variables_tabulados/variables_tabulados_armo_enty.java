package mx.org.inegi.sistemacaptura.armonizacion.entity.variables_tabulados;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "variables_tabulados", schema = "armonizacion")
public class variables_tabulados_armo_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    @Column(name = "id_a", nullable = false)
    private String idA;

    @Column(name = "id_tabulado", nullable = false)
    private String idTabulado;

    @Column(name = "comentario_a", nullable = false)
    private String comentarioA;

    public variables_tabulados_armo_enty() {
    }

    public variables_tabulados_armo_enty(Integer idUnique, String idA,
            String idTabulado, String comentarioA) {
        this.idUnique = idUnique;
        this.idA = idA;
        this.idTabulado = idTabulado;
        this.comentarioA = comentarioA;
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
}
