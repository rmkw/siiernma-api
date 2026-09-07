package mx.org.inegi.sistemacaptura.controller.usuario;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.org.inegi.sistemacaptura.entity.usuario.clave_admin_dto;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_admin_dto;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_admin_request_dto;
import mx.org.inegi.sistemacaptura.service.usuario.admin_usuarios_service;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin-usuarios")
public class admin_usuarios_controller {

    @Autowired
    private admin_usuarios_service service;

    @PostMapping("/desbloquear")
    public ResponseEntity<?> desbloquear(
            @RequestBody clave_admin_dto dto, HttpServletRequest request) {
        return ejecutar(() -> {
            service.desbloquear(request.getSession(true), dto == null ? null : dto.getClave());
            return Collections.<String, Object>singletonMap("desbloqueado", true);
        });
    }

    @GetMapping("/estado")
    public ResponseEntity<Map<String, Object>> estado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return ResponseEntity.ok(Collections.<String, Object>singletonMap(
                "desbloqueado", service.estaDesbloqueado(session)));
    }

    @PostMapping("/bloquear")
    public ResponseEntity<Map<String, Object>> bloquear(HttpServletRequest request) {
        service.bloquear(request.getSession(false));
        return ResponseEntity.ok(Collections.<String, Object>singletonMap("desbloqueado", false));
    }

    @GetMapping
    public ResponseEntity<?> listar(HttpServletRequest request) {
        return ejecutar(() -> {
            service.verificarAcceso(request.getSession(false));
            return service.listar();
        });
    }

    @PostMapping
    public ResponseEntity<?> crear(
            @RequestBody usuario_admin_request_dto dto, HttpServletRequest request) {
        return ejecutar(() -> {
            service.verificarAcceso(request.getSession(false));
            return service.crear(dto);
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody usuario_admin_request_dto dto,
            HttpServletRequest request) {
        return ejecutar(() -> {
            service.verificarAcceso(request.getSession(false));
            return service.actualizar(id, dto);
        });
    }

    @PutMapping("/{id}/contrasena")
    public ResponseEntity<?> cambiarContrasena(
            @PathVariable Long id,
            @RequestBody clave_admin_dto dto,
            HttpServletRequest request) {
        return ejecutar(() -> {
            service.verificarAcceso(request.getSession(false));
            service.cambiarContrasena(id, dto == null ? null : dto.getClave());
            return Collections.<String, Object>singletonMap(
                    "message", "Contraseña actualizada correctamente");
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(
            @PathVariable Long id, HttpServletRequest request) {
        return ejecutar(() -> {
            service.verificarAcceso(request.getSession(false));
            service.eliminar(id);
            return Collections.<String, Object>singletonMap(
                    "message", "Usuario eliminado correctamente");
        });
    }

    private ResponseEntity<?> ejecutar(Operacion operacion) {
        try {
            return ResponseEntity.ok(operacion.ejecutar());
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(
                    Collections.singletonMap("message", e.getReason()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Collections.singletonMap("message", "No fue posible completar la operación"));
        }
    }

    private interface Operacion {
        Object ejecutar();
    }
}
