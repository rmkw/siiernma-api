/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.controller.microdatos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.microdatos.microdatos_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.microdatos.microdatos_armo_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/armo/microdatos")
public class microdatos_armo_controller {

    @Autowired
    private microdatos_armo_service microdatosArmoService;

    @PostMapping
    public ResponseEntity<?> guardarMicrodato(
            @RequestBody microdatos_armo_dto dto) {
        try {
            microdatos_armo_dto guardado
                    = microdatosArmoService.guardarMicrodato(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar el microdato: "
                            + e.getMessage());
        }
    }

    @PutMapping("/{idUnique}")
    public ResponseEntity<?> actualizarMicrodato(@PathVariable Integer idUnique,
            @RequestBody microdatos_armo_dto dto) {
        try {
            return ResponseEntity.ok(microdatosArmoService.actualizarMicrodato(idUnique, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el microdato: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminarMicrodato(@PathVariable Integer idUnique) {
        try {
            microdatosArmoService.eliminarMicrodato(idUnique);
            return ResponseEntity.ok(
                    "Microdato eliminado correctamente con id_unique: "
                    + idUnique);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar el microdato: "
                            + e.getMessage());
        }
    }

    @GetMapping("/variable/{idA}")
    public ResponseEntity<List<microdatos_armo_dto>> obtenerPorIdA(
            @PathVariable String idA) {
        return ResponseEntity.ok(microdatosArmoService.obtenerPorIdA(idA));
    }
}
