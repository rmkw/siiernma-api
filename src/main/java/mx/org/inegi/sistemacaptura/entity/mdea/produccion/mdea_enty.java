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

    private String idA;
    private String idS;
    private String componente;
    private String subcomponente;
    private String tema;
    private String estadistica1;
    private String estadistica2;
    private String contribucion;
    private String comentarioS;
}