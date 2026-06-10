/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LUIS.CASTANEDAL
 */
package mx.org.inegi.sistemacaptura.entity.mdea.catalogo;

import javax.persistence.*;

@Entity
@Table(name = "mdea_temas", schema = "catalogos")
public class cat_tema_enty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unique_id")
    private Integer uniqueId;

    @Column(name = "id_componente", nullable = false)
    private Integer idComponente;

    @Column(name = "id_subcomponente", nullable = false)
    private Integer idSubcomponente;

    @Column(name = "id_tema", nullable = false)
    private Integer idTema;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "definicion", nullable = false)
    private String definicion;

    public Integer getUniqueId() { return uniqueId; }
    public void setUniqueId(Integer uniqueId) { this.uniqueId = uniqueId; }
    public Integer getIdComponente() { return idComponente; }
    public void setIdComponente(Integer idComponente) { this.idComponente = idComponente; }
    public Integer getIdSubcomponente() { return idSubcomponente; }
    public void setIdSubcomponente(Integer idSubcomponente) { this.idSubcomponente = idSubcomponente; }
    public Integer getIdTema() { return idTema; }
    public void setIdTema(Integer idTema) { this.idTema = idTema; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDefinicion() { return definicion; }
    public void setDefinicion(String definicion) { this.definicion = definicion; }
}