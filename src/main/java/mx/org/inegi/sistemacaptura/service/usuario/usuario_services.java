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
import java.util.List;
import java.util.Set;
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

    public List<usuario_enty> getAllUsuarios() {
        return repo.findAll();
    }

    public usuario_enty getUsuarioById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public usuario_enty registrarUsuario(usuario_enty usuario) {
        if (repo.findByNombre(usuario.getNombre()) != null) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        Set<String> roles = usuario.getRoles();
        if (roles == null) {
            roles = new HashSet<String>();
        }

        roles.add("USER");
        usuario.setRoles(roles);

        return repo.save(usuario);
    }
}