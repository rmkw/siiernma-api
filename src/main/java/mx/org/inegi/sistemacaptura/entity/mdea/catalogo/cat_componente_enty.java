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
@Table(name = "mdea_componentes", schema = "catalogos")
public class cat_componente_enty {
    @Id
    @Column(name = "id_componente")
    private Integer idComponente;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "definicion", nullable = false)
    private String definicion;

    @Column(name = "definicion_corta", nullable = false)
    private String definicionCorta;

    public Integer getIdComponente() { return idComponente; }
    public void setIdComponente(Integer idComponente) { this.idComponente = idComponente; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDefinicion() { return definicion; }
    public void setDefinicion(String definicion) { this.definicion = definicion; }
    public String getDefinicionCorta() { return definicionCorta; }
    public void setDefinicionCorta(String definicionCorta) { this.definicionCorta = definicionCorta; }
}