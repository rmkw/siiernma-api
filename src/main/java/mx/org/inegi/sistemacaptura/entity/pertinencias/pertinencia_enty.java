package mx.org.inegi.sistemacaptura.entity.pertinencias;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pertinencia", schema = "seleccion")
public class pertinencia_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    @Column(name = "id_a", nullable = false)
    private String idA;

    @Column(name = "id_s", nullable = false)
    private String idS;

    @Column(name = "pertinencia", nullable = false)
    private String pertinencia;

    @Column(name = "contribucion")
    private String contribucion;

    @Column(name = "viabilidad")
    private String viabilidad;

    @Column(name = "propuesta")
    private String propuesta;

    @Column(name = "comentario_s")
    private String comentarioS;

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

    public String getIdS() {
        return idS;
    }

    public void setIdS(String idS) {
        this.idS = idS;
    }

    public String getPertinencia() {
        return pertinencia;
    }

    public void setPertinencia(String pertinencia) {
        this.pertinencia = pertinencia;
    }

    public String getContribucion() {
        return contribucion;
    }

    public void setContribucion(String contribucion) {
        this.contribucion = contribucion;
    }

    public String getViabilidad() {
        return viabilidad;
    }

    public void setViabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
    }

    public String getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(String propuesta) {
        this.propuesta = propuesta;
    }

    public String getComentarioS() {
        return comentarioS;
    }

    public void setComentarioS(String comentarioS) {
        this.comentarioS = comentarioS;
    }
}