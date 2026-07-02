package mx.org.inegi.sistemacaptura.armonizacion.controller.desgloses;

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desgloses.desgloses_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.desgloses.desgloses_armo_service;
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
@RequestMapping("/api/armo/desgloses")
public class desgloses_armo_controller {

    @Autowired
    private desgloses_armo_service desglosesArmoService;

    @GetMapping("/tabulado/{idTabulado}")
    public ResponseEntity<?> obtenerPorTabulado(
            @PathVariable String idTabulado) {
        try {
            return ResponseEntity.ok(
                    desglosesArmoService.obtenerPorTabulado(idTabulado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al consultar los desgloses: "
                            + e.getMessage());
        }
    }

    @GetMapping("/{idUnique}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer idUnique) {
        Optional<desgloses_armo_dto> desglose
                = desglosesArmoService.obtenerPorId(idUnique);
        if (desglose.isPresent()) {
            return ResponseEntity.ok(desglose.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe el desglose con id_unique: " + idUnique);
    }

    @PostMapping
    public ResponseEntity<?> guardarDesglose(
            @RequestBody desgloses_armo_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(desglosesArmoService.guardarDesglose(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar el desglose: " + e.getMessage());
        }
    }

    @PutMapping("/{idUnique}")
    public ResponseEntity<?> actualizarDesglose(
            @PathVariable Integer idUnique,
            @RequestBody desgloses_armo_dto dto) {
        try {
            return ResponseEntity.ok(
                    desglosesArmoService.actualizarDesglose(idUnique, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el desglose: "
                            + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminarDesglose(
            @PathVariable Integer idUnique) {
        try {
            desglosesArmoService.eliminarDesglose(idUnique);
            return ResponseEntity.ok("Desglose eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar el desglose: " + e.getMessage());
        }
    }
}
