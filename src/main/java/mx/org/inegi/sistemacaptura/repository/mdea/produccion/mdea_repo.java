/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.mdea.produccion;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface mdea_repo extends JpaRepository<mdea_enty, Integer> {
    List<mdea_enty> findByIdA(String idA);
    List<mdea_enty> findByIdS(String idS);

    @Query(value = "SELECT id_unique, id_a, CAST('' AS text) AS id_s, componente, "
            + "subcomponente, tema, estadistica1, estadistica2, contribucion, "
            + "comentario_s FROM armonizacion.mdea WHERE id_a = :idA", nativeQuery = true)
    List<mdea_enty> findArmonizacionByIdA(@Param("idA") String idA);
    void deleteByIdA(String idA);

    boolean existsByIdAAndComponenteAndSubcomponenteAndTemaAndEstadistica1AndEstadistica2(
            String idA, String componente, String subcomponente,
            String tema, String estadistica1, String estadistica2);
}
