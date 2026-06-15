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
@Table(name = "ods_meta", schema = "catalogos")
public class cat_meta_enty {

    @Id
    @Column(name = "id_meta")
    private String idMeta;

    @Column(name = "unique_id")
    private String uniqueId;

    @Column(name = "id_objetivo", nullable = false)
    private Integer idObjetivo;

    @Column(name = "meta", nullable = false)
    private String meta;

    public String getIdMeta() {
        return idMeta;
    }

    public void setIdMeta(String idMeta) {
        this.idMeta = idMeta;
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

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}