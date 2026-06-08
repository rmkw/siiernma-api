/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.auth;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import mx.org.inegi.sistemacaptura.repository.usuario.usuario_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class auth_services {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private usuario_repo repo;

    public usuario_enty login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("Autenticacion despues de login: "
                + SecurityContextHolder.getContext().getAuthentication());

        return repo.findByNombre(username);
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public usuario_enty getUsuarioAutenticado(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println("Cookie recibida: "
                        + cookie.getName() + " = " + cookie.getValue());
            }
        } else {
            System.out.println("No se recibieron cookies en la peticion.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Autenticacion actual: " + authentication);

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            System.out.println("No hay usuario autenticado");
            return null;
        }

        String nombreUsuario = authentication.getName();
        System.out.println("Nombre autenticado: " + nombreUsuario);

        return repo.findByNombre(nombreUsuario);
    }
}
