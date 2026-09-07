package mx.org.inegi.sistemacaptura.service.usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_admin_dto;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_admin_request_dto;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import mx.org.inegi.sistemacaptura.repository.usuario.usuario_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class admin_usuarios_service {

    private static final String ACCESO = "ADMIN_USUARIOS_DESBLOQUEADO";
    private static final String ULTIMA_ACTIVIDAD = "ADMIN_USUARIOS_ULTIMA_ACTIVIDAD";
    private static final String INTENTOS = "ADMIN_USUARIOS_INTENTOS";
    private static final String BLOQUEADO_HASTA = "ADMIN_USUARIOS_BLOQUEADO_HASTA";
    private static final long DURACION_ACCESO_MS = 15L * 60L * 1000L;
    private static final long DURACION_BLOQUEO_MS = 60L * 1000L;
    private static final int MAX_INTENTOS = 5;
    private static final String CLAVE_ADMINISTRATIVA = "SIIERNMA-USUARIOS-2026";
    private static final Set<String> ROLES_PERMITIDOS = new HashSet<String>(
            Arrays.asList("USER", "ARMO", "ADMIN", "ROOT"));

    @Autowired
    private usuario_repo repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void desbloquear(HttpSession session, String clave) {
        long ahora = System.currentTimeMillis();
        Long bloqueadoHasta = (Long) session.getAttribute(BLOQUEADO_HASTA);
        if (bloqueadoHasta != null && bloqueadoHasta > ahora) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Demasiados intentos. Espera un minuto antes de volver a intentar");
        }

        if (isBlank(clave) || !CLAVE_ADMINISTRATIVA.equals(clave)) {
            Integer intentos = (Integer) session.getAttribute(INTENTOS);
            intentos = intentos == null ? 1 : intentos + 1;
            session.setAttribute(INTENTOS, intentos);

            if (intentos >= MAX_INTENTOS) {
                session.setAttribute(BLOQUEADO_HASTA, ahora + DURACION_BLOQUEO_MS);
                session.setAttribute(INTENTOS, 0);
            }

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "La clave es incorrecta");
        }

        session.setAttribute(ACCESO, Boolean.TRUE);
        session.setAttribute(ULTIMA_ACTIVIDAD, ahora);
        session.removeAttribute(INTENTOS);
        session.removeAttribute(BLOQUEADO_HASTA);
    }

    public boolean estaDesbloqueado(HttpSession session) {
        if (session == null || !Boolean.TRUE.equals(session.getAttribute(ACCESO))) {
            return false;
        }

        Long ultimaActividad = (Long) session.getAttribute(ULTIMA_ACTIVIDAD);
        if (ultimaActividad == null
                || System.currentTimeMillis() - ultimaActividad > DURACION_ACCESO_MS) {
            bloquear(session);
            return false;
        }

        session.setAttribute(ULTIMA_ACTIVIDAD, System.currentTimeMillis());
        return true;
    }

    public void verificarAcceso(HttpSession session) {
        if (!estaDesbloqueado(session)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "El módulo de usuarios está bloqueado");
        }
    }

    public void bloquear(HttpSession session) {
        if (session == null) return;
        session.removeAttribute(ACCESO);
        session.removeAttribute(ULTIMA_ACTIVIDAD);
    }

    public List<usuario_admin_dto> listar() {
        List<usuario_admin_dto> respuesta = new ArrayList<usuario_admin_dto>();
        for (usuario_enty usuario : repository.findAll()) {
            respuesta.add(toDto(usuario));
        }
        return respuesta;
    }

    @Transactional
    public usuario_admin_dto crear(usuario_admin_request_dto dto) {
        validarDatosBase(dto);
        if (isBlank(dto.getContrasena())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }
        if (repository.findByNombre(dto.getNombre().trim()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya existe");
        }

        usuario_enty usuario = new usuario_enty();
        usuario.setNombre(dto.getNombre().trim());
        usuario.setAka(dto.getAka().trim());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRoles(validarRoles(dto.getRoles()));
        return toDto(repository.save(usuario));
    }

    @Transactional
    public usuario_admin_dto actualizar(Long id, usuario_admin_request_dto dto) {
        validarDatosBase(dto);
        usuario_enty usuario = obtener(id);
        usuario_enty existente = repository.findByNombre(dto.getNombre().trim());
        if (existente != null && !existente.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El nombre de usuario ya existe");
        }

        usuario.setNombre(dto.getNombre().trim());
        usuario.setAka(dto.getAka().trim());
        usuario.setRoles(validarRoles(dto.getRoles()));
        return toDto(repository.save(usuario));
    }

    @Transactional
    public void cambiarContrasena(Long id, String contrasena) {
        if (isBlank(contrasena)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }
        usuario_enty usuario = obtener(id);
        usuario.setContrasena(passwordEncoder.encode(contrasena));
        repository.save(usuario);
    }

    @Transactional
    public void eliminar(Long id) {
        usuario_enty usuario = obtener(id);
        try {
            repository.delete(usuario);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el usuario porque tiene registros relacionados",
                    e);
        }
    }

    public usuario_admin_dto toDto(usuario_enty usuario) {
        Set<String> roles = usuario.getRoles() == null
                ? Collections.<String>emptySet()
                : new HashSet<String>(usuario.getRoles());
        return new usuario_admin_dto(
                usuario.getId(), usuario.getNombre(), usuario.getAka(), roles);
    }

    private usuario_enty obtener(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del usuario es obligatorio");
        }
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private void validarDatosBase(usuario_admin_request_dto dto) {
        if (dto == null || isBlank(dto.getNombre()) || isBlank(dto.getAka())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El nombre de usuario y el alias son obligatorios");
        }
    }

    private Set<String> validarRoles(Set<String> roles) {
        Set<String> resultado = new HashSet<String>();
        if (roles != null) {
            for (String rol : roles) {
                String limpio = rol == null ? "" : rol.trim().toUpperCase();
                if (!ROLES_PERMITIDOS.contains(limpio)) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Rol no permitido: " + rol);
                }
                resultado.add(limpio);
            }
        }
        if (resultado.isEmpty()) resultado.add("USER");
        return resultado;
    }

    private boolean isBlank(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
