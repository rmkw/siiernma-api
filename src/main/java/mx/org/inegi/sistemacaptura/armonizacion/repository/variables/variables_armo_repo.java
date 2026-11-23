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
import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface variables_armo_repo
        extends JpaRepository<variables_armo_enty, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM variables_armo_enty v WHERE v.idA = :idA")
    Optional<variables_armo_enty> buscarParaActualizar(@Param("idA") String idA);

    default void exigirEditable(String idA) {
        buscarParaActualizar(idA).ifPresent(variables_armo_repo::comprobarRevision);
    }

    static void comprobarRevision(variables_armo_enty variable) {
        if (Boolean.TRUE.equals(variable.getValidada())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La variable " + variable.getIdA()
                    + " está revisada. Debe desrevisarla en Validación antes de modificarla.");
        }
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM variables_armo_enty v WHERE v.idFuente = :idFuente ORDER BY v.idA")
    List<variables_armo_enty> bloquearPorFuente(@Param("idFuente") String idFuente);

    default void exigirFuenteEditable(String idFuente) {
        for (variables_armo_enty variable : bloquearPorFuente(idFuente)) {
            comprobarRevision(variable);
        }
    }

    String TIENE_CLASIFICACION = "(SELECT COUNT(*) FROM " + DatabaseTables.CLASIFICACIONES_A_SQL + " WHERE id_a = :idA) >= 2"
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
