/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author LUIS.CASTANEDAL
 */
package mx.org.inegi.sistemacaptura.service.usuario;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_admin_dto;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import mx.org.inegi.sistemacaptura.repository.usuario.usuario_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class usuario_services {

    @Autowired
    private usuario_repo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<usuario_admin_dto> getAllUsuarios() {
        List<usuario_admin_dto> respuesta = new ArrayList<usuario_admin_dto>();
        for (usuario_enty usuario : repo.findAll()) {
            respuesta.add(toDto(usuario));
        }
        return respuesta;
    }

    public usuario_admin_dto getUsuarioById(Long id) {
        usuario_enty usuario = repo.findById(id).orElse(null);
        return usuario == null ? null : toDto(usuario);
    }

    public usuario_enty registrarUsuario(usuario_enty usuario) {
        if (repo.findByNombre(usuario.getNombre()) != null) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        Set<String> roles = new HashSet<String>();
        roles.add("USER");
        usuario.setRoles(roles);

        return repo.save(usuario);
    }

    public usuario_admin_dto toDto(usuario_enty usuario) {
        Set<String> roles = usuario.getRoles() == null
                ? new HashSet<String>()
                : new HashSet<String>(usuario.getRoles());
        return new usuario_admin_dto(
                usuario.getId(), usuario.getNombre(), usuario.getAka(), roles);
    }
}
