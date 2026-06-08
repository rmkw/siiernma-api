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

public interface ods_repo extends JpaRepository<ods_enty, Integer> {

    List<ods_enty> findByIdA(String idA);

    List<ods_enty> findByIdS(String idS);

    void deleteByIdA(String idA);

    boolean existsByIdAAndObjetivoAndMetaAndIndicador(
            String idA, String objetivo, String meta, String indicador);
}