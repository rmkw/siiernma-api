/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.repository.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface variables_armo_repo
        extends JpaRepository<variables_armo_enty, String> {

    List<variables_armo_enty> findByIdFuenteOrderByIdAAsc(String idFuente);

    long countByIdFuente(String idFuente);

    List<variables_armo_enty> findByAcronimoOrderByIdAAsc(String acronimo);

    @Query("SELECT v FROM variables_armo_enty v "
            + "WHERE UPPER(v.idA) LIKE CONCAT(UPPER(:termino), '%') "
            + "OR LOWER(COALESCE(v.variableA, '')) "
            + "LIKE CONCAT('%', LOWER(:termino), '%') "
            + "OR LOWER(COALESCE(v.variableS, '')) "
            + "LIKE CONCAT('%', LOWER(:termino), '%') "
            + "ORDER BY v.idA ASC")
    List<variables_armo_enty> buscarPorIdONombre(
            @Param("termino") String termino);
}
