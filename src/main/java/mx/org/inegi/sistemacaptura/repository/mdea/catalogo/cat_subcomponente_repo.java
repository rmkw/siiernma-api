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
import mx.org.inegi.sistemacaptura.entity.mdea.catalogo.cat_subcomponente_enty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cat_subcomponente_repo extends JpaRepository<cat_subcomponente_enty, Integer> {
    List<cat_subcomponente_enty> findByIdComponente(Integer idComponente);
    Optional<cat_subcomponente_enty> findByUniqueId(Integer uniqueId);
}
