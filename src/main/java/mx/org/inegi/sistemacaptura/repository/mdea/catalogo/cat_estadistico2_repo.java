/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.mdea.catalogo;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.mdea.catalogo.cat_estadistico2_enty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cat_estadistico2_repo extends JpaRepository<cat_estadistico2_enty, String> {
    List<cat_estadistico2_enty> findByIdComponenteAndIdSubcomponenteAndIdTemaAndIdEstadistico1OrderByUniqueId(
            Integer idComponente, Integer idSubcomponente, Integer idTema, String idEstadistico1);

    Optional<cat_estadistico2_enty> findByUniqueId(String uniqueId);
}