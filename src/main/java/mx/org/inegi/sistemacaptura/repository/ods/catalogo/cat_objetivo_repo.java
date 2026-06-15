/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.ods.catalogo;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_objetivo_enty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cat_objetivo_repo extends JpaRepository<cat_objetivo_enty, Integer> {

    Optional<cat_objetivo_enty> findByIdObjetivo(Integer idObjetivo);
}
