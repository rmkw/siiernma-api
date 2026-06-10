/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.mdea.catalogo;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import javax.persistence.*;

@Entity
@Table(name = "mdea_estadisticos2", schema = "catalogos")
public class cat_estadistico2_enty {
    @Id
    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "id_componente", nullable = false)
    private Integer idComponente;

    @Column(name = "id_subcomponente", nullable = false)
    private Integer idSubcomponente;

    @Column(name = "id_tema", nullable = false)
    private Integer idTema;

    @Column(name = "id_estadistico1", nullable = false)
    private String idEstadistico1;

    @Column(name = "id_estadistico2", nullable = false)
    private Integer idEstadistico2;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    public String getUniqueId() { return uniqueId; }
    public void setUniqueId(String uniqueId) { this.uniqueId = uniqueId; }
    public Integer getIdComponente() { return idComponente; }
    public void setIdComponente(Integer idComponente) { this.idComponente = idComponente; }
    public Integer getIdSubcomponente() { return idSubcomponente; }
    public void setIdSubcomponente(Integer idSubcomponente) { this.idSubcomponente = idSubcomponente; }
    public Integer getIdTema() { return idTema; }
    public void setIdTema(Integer idTema) { this.idTema = idTema; }
    public String getIdEstadistico1() { return idEstadistico1; }
    public void setIdEstadistico1(String idEstadistico1) { this.idEstadistico1 = idEstadistico1; }
    public Integer getIdEstadistico2() { return idEstadistico2; }
    public void setIdEstadistico2(Integer idEstadistico2) { this.idEstadistico2 = idEstadistico2; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}