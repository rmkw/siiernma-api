/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.tematicas_temas;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.List;
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.tematicas_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tematicas_repo extends JpaRepository<tematicas_enty, Integer> {

    List<tematicas_enty> findByAcronimoOrderByTematicaAsc(String acronimo);
}