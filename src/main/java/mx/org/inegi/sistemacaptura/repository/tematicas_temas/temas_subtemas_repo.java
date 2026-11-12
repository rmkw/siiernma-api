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
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.temas_subtemas_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface temas_subtemas_repo extends JpaRepository<temas_subtemas_enty, Integer> {

    @Query("SELECT DISTINCT t.tema FROM temas_subtemas_enty t ORDER BY t.tema ASC")
    List<String> findTemasDistinct();

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END "
            + "FROM temas_subtemas_enty t WHERE LOWER(TRIM(t.tema)) = :nombre")
    boolean existsByNombreNormalizado(@Param("nombre") String nombre);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM temas_subtemas_enty t "
            + "WHERE LOWER(TRIM(t.tema)) = :tema AND LOWER(TRIM(t.subtema)) = :subtema")
    boolean existsSubtemaNormalizado(@Param("tema") String tema, @Param("subtema") String subtema);

    Optional<temas_subtemas_enty> findFirstByTemaAndSubtemaIsNull(String tema);

    @Query("SELECT t FROM temas_subtemas_enty t WHERE t.tema = :tema AND t.subtema IS NOT NULL ORDER BY t.subtema ASC")
    List<temas_subtemas_enty> findByTemaOrderBySubtemaAsc(@Param("tema") String tema);
}
