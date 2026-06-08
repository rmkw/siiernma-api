/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.ods.produccion;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import javax.persistence.*;

@Entity
@Table(name = "ods", schema = "seleccion")
public class ods_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    private String idA;
    private String idS;
    private String objetivo;
    private String meta;
    private String indicador;
    private String contribucion;
    private String comentarioS;
}
