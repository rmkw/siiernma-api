package mx.org.inegi.sistemacaptura.armonizacion.controller.pertinencias;

import mx.org.inegi.sistemacaptura.armonizacion.entity.pertinencias.pertinencia_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.pertinencias.pertinencia_armo_service;
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
@RequestMapping("/api/armo/pertinencia")
public class pertinencia_armo_controller {

    @Autowired
    private pertinencia_armo_service pertinenciaArmoService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody pertinencia_armo_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(pertinenciaArmoService.guardar(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar la pertinencia: " + e.getMessage());
        }
    }

    @PutMapping("/{idUnique}")
    public ResponseEntity<?> actualizar(@PathVariable Integer idUnique,
            @RequestBody pertinencia_armo_dto dto) {
        try {
            return ResponseEntity.ok(pertinenciaArmoService.actualizar(idUnique, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar la pertinencia: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idUnique) {
        try {
            pertinenciaArmoService.eliminar(idUnique);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar la pertinencia: " + e.getMessage());
        }
    }

    @GetMapping("/variable/{idA}")
    public ResponseEntity<pertinencia_armo_dto> obtenerPorIdA(@PathVariable String idA) {
        return pertinenciaArmoService.obtenerPorIdA(idA)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
