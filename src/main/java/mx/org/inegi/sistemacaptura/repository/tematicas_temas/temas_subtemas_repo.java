/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author LUIS.CASTANEDAL
 */
package mx.org.inegi.sistemacaptura.repository.tematicas_temas;

import java.util.List;
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.temas_subtemas_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface temas_subtemas_repo extends JpaRepository<temas_subtemas_enty, Integer> {

    @Query("SELECT DISTINCT t.tema FROM temas_subtemas_enty t ORDER BY t.tema ASC")
    List<String> findTemasDistinct();

    List<temas_subtemas_enty> findByTemaOrderBySubtemaAsc(String tema);
}