/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.repository.datosabiertos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.datosabiertos.datos_abiertos_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface datos_abiertos_armo_repo
        extends JpaRepository<datos_abiertos_armo_enty, Integer> {

    List<datos_abiertos_armo_enty> findByIdAOrderByIdUniqueAsc(String idA);

    @Query("SELECT COUNT(d) FROM datos_abiertos_armo_enty d "
            + "WHERE d.idA = :idA "
            + "AND LOWER(TRIM(d.urlAcceso)) = LOWER(TRIM(:urlAcceso)) "
            + "AND LOWER(TRIM(d.urlDescarga)) "
            + "= LOWER(TRIM(:urlDescarga)) "
            + "AND LOWER(TRIM(d.descriptor)) = LOWER(TRIM(:descriptor)) "
            + "AND LOWER(TRIM(d.tabla)) = LOWER(TRIM(:tabla)) "
            + "AND LOWER(TRIM(d.campo)) = LOWER(TRIM(:campo)) "
            + "AND LOWER(TRIM(d.comentarioA)) = LOWER(TRIM(:comentarioA))")
    long contarDuplicados(
            @Param("idA") String idA,
            @Param("urlAcceso") String urlAcceso,
            @Param("urlDescarga") String urlDescarga,
            @Param("descriptor") String descriptor,
            @Param("tabla") String tabla,
            @Param("campo") String campo,
            @Param("comentarioA") String comentarioA);

    @Query("SELECT COUNT(d) FROM datos_abiertos_armo_enty d "
            + "WHERE d.idUnique <> :idUnique AND d.idA = :idA "
            + "AND LOWER(TRIM(d.urlAcceso)) = LOWER(TRIM(:urlAcceso)) "
            + "AND LOWER(TRIM(d.urlDescarga)) = LOWER(TRIM(:urlDescarga)) "
            + "AND LOWER(TRIM(d.descriptor)) = LOWER(TRIM(:descriptor)) "
            + "AND LOWER(TRIM(d.tabla)) = LOWER(TRIM(:tabla)) "
            + "AND LOWER(TRIM(d.campo)) = LOWER(TRIM(:campo)) "
            + "AND LOWER(TRIM(d.comentarioA)) = LOWER(TRIM(:comentarioA))")
    long contarDuplicadosExcluyendoId(
            @Param("idUnique") Integer idUnique,
            @Param("idA") String idA,
            @Param("urlAcceso") String urlAcceso,
            @Param("urlDescarga") String urlDescarga,
            @Param("descriptor") String descriptor,
            @Param("tabla") String tabla,
            @Param("campo") String campo,
            @Param("comentarioA") String comentarioA);
}
