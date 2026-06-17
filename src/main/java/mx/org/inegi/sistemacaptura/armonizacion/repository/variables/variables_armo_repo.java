/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.repository.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface variables_armo_repo
        extends JpaRepository<variables_armo_enty, String> {
}
