package mx.org.inegi.sistemacaptura.armonizacion.entity.mdea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mdea_a", schema = "public")
public class mdea_armo_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    @Column(name = "id_a", nullable = false)
    private String idA;

    @Column(name = "componente", nullable = false)
    private String componente;

    @Column(name = "subcomponente", nullable = false)
    private String subcomponente;

    @Column(name = "tema", nullable = false)
    private String tema;

    @Column(name = "estadistica1", nullable = false)
    private String estadistica1;

    @Column(name = "estadistica2", nullable = false)
    private String estadistica2;

    @Column(name = "contribucion", nullable = false)
    private String contribucion;

    @Column(name = "comentario_s", nullable = false)
    private String comentarioS;

    public Integer getIdUnique() { return idUnique; }
    public void setIdUnique(Integer idUnique) { this.idUnique = idUnique; }
    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }
    public String getComponente() { return componente; }
    public void setComponente(String componente) { this.componente = componente; }
    public String getSubcomponente() { return subcomponente; }
    public void setSubcomponente(String subcomponente) { this.subcomponente = subcomponente; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getEstadistica1() { return estadistica1; }
    public void setEstadistica1(String estadistica1) { this.estadistica1 = estadistica1; }
    public String getEstadistica2() { return estadistica2; }
    public void setEstadistica2(String estadistica2) { this.estadistica2 = estadistica2; }
    public String getContribucion() { return contribucion; }
    public void setContribucion(String contribucion) { this.contribucion = contribucion; }
    public String getComentarioS() { return comentarioS; }
    public void setComentarioS(String comentarioS) { this.comentarioS = comentarioS; }
}
