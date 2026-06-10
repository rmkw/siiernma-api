/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.mdea.catalogo;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.mdea.catalogo.cat_componente_enty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cat_componente_repo extends JpaRepository<cat_componente_enty, Integer> {
    Optional<cat_componente_enty> findByIdComponente(Integer idComponente);
}