package mx.org.inegi.sistemacaptura.armonizacion.entity.clasificadores;

import mx.org.inegi.sistemacaptura.config.DatabaseTables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = DatabaseTables.CLASIFICADORES_A, schema = DatabaseTables.ARMONIZACION)
public class clasificadores_armo_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    @Column(name = "id_a", nullable = false)
    private String idA;

    @Column(name = "clasificador")
    private String clasificador;

    @Column(name = "version")
    private String version;

    @Column(name = "url")
    private String url;

    @Column(name = "comentarios_a")
    private String comentariosA;

    public clasificadores_armo_enty() {
    }

    public clasificadores_armo_enty(Integer idUnique, String idA, String clasificador,
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
