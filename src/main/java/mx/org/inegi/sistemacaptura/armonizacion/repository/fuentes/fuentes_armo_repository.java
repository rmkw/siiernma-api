/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.repository.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes.fuentes_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface fuentes_armo_repository
        extends JpaRepository<fuentes_armo_enty, String> {

    Optional<fuentes_armo_enty> findByIdFuente(String idFuente);

    boolean existsByIdFuente(String idFuente);

    Optional<fuentes_armo_enty> findByIdFuenteSeleccion(String idFuenteSeleccion);

    boolean existsByIdFuenteSeleccion(String idFuenteSeleccion);

    @Modifying
    @Query(value = "INSERT INTO armonizacion.fuentes "
            + "(acronimo, fuente, url, edicion, comentario_s, comentario_a, id_fuente_seleccion) "
            + "VALUES (:acronimo, :fuente, :url, :edicion, :comentarioS, :comentarioA, :idFuenteSeleccion)",
            nativeQuery = true)
    void insertFuente(
            @Param("acronimo") String acronimo,
            @Param("fuente") String fuente,
            @Param("url") String url,
            @Param("edicion") String edicion,
            @Param("comentarioS") String comentarioS,
            @Param("comentarioA") String comentarioA,
            @Param("idFuenteSeleccion") String idFuenteSeleccion);

    @Modifying
    @Query(value = "UPDATE armonizacion.fuentes "
            + "SET acronimo = :acronimo, "
            + "fuente = :fuente, "
            + "url = :url, "
            + "edicion = :edicion, "
            + "comentario_s = :comentarioS, "
            + "comentario_a = :comentarioA "
            + "WHERE id_fuente_seleccion = :idFuenteSeleccion",
            nativeQuery = true)
    int updateFuenteByIdFuenteSeleccion(
            @Param("acronimo") String acronimo,
            @Param("fuente") String fuente,
            @Param("url") String url,
            @Param("edicion") String edicion,
            @Param("comentarioS") String comentarioS,
            @Param("comentarioA") String comentarioA,
            @Param("idFuenteSeleccion") String idFuenteSeleccion);
}