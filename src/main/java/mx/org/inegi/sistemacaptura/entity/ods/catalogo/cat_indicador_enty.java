/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.ods.catalogo;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ods_indicador", schema = "catalogos")
public class cat_indicador_enty {

    @Id
    @Column(name = "id_indicador")
    private Integer idIndicador;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "id_objetivo", nullable = false)
    private Integer idObjetivo;

    @Column(name = "id_meta", nullable = false)
    private String idMeta;

    @Column(name = "indicador", nullable = false)
    private String indicador;

    public Integer getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(Integer idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Integer getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(Integer idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public String getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(String idMeta) {
        this.idMeta = idMeta;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }
}
