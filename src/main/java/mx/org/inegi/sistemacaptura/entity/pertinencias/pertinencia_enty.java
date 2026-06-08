/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.pertinencias;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import javax.persistence.*;

@Entity
@Table(name = "pertinencia", schema = "seleccion")
public class pertinencia_enty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unique")
    private Integer idUnique;

    private String idA;
    private String idS;
    private String pertinencia;
    private String contribucion;
    private String viabilidad;
    private String propuesta;
    private String comentarioS;
}