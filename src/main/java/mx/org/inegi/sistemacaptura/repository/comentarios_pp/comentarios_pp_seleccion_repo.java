/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.comentarios_pp;

/**
 *
 * @author LUIS.CASTANEDAL
 */


import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.comentarios_pp.comentarios_pp_seleccion_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface comentarios_pp_seleccion_repo
        extends JpaRepository<comentarios_pp_seleccion_enty, String> {

    Optional<comentarios_pp_seleccion_enty> findByAcronimo(String acronimo);
}