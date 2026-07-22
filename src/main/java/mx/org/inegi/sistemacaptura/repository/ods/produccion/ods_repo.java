/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.ods.produccion;

/**
 *
 * @author LUIS.CASTANEDAL
 */


import java.util.List;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ods_repo extends JpaRepository<ods_enty, Integer> {

    List<ods_enty> findByIdA(String idA);

    List<ods_enty> findByIdS(String idS);

    @Query(value = "SELECT id_unique, id_a, CAST('' AS text) AS id_s, objetivo, meta, "
            + "indicador, contribucion, comentario_s FROM armonizacion.ods "
            + "WHERE id_a = :idA", nativeQuery = true)
    List<ods_enty> findArmonizacionByIdA(@Param("idA") String idA);

    void deleteByIdA(String idA);

    boolean existsByIdAAndObjetivoAndMetaAndIndicador(
            String idA,
            String objetivo,
            String meta,
            String indicador);
}
