/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.controller.datosabiertos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.datosabiertos.datos_abiertos_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.datosabiertos.datos_abiertos_armo_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/armo/datos-abiertos")
public class datos_abiertos_armo_controller {

    @Autowired
    private datos_abiertos_armo_service datosAbiertosArmoService;

    @PostMapping
    public ResponseEntity<?> guardarDatoAbierto(
            @RequestBody datos_abiertos_armo_dto dto) {
        try {
            datos_abiertos_armo_dto guardado
                    = datosAbiertosArmoService.guardarDatoAbierto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar el dato abierto: "
                            + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminarDatoAbierto(
            @PathVariable Integer idUnique) {
        try {
            datosAbiertosArmoService.eliminarDatoAbierto(idUnique);
            return ResponseEntity.ok(
                    "Dato abierto eliminado correctamente con id_unique: "
                    + idUnique);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar el dato abierto: "
                            + e.getMessage());
        }
    }

    @GetMapping("/variable/{idA}")
    public ResponseEntity<List<datos_abiertos_armo_dto>> obtenerPorIdA(
            @PathVariable String idA) {
        return ResponseEntity.ok(
                datosAbiertosArmoService.obtenerPorIdA(idA));
    }
}
