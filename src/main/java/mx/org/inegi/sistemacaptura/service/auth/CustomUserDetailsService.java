package mx.org.inegi.sistemacaptura.service.auth;

import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import mx.org.inegi.sistemacaptura.repository.usuario.usuario_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private usuario_repo usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usuario_enty usuario = usuarioRepo.findByNombre(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return User.builder()
                .username(usuario.getNombre())
                .password(usuario.getContrasena())
                .roles(usuario.getRoles().toArray(new String[0]))
                .build();
    }
}