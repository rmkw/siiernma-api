package mx.org.inegi.sistemacaptura.armonizacion.controller.tabulados;

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados.tabulados_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.tabulados.tabulados_armo_service;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/armo/tabulados")
public class tabulados_armo_controller {

    @Autowired
    private tabulados_armo_service tabuladosArmoService;

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorPrefijo(
            @RequestParam String prefijo) {
        try {
            List<tabulados_armo_dto> tabulados
                    = tabuladosArmoService.buscarPorPrefijo(prefijo);
            return ResponseEntity.ok(tabulados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al buscar tabulados: " + e.getMessage());
        }
    }

    @GetMapping("/{idTabulado}")
    public ResponseEntity<?> obtenerPorId(
            @PathVariable String idTabulado) {
        Optional<tabulados_armo_dto> tabulado
                = tabuladosArmoService.obtenerPorId(idTabulado);

        if (tabulado.isPresent()) {
            return ResponseEntity.ok(tabulado.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No existe el tabulado con id_tabulado: "
                        + idTabulado);
    }

    @PostMapping
    public ResponseEntity<?> guardarTabulado(
            @RequestBody tabulados_armo_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(tabuladosArmoService.guardarTabulado(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar el tabulado: "
                            + e.getMessage());
        }
    }

    @PutMapping("/{idTabulado}")
    public ResponseEntity<?> actualizarTabulado(
            @PathVariable String idTabulado,
            @RequestBody tabulados_armo_dto dto) {
        try {
            return ResponseEntity.ok(
                    tabuladosArmoService.actualizarTabulado(
                            idTabulado, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el tabulado: "
                            + e.getMessage());
        }
    }

    @DeleteMapping("/{idTabulado}")
    public ResponseEntity<?> eliminarTabulado(
            @PathVariable String idTabulado) {
        try {
            tabuladosArmoService.eliminarTabulado(idTabulado);
            return ResponseEntity.ok(
                    "Tabulado eliminado correctamente con id_tabulado: "
                    + idTabulado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar el tabulado: "
                            + e.getMessage());
        }
    }
}
