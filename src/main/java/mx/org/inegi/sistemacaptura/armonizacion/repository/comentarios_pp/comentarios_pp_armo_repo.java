/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LUIS.CASTANEDAL
 */
package mx.org.inegi.sistemacaptura.armonizacion.repository.comentarios_pp;

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.comentarios_pp.comentarios_pp_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface comentarios_pp_armo_repo
        extends JpaRepository<comentarios_pp_armo_enty, String> {

    Optional<comentarios_pp_armo_enty> findByAcronimo(String acronimo);
}
