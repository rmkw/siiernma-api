/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.entity.variables.variables_enty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface variables_repo extends JpaRepository<variables_enty, String> {

    boolean existsByIdA(String idA);

    boolean existsByIdSAndIdFuente(String idS, String idFuente);

    List<variables_enty> findByIdS(String idS);

    List<variables_enty> findByIdFuente(String idFuente);

    List<variables_enty> findByIdFuenteInOrderByIdFuenteDescIdAAsc(
            List<String> idFuentes);

    Long countByIdFuente(String idFuente);

    Long countByPrioridad(Integer prioridad);

    Page<variables_enty> findByIdFuente(String idFuente, Pageable pageable);
}