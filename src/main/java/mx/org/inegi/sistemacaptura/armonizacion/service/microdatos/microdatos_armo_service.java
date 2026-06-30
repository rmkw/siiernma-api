/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.microdatos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.microdatos.microdatos_armo_dto;

public interface microdatos_armo_service {

    microdatos_armo_dto guardarMicrodato(microdatos_armo_dto dto);

    void eliminarMicrodato(Integer idUnique);

    List<microdatos_armo_dto> obtenerPorIdA(String idA);
}
