package mx.org.inegi.sistemacaptura.armonizacion.controller.desagregaciones;

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desagregaciones.desagregaciones_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.desagregaciones.desagregaciones_armo_service;
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
@RequestMapping("/api/armo/desagregaciones")
public class desagregaciones_armo_controller {

    @Autowired
    private desagregaciones_armo_service desagregacionesArmoService;

    @GetMapping("/tabulado/{idTabulado}")
    public ResponseEntity<?> obtenerPorTabulado(
            @PathVariable String idTabulado) {
        try {
            return ResponseEntity.ok(
                    desagregacionesArmoService.obtenerPorTabulado(
                            idTabulado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al consultar las desagregaciones: "
                            + e.getMessage());
        }
    }

    @GetMapping("/{idUnique}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUnique) {
        Optional<desagregaciones_armo_dto> desagregacion
                = desagregacionesArmoService.obtenerPorId(idUnique);
        if (desagregacion.isPresent()) {
            return ResponseEntity.ok(desagregacion.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe la desagregación con id_unique: "
                        + idUnique);
    }

    @PostMapping
    public ResponseEntity<?> guardarDesagregacion(
            @RequestBody desagregaciones_armo_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(desagregacionesArmoService
                            .guardarDesagregacion(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar la desagregación: "
                            + e.getMessage());
        }
    }

    @PutMapping("/{idUnique}")
    public ResponseEntity<?> actualizarDesagregacion(
            @PathVariable Integer idUnique,
            @RequestBody desagregaciones_armo_dto dto) {
        try {
            return ResponseEntity.ok(desagregacionesArmoService
                    .actualizarDesagregacion(idUnique, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar la desagregación: "
                            + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminarDesagregacion(
            @PathVariable Integer idUnique) {
        try {
            desagregacionesArmoService.eliminarDesagregacion(idUnique);
            return ResponseEntity.ok(
                    "Desagregación eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar la desagregación: "
                            + e.getMessage());
        }
    }
}
