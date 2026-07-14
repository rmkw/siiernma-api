/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_busqueda_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_detalle_armo_dto;

public interface variables_armo_service {

    Optional<variables_armo_dto> obtenerPorIdA(String idA);

    variables_detalle_armo_dto obtenerDetallePorIdA(String idA);

    variables_armo_dto actualizarValidacion(String idA, Boolean validada);

    List<variables_armo_dto> obtenerPorIdFuente(String idFuente);

    List<variables_busqueda_armo_dto> buscarPorIdONombre(String termino);

    List<variables_busqueda_armo_dto> obtenerPorProceso(String acronimo);

    boolean existePorIdA(String idA);

    variables_armo_dto guardarVariable(variables_armo_dto dto);

    variables_armo_dto actualizarVariable(String idA, variables_armo_dto dto);

    void eliminarVariable(String idA);

    Long contarVariablesArmonizadas();
}
