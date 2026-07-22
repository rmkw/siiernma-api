/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.repository.microdatos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.microdatos.microdatos_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface microdatos_armo_repo
        extends JpaRepository<microdatos_armo_enty, Integer> {

    List<microdatos_armo_enty> findByIdAOrderByIdUniqueAsc(String idA);

    @Query("SELECT COUNT(m) FROM microdatos_armo_enty m "
            + "WHERE m.idA = :idA "
            + "AND LOWER(TRIM(m.urlAcceso)) = LOWER(TRIM(:urlAcceso)) "
            + "AND LOWER(TRIM(m.descriptor)) = LOWER(TRIM(:descriptor)) "
            + "AND LOWER(TRIM(m.urlDescriptor)) "
            + "= LOWER(TRIM(:urlDescriptor)) "
            + "AND LOWER(TRIM(m.tabla)) = LOWER(TRIM(:tabla)) "
            + "AND LOWER(TRIM(m.campo)) = LOWER(TRIM(:campo)) "
            + "AND LOWER(TRIM(m.comentarioA)) = LOWER(TRIM(:comentarioA))")
    long contarDuplicados(
            @Param("idA") String idA,
            @Param("urlAcceso") String urlAcceso,
            @Param("descriptor") String descriptor,
            @Param("urlDescriptor") String urlDescriptor,
            @Param("tabla") String tabla,
            @Param("campo") String campo,
            @Param("comentarioA") String comentarioA);

    @Query("SELECT COUNT(m) FROM microdatos_armo_enty m "
            + "WHERE m.idUnique <> :idUnique AND m.idA = :idA "
            + "AND LOWER(TRIM(m.urlAcceso)) = LOWER(TRIM(:urlAcceso)) "
            + "AND LOWER(TRIM(m.descriptor)) = LOWER(TRIM(:descriptor)) "
            + "AND LOWER(TRIM(m.urlDescriptor)) = LOWER(TRIM(:urlDescriptor)) "
            + "AND LOWER(TRIM(m.tabla)) = LOWER(TRIM(:tabla)) "
            + "AND LOWER(TRIM(m.campo)) = LOWER(TRIM(:campo)) "
            + "AND LOWER(TRIM(m.comentarioA)) = LOWER(TRIM(:comentarioA))")
    long contarDuplicadosExcluyendoId(
            @Param("idUnique") Integer idUnique,
            @Param("idA") String idA,
            @Param("urlAcceso") String urlAcceso,
            @Param("descriptor") String descriptor,
            @Param("urlDescriptor") String urlDescriptor,
            @Param("tabla") String tabla,
            @Param("campo") String campo,
            @Param("comentarioA") String comentarioA);
}
