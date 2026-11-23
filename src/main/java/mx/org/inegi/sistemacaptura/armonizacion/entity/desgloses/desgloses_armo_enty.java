package mx.org.inegi.sistemacaptura.armonizacion.entity.desgloses;

import mx.org.inegi.sistemacaptura.config.DatabaseTables;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = DatabaseTables.DESGLOSE_A, schema = DatabaseTables.ARMONIZACION)
public class desgloses_armo_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    @Column(name = "id_tabulado", nullable = false)
    private String idTabulado;

    @Column(name = "desglose", nullable = false)
    private String desglose;

    @Column(name = "comentario_a", nullable = false)
    private String comentarioA;

    public desgloses_armo_enty() {
    }

    public desgloses_armo_enty(Integer idUnique, String idTabulado,
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
