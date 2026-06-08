/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.unidades;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unidades", schema = "catalogos")
public class unidades_enty {
    @Id
    @Column(name = "id_unidad")
    private Long idUnidad;

    @Column(name = "name_unidad")
    private String nameUnidad;

    public unidades_enty() {
    }

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNameUnidad() {
        return nameUnidad;
    }

    public void setNameUnidad(String nameUnidad) {
        this.nameUnidad = nameUnidad;
    }
}
