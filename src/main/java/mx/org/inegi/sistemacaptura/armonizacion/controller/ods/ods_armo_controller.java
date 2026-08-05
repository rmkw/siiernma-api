package mx.org.inegi.sistemacaptura.armonizacion.controller.ods;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.ods.ods_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.ods.ods_armo_service;
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
@RequestMapping("/api/armo/ods")
public class ods_armo_controller {

    @Autowired
    private ods_armo_service odsArmoService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody ods_armo_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(odsArmoService.guardar(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar la relación ODS: " + e.getMessage());
        }
    }

    @PutMapping("/{idUnique}")
    public ResponseEntity<?> actualizar(@PathVariable Integer idUnique,
            @RequestBody ods_armo_dto dto) {
        try {
            return ResponseEntity.ok(odsArmoService.actualizar(idUnique, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar la relación ODS: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idUnique) {
        try {
            odsArmoService.eliminar(idUnique);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar la relación ODS: " + e.getMessage());
        }
    }

    @GetMapping("/variable/{idA}")
    public List<ods_armo_dto> obtenerPorIdA(@PathVariable String idA) {
        return odsArmoService.obtenerPorIdA(idA);
    }
}
