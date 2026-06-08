/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller.usuario;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import mx.org.inegi.sistemacaptura.repository.usuario.usuario_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prueba/usuarios")
public class usuario_ctr {
    @Autowired
    private usuario_repo usuarioRepository;

    @GetMapping
    public List<Map<String, Object>> listarUsuarios() {
        List<usuario_enty> usuarios = usuarioRepository.findAll();
        List<Map<String, Object>> respuesta = new ArrayList<Map<String, Object>>();

        for (usuario_enty usuario : usuarios) {
            Map<String, Object> registro = new LinkedHashMap<String, Object>();
            registro.put("id", usuario.getId());
            registro.put("nombre", usuario.getNombre());
            registro.put("aka", usuario.getAka());
            registro.put("roles", usuario.getRoles());

            respuesta.add(registro);
        }

        return respuesta;
    }
}
