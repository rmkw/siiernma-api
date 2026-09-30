/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.repository.variables;

import mx.org.inegi.sistemacaptura.config.DatabaseTables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface variables_armo_repo
        extends JpaRepository<variables_armo_enty, String> {

    String TIENE_CLASIFICACION = "EXISTS (SELECT 1 FROM " + DatabaseTables.CLASIFICACIONES_A_SQL + " WHERE id_a = :idA)"
            + " OR EXISTS (SELECT 1 FROM " + DatabaseTables.CLASIFICADORES_A_SQL + " WHERE id_a = :idA)";

    @Query(value = "SELECT " + TIENE_CLASIFICACION, nativeQuery = true)
    boolean tieneClasificacionOClasificador(@Param("idA") String idA);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE " + DatabaseTables.VARIABLES_A_SQL + " SET clasificacion = (" + TIENE_CLASIFICACION
            + ") WHERE id_a = :idA", nativeQuery = true)
    int sincronizarClasificacion(@Param("idA") String idA);

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
