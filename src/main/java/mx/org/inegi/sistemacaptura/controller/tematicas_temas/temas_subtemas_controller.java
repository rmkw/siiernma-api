/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller.tematicas_temas;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.List;
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.temas_subtemas_enty;
import mx.org.inegi.sistemacaptura.service.tematicas_temas.temas_subtemas_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog/temas-subtemas")
public class temas_subtemas_controller {

    @Autowired
    private temas_subtemas_service service;

    @GetMapping("/temas")
    public List<String> obtenerTemas() {
        return service.obtenerTemas();
    }

    @GetMapping("/subtemas/{tema}")
    public List<temas_subtemas_enty> obtenerSubtemasPorTema(@PathVariable String tema) {
        return service.obtenerSubtemasPorTema(tema);
    }
}