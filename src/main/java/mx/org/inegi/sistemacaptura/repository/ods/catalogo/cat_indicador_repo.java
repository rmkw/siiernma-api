/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.ods.catalogo;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_indicador_enty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cat_indicador_repo extends JpaRepository<cat_indicador_enty, Integer> {

    List<cat_indicador_enty> findByIdObjetivoAndIdMeta(Integer idObjetivo, String idMeta);

    Optional<cat_indicador_enty> findByUniqueId(String uniqueId);
}