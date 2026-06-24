/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.clasificaciones;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificaciones.clasificaciones_armo_dto;

public interface clasificaciones_armo_service {

    clasificaciones_armo_dto guardarClasificacion(clasificaciones_armo_dto dto);

    void eliminarClasificacion(Integer idUnique);

    List<clasificaciones_armo_dto> obtenerPorIdA(String idA);
}
