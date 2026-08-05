/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.pertinencias;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.pertinencias.pertinencia_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface pertinencia_repo extends JpaRepository<pertinencia_enty, Integer> {

    Optional<pertinencia_enty> findByIdA(String idA);

    @Query(value = "SELECT id_unique, id_a, CAST('' AS text) AS id_s, pertinencia, "
            + "contribucion, viabilidad, propuesta, comentario_s "
            + "FROM public.pertinencia_a WHERE id_a = :idA", nativeQuery = true)
    Optional<pertinencia_enty> findArmonizacionByIdA(@Param("idA") String idA);

    void deleteByIdA(String idA);
}
