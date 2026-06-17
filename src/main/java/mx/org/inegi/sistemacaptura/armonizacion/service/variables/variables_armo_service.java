/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_dto;

public interface variables_armo_service {

    Optional<variables_armo_dto> obtenerPorIdA(String idA);

    boolean existePorIdA(String idA);

    variables_armo_dto guardarVariable(variables_armo_dto dto);

    variables_armo_dto actualizarVariable(String idA, variables_armo_dto dto);

    void eliminarVariable(String idA);

    Long contarVariablesArmonizadas();
}