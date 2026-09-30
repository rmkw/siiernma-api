package mx.org.inegi.sistemacaptura.armonizacion.controller.clasificadores;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificadores.clasificadores_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.clasificadores.clasificadores_armo_service;
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
@RequestMapping("/api/armo/clasificadores")
public class clasificadores_armo_controller {

    @Autowired
    private clasificadores_armo_service clasificadoresArmoService;

    @PostMapping
    public ResponseEntity<?> guardarClasificador(@RequestBody clasificadores_armo_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clasificadoresArmoService.guardarClasificador(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar el clasificador: " + e.getMessage());
        }
    }

    @PutMapping("/{idUnique}")
    public ResponseEntity<?> actualizarClasificador(@PathVariable Integer idUnique,
            @RequestBody clasificadores_armo_dto dto) {
        try {
            return ResponseEntity.ok(
                    clasificadoresArmoService.actualizarClasificador(idUnique, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar el clasificador: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminarClasificador(@PathVariable Integer idUnique) {
        try {
            clasificadoresArmoService.eliminarClasificador(idUnique);
            return ResponseEntity.ok(
                    "Clasificador eliminado correctamente con id_unique: " + idUnique);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar el clasificador: " + e.getMessage());
        }
    }

    @GetMapping("/variable/{idA}")
    public ResponseEntity<List<clasificadores_armo_dto>> obtenerPorIdA(
            @PathVariable String idA) {
        return ResponseEntity.ok(clasificadoresArmoService.obtenerPorIdA(idA));
    }
}
