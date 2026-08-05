package mx.org.inegi.sistemacaptura.armonizacion.controller.mdea;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.mdea.mdea_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.mdea.mdea_armo_service;
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
@RequestMapping("/api/armo/mdea")
public class mdea_armo_controller {

    @Autowired
    private mdea_armo_service mdeaArmoService;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody mdea_armo_dto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(mdeaArmoService.guardar(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar la relación MDEA: " + e.getMessage());
        }
    }

    @PutMapping("/{idUnique}")
    public ResponseEntity<?> actualizar(@PathVariable Integer idUnique,
            @RequestBody mdea_armo_dto dto) {
        try {
            return ResponseEntity.ok(mdeaArmoService.actualizar(idUnique, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al actualizar la relación MDEA: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idUnique}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idUnique) {
        try {
            mdeaArmoService.eliminar(idUnique);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al eliminar la relación MDEA: " + e.getMessage());
        }
    }

    @GetMapping("/variable/{idA}")
    public List<mdea_armo_dto> obtenerPorIdA(@PathVariable String idA) {
        return mdeaArmoService.obtenerPorIdA(idA);
    }
}
