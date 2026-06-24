/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.controller.clasificaciones;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificaciones.clasificaciones_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.clasificaciones.clasificaciones_armo_service;
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
@RequestMapping("/api/armo/clasificaciones")
public class clasificaciones_armo_controller {

    @Autowired
    private clasificaciones_armo_service clasificacionesArmoService;

    @PostMapping
    public ResponseEntity<?> guardarClasificacion(
            @RequestBody clasificaciones_armo_dto dto) {
        try {
            clasificaciones_armo_dto guardada
                    = clasificacionesArmoService.guardarClasificacion(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar la clasificacion: "
                            + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminarClasificacion(
            @PathVariable Integer idUnique) {
        try {
            clasificacionesArmoService.eliminarClasificacion(idUnique);
            return ResponseEntity.ok(
                    "Clasificacion eliminada correctamente con id_unique: "
                    + idUnique);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar la clasificacion: "
                            + e.getMessage());
        }
    }

    @GetMapping("/variable/{idA}")
    public ResponseEntity<List<clasificaciones_armo_dto>> obtenerPorIdA(
            @PathVariable String idA) {
        return ResponseEntity.ok(clasificacionesArmoService.obtenerPorIdA(idA));
    }
}
