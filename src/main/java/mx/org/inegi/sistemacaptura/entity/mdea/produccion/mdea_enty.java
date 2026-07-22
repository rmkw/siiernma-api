/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.mdea.produccion;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import javax.persistence.*;

@Entity
@Table(name = "mdea", schema = "seleccion")
public class mdea_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    @Column(name = "id_a")
    private String idA;

    @Column(name = "id_s", nullable = false)
    private String idS;

    @Column(name = "componente")
    private String componente;

    @Column(name = "subcomponente")
    private String subcomponente;

    @Column(name = "tema")
    private String tema;

    @Column(name = "estadistica1")
    private String estadistica1;

    @Column(name = "estadistica2")
    private String estadistica2;

    @Column(name = "contribucion")
    private String contribucion;

    @Column(name = "comentario_s")
    private String comentarioS;

    public Integer getIdUnique() { return idUnique; }
    public void setIdUnique(Integer idUnique) { this.idUnique = idUnique; }

    public String getIdA() { return idA; }
    public void setIdA(String idA) { this.idA = idA; }

    public String getIdS() { return idS; }
    public void setIdS(String idS) { this.idS = idS; }

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
