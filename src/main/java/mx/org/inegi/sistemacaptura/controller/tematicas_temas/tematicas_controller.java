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
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.tematicas_enty;
import mx.org.inegi.sistemacaptura.service.tematicas_temas.tematicas_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/catalog/tematicas")
public class tematicas_controller {

    @Autowired
    private tematicas_service service;

    @GetMapping("/proceso/{acronimo}")
    public List<tematicas_enty> obtenerPorAcronimo(@PathVariable String acronimo) {
        return service.obtenerPorAcronimo(acronimo);
    }
}