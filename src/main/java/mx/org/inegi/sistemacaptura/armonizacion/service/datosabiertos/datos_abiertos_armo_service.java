/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.datosabiertos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.datosabiertos.datos_abiertos_armo_dto;

public interface datos_abiertos_armo_service {

    datos_abiertos_armo_dto guardarDatoAbierto(datos_abiertos_armo_dto dto);

    void eliminarDatoAbierto(Integer idUnique);

    List<datos_abiertos_armo_dto> obtenerPorIdA(String idA);
}
