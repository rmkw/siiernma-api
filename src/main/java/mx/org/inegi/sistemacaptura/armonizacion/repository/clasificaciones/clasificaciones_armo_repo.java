/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.repository.clasificaciones;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificaciones.clasificaciones_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clasificaciones_armo_repo
        extends JpaRepository<clasificaciones_armo_enty, Integer> {

    List<clasificaciones_armo_enty> findByIdAOrderByIdUniqueAsc(String idA);
}
