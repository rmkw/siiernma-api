/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller.auth;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.org.inegi.sistemacaptura.entity.auth.login_dto;
import mx.org.inegi.sistemacaptura.entity.auth.usuario_dto;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import mx.org.inegi.sistemacaptura.service.auth.auth_services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class auth_ctr {
    @Autowired
    private auth_services authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody login_dto loginDTO,
            HttpServletRequest request) {
        try {
            usuario_enty usuario = authService.login(
                    loginDTO.getUsername(),
                    loginDTO.getPassword());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("Autenticacion despues de login: " + auth);

            Map<String, Object> response = new HashMap<String, Object>();
            response.put("user", new usuario_dto(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getAka(),
                    usuario.getRoles()));

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap(
                            "error",
                            "El usuario o la contrasena son incorrectos"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("No hay sesion activa en el backend");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("No hay sesion activa");
        }

        System.out.println("Cerrando sesion para ID: " + session.getId());

        session.invalidate();
        SecurityContextHolder.clearContext();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Sesion cerrada exitosamente");
    }

    @GetMapping("/usuario")
    public ResponseEntity<Map<String, Object>> getUsuarioAutenticado(
            HttpServletRequest request) {
        usuario_enty usuario = authService.getUsuarioAutenticado(request);
        Map<String, Object> response = new HashMap<String, Object>();

        if (usuario == null) {
            response.put("authenticated", false);
            response.put("message", "No hay usuario autenticado");
            return ResponseEntity.ok(response);
        }

        response.put("authenticated", true);
        response.put("user", new usuario_dto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getAka(),
                usuario.getRoles()));

        return ResponseEntity.ok(response);
    }
}
