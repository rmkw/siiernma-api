/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.Collections;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import mx.org.inegi.sistemacaptura.entity.fuentes.fuentes_dto;
import mx.org.inegi.sistemacaptura.entity.fuentes.fuentes_enty;
import mx.org.inegi.sistemacaptura.entity.usuario.clave_admin_dto;
import mx.org.inegi.sistemacaptura.service.fuentes.fuentes_services;
import mx.org.inegi.sistemacaptura.service.usuario.admin_usuarios_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fuentes")
public class fuentes_ctr {
    @Autowired
    private fuentes_services service;
    @Autowired
    private admin_usuarios_service adminService;

    @GetMapping("/por-acronimo")
    public ResponseEntity<List<fuentes_dto>> getByAcronimo(@RequestParam String acronimo) {
        return ResponseEntity.ok(service.getByAcronimo(acronimo));
    }

    @GetMapping("/by-id-fuente-seleccion")
    public ResponseEntity<fuentes_enty> getByIdFuenteSeleccion(@RequestParam String idFuenteSeleccion) {
        return ResponseEntity.ok(service.getByIdFuenteSeleccion(idFuenteSeleccion));
    }

    @PostMapping
    public ResponseEntity<fuentes_enty> create(@RequestBody fuentes_enty fuente) {
        return ResponseEntity.ok(service.create(fuente));
    }

    @PutMapping("/update")
    public ResponseEntity<fuentes_enty> update(
            @RequestParam String idFuenteSeleccion,
            @RequestBody fuentes_enty datos) {
        return ResponseEntity.ok(service.update(idFuenteSeleccion, datos));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam String idFuenteSeleccion,
            @RequestBody(required = false) clave_admin_dto dto,
            HttpServletRequest request) {
        adminService.verificarClaveAdministrativa(request.getSession(true), dto == null ? null : dto.getClave());
        service.deleteByIdFuenteSeleccion(idFuenteSeleccion);
        return ResponseEntity.ok(Collections.singletonMap("message", "Fuente eliminada correctamente"));
    }

    @PostMapping("/eliminar-desde-armonizacion")
    public ResponseEntity<?> deleteDesdeArmonizacion(
            @RequestParam String idFuenteSeleccion,
            @RequestBody clave_admin_dto dto,
            HttpServletRequest request) {
        adminService.verificarClaveAdministrativa(request.getSession(true), dto == null ? null : dto.getClave());
        service.deleteByIdFuenteSeleccion(idFuenteSeleccion);
        return ResponseEntity.ok(Collections.singletonMap("message", "Fuente de Selección y sus variables eliminadas correctamente"));
    }

    @GetMapping("/count")
    public ResponseEntity<?> countFuentes() {
        return ResponseEntity.ok(Collections.singletonMap("total", service.contarFuentes()));
    }
}
