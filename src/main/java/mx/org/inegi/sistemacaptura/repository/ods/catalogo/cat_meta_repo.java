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
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_meta_enty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cat_meta_repo extends JpaRepository<cat_meta_enty, String> {

    List<cat_meta_enty> findByIdObjetivo(Integer idObjetivo);

    Optional<cat_meta_enty> findByUniqueId(String uniqueId);
}