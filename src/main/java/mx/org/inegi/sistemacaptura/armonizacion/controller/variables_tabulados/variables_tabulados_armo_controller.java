package mx.org.inegi.sistemacaptura.armonizacion.controller.variables_tabulados;

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables_tabulados.variables_tabulados_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.variables_tabulados.variables_tabulados_armo_service;
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
@RequestMapping("/api/armo/variables-tabulados")
public class variables_tabulados_armo_controller {

    @Autowired
    private variables_tabulados_armo_service service;

    @GetMapping("/tabulado/{idTabulado}")
    public ResponseEntity<?> obtenerPorTabulado(
            @PathVariable String idTabulado) {
        try {
            return ResponseEntity.ok(service.obtenerPorTabulado(idTabulado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al consultar las relaciones: "
                            + e.getMessage());
        }
    }

    @GetMapping("/{idUnique}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUnique) {
        Optional<variables_tabulados_armo_dto> relacion
                = service.obtenerPorId(idUnique);
        return relacion.isPresent()
                ? ResponseEntity.ok(relacion.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No existe la relación con id_unique: "
                                + idUnique);
    }

    @PostMapping
    public ResponseEntity<?> guardar(
            @RequestBody variables_tabulados_armo_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(service.guardar(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar la relación: " + e.getMessage());
        }
    }

    @PutMapping("/{idUnique}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer idUnique,
            @RequestBody variables_tabulados_armo_dto dto) {
        try {
            return ResponseEntity.ok(service.actualizar(idUnique, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar la relación: "
                            + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idUnique) {
        try {
            service.eliminar(idUnique);
            return ResponseEntity.ok("Relación eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar la relación: " + e.getMessage());
        }
    }
}
