/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.tematicas_temas;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.List;
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.temas_subtemas_enty;

public interface temas_subtemas_service {

    List<String> obtenerTemas();

    List<temas_subtemas_enty> obtenerSubtemasPorTema(String tema);
}